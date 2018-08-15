package com.hxb.learning.patterns.singleton;

/**
 * 懒汉模式
 */
public class LazySingletonOne {

    private LazySingletonOne(){}

    private static LazySingletonOne singleton = null;

    public static LazySingletonOne getInstance(){
        if(singleton == null){
            singleton = new LazySingletonOne();
        }
        return singleton;
    }

}
