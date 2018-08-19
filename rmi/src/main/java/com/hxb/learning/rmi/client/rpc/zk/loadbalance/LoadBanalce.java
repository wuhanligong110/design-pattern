package com.hxb.learning.rmi.client.rpc.zk.loadbalance;

import java.util.List;

public interface LoadBanalce {
    String selectHost(List<String> repos);
}
