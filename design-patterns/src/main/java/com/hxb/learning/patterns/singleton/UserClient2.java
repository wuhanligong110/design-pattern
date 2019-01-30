package com.hxb.learning.patterns.singleton;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class UserClient2 {

    public static void main(String[] args) {

        int count = 200;
        CountDownLatch latch = new CountDownLatch(count);
        for(int i = 0; i < count; i++){
            new Thread(()->{
                try {
                    latch.await();
//                    Object object = HungrySingleton.getInstance();
//                    Object object = LazySingletonOne.getInstance();
//                    Object object = LazySingletonTwo.getInstance();
                    Object object = LazySingletonThree.getInstance();
                    System.out.println(System.currentTimeMillis()+":"+object);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            //latch.countDown();
        }

        for(int i = 0; i < count; i++){
            latch.countDown();
        }

        try {
            System.in.read();
            System.out.println("并发单例模式获取完成！" + System.currentTimeMillis());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
