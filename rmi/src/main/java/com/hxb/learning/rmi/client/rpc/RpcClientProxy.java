package com.hxb.learning.rmi.client.rpc;

import java.lang.reflect.Proxy;

public class RpcClientProxy {

    public <T> T clientProxy(final Class<T> intefaceCls, final String host, final int port){
        return (T) Proxy.newProxyInstance(intefaceCls.getClassLoader(),new Class[]{intefaceCls},new RemoteInvocationHandler(host,port));
    }
}
