package com.hxb.learning.patterns.proxy;

public class SuperStar implements Person{

    @Override
    public void sing(String song) {
        System.out.println("明星在唱歌，歌名：" + song);
    }

    @Override
    public void sign(String name) {
        System.out.println("明星给唱片签名：" + name);
    }
}
