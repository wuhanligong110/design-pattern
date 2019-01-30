package bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName Server
 * @Description TODO
 * @Author Hxb
 * @Date 2019/1/22 16:42
 * @Version 1.0
 **/
@Slf4j
public class Server {

    //默认的端口号
    private static int DEFAULT_PORT = 7777;

    private static ServerSocket serverSocket;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void start() throws IOException{
        start(DEFAULT_PORT);
    }

    public synchronized static void start(int port) throws IOException{
        if(serverSocket != null) return;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println(("服务端已启动，端口号：" + port));

            while (true){
                Socket socket = serverSocket.accept();
//                new Thread(new ServerHandler(socket)).start();
                executorService.execute(new ServerHandler(socket));
            }
        }finally {
            if(serverSocket != null){
                serverSocket.close();
                serverSocket = null;
                System.out.println(("服务端已关闭！"));
            }
        }
    }


}
