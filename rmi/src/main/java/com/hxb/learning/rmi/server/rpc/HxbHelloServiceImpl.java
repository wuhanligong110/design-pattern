package com.hxb.learning.rmi.server.rpc;

public class HxbHelloServiceImpl implements IHxbHelloService{

    @Override
    public String sayHello(String message) {
        return "hello, " + message;
    }

}
