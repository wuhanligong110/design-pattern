package bio;

import java.io.IOException;
import java.util.Random;

/**
 * @ClassName Test
 * @Description TODO
 * @Author Hxb
 * @Date 2019/1/22 18:25
 * @Version 1.0
 **/
public class Test {

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            try {
                Server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(100);

        char[] op = {'+','-','*','/'};

        Random random = new Random(System.currentTimeMillis());
        new Thread(
            ()->{
                while (true){
                    String expression = random.nextInt(10) + ""
                            + op[random.nextInt(4)]
                            + (random.nextInt(10)+1);
                    Client.send(expression);
                    try {
                        Thread.sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        ).start();

    }
}
