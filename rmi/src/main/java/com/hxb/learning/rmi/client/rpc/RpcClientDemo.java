package com.hxb.learning.rmi.client.rpc;

import com.hxb.learning.rmi.client.rpc.zk.IServiceDiscovery;
import com.hxb.learning.rmi.client.rpc.zk.ServiceDiscoveryImpl;
import com.hxb.learning.rmi.client.rpc.zk.ZkConfig;

public class RpcClientDemo {

    public static void main(String[] args) throws InterruptedException {
        IServiceDiscovery serviceDiscovery=new
                ServiceDiscoveryImpl(ZkConfig.CONNNECTION_STR);

        RpcClientProxy rpcClientProxy=new RpcClientProxy(serviceDiscovery);

        for(int i=0;i<10;i++) {
            IHxbHelloService hello = rpcClientProxy.clientProxy(IHxbHelloService.class, null);
            System.out.println(hello.sayHello("hxb"));
            Thread.sleep(1000);
        }

    }
}
