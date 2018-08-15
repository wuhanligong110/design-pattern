package com.hxb.learning.patterns.abstartfactory;

public class UseClient {
    public static void main(String[] args) {
        ConcreteFactory factory = new ConcreteFactory();
        System.out.println(factory.produceRice());
    }
}
