package com.hxb.learning.patterns.proxy.dynamicproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibAgent implements MethodInterceptor {

    /*public Object getInstance(Class<?> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("邀请明星前先找经济人邀约。");
        System.out.println("经纪人排期处理前期事务。");
        methodProxy.invokeSuper(o,objects);
        System.out.println("明星事情完毕，经纪人收钱。");
        return null;
    }*/

    private Object target;

    public Object getInstance(final Object target){
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("邀请明星前先找经济人邀约。");
        System.out.println("经纪人排期处理前期事务。");
        methodProxy.invoke(target,objects);
        System.out.println("明星事情完毕，经纪人收钱。");
        return null;
    }

}
