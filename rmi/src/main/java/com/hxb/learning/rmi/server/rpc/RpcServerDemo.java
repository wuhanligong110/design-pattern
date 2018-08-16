package com.hxb.learning.rmi.server.rpc;

public class RpcServerDemo {

    public static void main(String[] args) {
        IHxbHelloService helloService = new HxbHelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.publish(helloService,8888);
    }
}
