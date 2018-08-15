package com.hxb.learning.patterns.singleton;

public class LazySingletonTwo {
    private LazySingletonTwo(){}

    private static LazySingletonTwo singleton = null;

    public static synchronized LazySingletonTwo getInstance(){
        if(singleton == null){
            singleton = new LazySingletonTwo();
        }
        return singleton;
    }
}
