package com.hxb.learning.patterns.proxy.staticproxy;

import com.hxb.learning.patterns.proxy.Person;
import com.hxb.learning.patterns.proxy.SuperStar;

public class StaticProxyTest {

    public static void main(String[] args) {
        Person zhangxueyou = new SuperStar();
        Agent agent = new Agent(zhangxueyou);
        agent.sign("饿狼传说");
    }
}
