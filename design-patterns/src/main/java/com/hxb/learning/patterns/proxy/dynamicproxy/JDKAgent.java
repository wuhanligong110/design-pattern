package com.hxb.learning.patterns.proxy.dynamicproxy;

import com.hxb.learning.patterns.proxy.Person;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态代理方式 被代理的类必须实现相应接口（局限性）
 */
public class JDKAgent implements InvocationHandler {

    private Person target;

    public JDKAgent(Person target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("邀请明星前先找经济人邀约。");
        System.out.println("经纪人排期处理前期事务。");
        method.invoke(target,args);
        System.out.println("明星事情完毕，经纪人收钱。");
        return null;
    }
}
