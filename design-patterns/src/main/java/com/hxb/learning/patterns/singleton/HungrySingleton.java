package com.hxb.learning.patterns.singleton;

/**
 * 饿汉模式 类初始化时就创建了
 * 类加载顺序 先静态后动态 先属性后方法 先父后子
 */
public class HungrySingleton {
    //私有，外部不能new
    private HungrySingleton(){}

    private static final HungrySingleton singleton = new HungrySingleton();

    public static HungrySingleton getInstance(){
        return HungrySingleton.singleton;
    }

}
