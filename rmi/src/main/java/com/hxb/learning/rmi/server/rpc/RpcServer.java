package com.hxb.learning.rmi.server.rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void publish(final Object service, int port){

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true){
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessHandler(socket,service));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
