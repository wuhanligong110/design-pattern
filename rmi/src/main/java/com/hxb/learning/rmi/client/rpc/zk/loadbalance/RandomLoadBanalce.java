package com.hxb.learning.rmi.client.rpc.zk.loadbalance;

import java.util.List;
import java.util.Random;

public class RandomLoadBanalce extends AbstractLoadBanance{
    @Override
    protected String doSelect(List<String> repos) {
        int len=repos.size();
        Random random=new Random();
        return repos.get(random.nextInt(len));
    }

}
