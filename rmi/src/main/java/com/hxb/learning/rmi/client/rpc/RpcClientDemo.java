package com.hxb.learning.rmi.client.rpc;

public class RpcClientDemo {

    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy();
        IHxbHelloService helloService = rpcClientProxy.clientProxy(IHxbHelloService.class,"localhost",8888);
        System.out.println(helloService.sayHello("Hxb"));
    }
}
