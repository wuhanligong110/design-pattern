package bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @ClassName Client
 * @Description TODO
 * @Author Hxb
 * @Date 2019/1/22 18:14
 * @Version 1.0
 **/
@Slf4j
public class Client {

    private static int DEFAULT_SERVER_PORT = 7777;

    private static String DEFAULT_SERVER_IP = "127.0.0.1";

    public static void send(String expression){
        send(DEFAULT_SERVER_PORT,expression);
    }

    private static void send(int port, String expression) {
        System.out.println(("算术表达式为：" + expression));
        try(
                Socket socket = new Socket(DEFAULT_SERVER_IP,port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true)
        ){
            out.println(expression);
            System.out.println(("__结果为： " + in.readLine()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
