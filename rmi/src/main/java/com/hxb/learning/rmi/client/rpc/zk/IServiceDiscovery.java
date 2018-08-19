package com.hxb.learning.rmi.client.rpc.zk;

public interface IServiceDiscovery {
    String discover(String serviceName);
}
