package com.hxb.learning.patterns.singleton;

public class LazySingletonThree {

    private LazySingletonThree(){}

    private static class SingletonHolder{
        private static final LazySingletonThree INSTANCE = new LazySingletonThree();
    }

    public static LazySingletonThree getInstance(){
        return  SingletonHolder.INSTANCE;
    }
}
