package bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @ClassName ServerHandler
 * @Description TODO
 * @Author Hxb
 * @Date 2019/1/22 16:57
 * @Version 1.0
 **/
@Slf4j
public class ServerHandler implements Runnable {

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out =  new PrintWriter(socket.getOutputStream(),true)
        ){
            String expression;
            String result;
            while (true){
                if((expression = in.readLine()) == null) break;
                System.out.println(("服务端收到信息：" + expression));
                result = Calculator.cal(expression);
                out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println((e.getLocalizedMessage()));
        }
    }
}
