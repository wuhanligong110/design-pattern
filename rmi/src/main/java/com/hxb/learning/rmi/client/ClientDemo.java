package com.hxb.learning.rmi.client;

import com.hxb.learning.rmi.server.IHelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientDemo {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        IHelloService helloService = (IHelloService)Naming.lookup("rmi://127.0.0.1/hello");
        System.out.println(helloService.sayHello("Hxb"));
    }
}
