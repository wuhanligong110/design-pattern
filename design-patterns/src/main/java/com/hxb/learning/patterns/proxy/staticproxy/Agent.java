package com.hxb.learning.patterns.proxy.staticproxy;

import com.hxb.learning.patterns.proxy.Person;

/**
 * 静态代理不灵活 被代理类增加一个方法 代理类必须增加对象的方法
 */
public class Agent implements Person{

    private Person person;

    public Agent(Person person) {
        this.person = person;
    }

    @Override
    public void sing(String song) {

    }

    @Override
    public void sign(String song) {
        System.out.println("邀请明星前先找经济人邀约。");
        System.out.println("经纪人排期处理前期事务。");
        person.sign(song);
        System.out.println("明星事情完毕，经纪人收钱。");
    }
}
