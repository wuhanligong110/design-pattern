package com.hxb.learning.rmi.client.rpc;

import com.hxb.learning.rmi.client.rpc.zk.IServiceDiscovery;

import java.lang.reflect.Proxy;

public class RpcClientProxy {

    private IServiceDiscovery serviceDiscovery;

    public RpcClientProxy(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public <T> T clientProxy(final Class<T> intefaceCls, String version){
        return (T) Proxy.newProxyInstance(intefaceCls.getClassLoader(),new Class[]{intefaceCls},new RemoteInvocationHandler(serviceDiscovery,version));
    }
}
