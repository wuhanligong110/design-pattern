package com.hxb.learning.patterns.proxy.dynamicproxy;

import com.hxb.learning.patterns.proxy.Person;
import com.hxb.learning.patterns.proxy.SuperStar;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class JDKDynamicProxyTest {

    public static void main(String[] args) {
        Person zhangxueyou = new SuperStar();
        Person agent = (Person) Proxy.newProxyInstance(SuperStar.class.getClassLoader(),new Class[]{Person.class},new JDKAgent(zhangxueyou));
        //agent.sing("饿狼传说");
        System.out.println(agent.getClass());
        agent.sign("张学友");

        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0",new Class[]{Person.class});
        try {
            FileOutputStream fos = new FileOutputStream("D://$Proxy0.class");
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
