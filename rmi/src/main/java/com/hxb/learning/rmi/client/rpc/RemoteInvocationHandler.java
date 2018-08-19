package com.hxb.learning.rmi.client.rpc;

import com.hxb.learning.rmi.client.rpc.zk.IServiceDiscovery;
import com.hxb.learning.rmi.server.rpc.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {

    private IServiceDiscovery serviceDiscovery;

    private String version;

    public RemoteInvocationHandler(IServiceDiscovery serviceDiscovery, String version) {
        this.serviceDiscovery = serviceDiscovery;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setVersion(version);
        String serviceAddress=serviceDiscovery.discover(request.getClassName());
        TCPTransport tcpTransport = new TCPTransport(serviceAddress);
        return tcpTransport.send(request);
    }
}
