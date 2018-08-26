package com.hxb.learning.patterns.proxy.dynamicproxy;

import com.hxb.learning.patterns.proxy.SuperStar;

public class CglibAgentTest {

    public static void main(String[] args) {
        SuperStar zhangxueyou = new SuperStar();
        CglibAgent agent = new CglibAgent();
        SuperStar zhangxueyouProxy = (SuperStar) agent.getInstance(zhangxueyou);
        zhangxueyouProxy.sing("饿狼传说");
    }
}
