package com.hxb.learning.patterns.factorymethod;

public class UseClient {
    public static void main(String[] args) {
        IFactory factory = new RiceFactory();
        System.out.println(factory.produceFoodStuff());
    }
}
