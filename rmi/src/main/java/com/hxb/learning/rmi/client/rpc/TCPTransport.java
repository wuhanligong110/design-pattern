package com.hxb.learning.rmi.client.rpc;

import com.hxb.learning.rmi.server.rpc.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPTransport {

    private String host;
    private int port;

    public TCPTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Socket newSocket(){
        System.out.println("创建一个新的连接");
        Socket socket;
        try {
            socket = new Socket(host,port);
            return socket;
        } catch (IOException e) {
            System.out.println("连接建立失败");
            e.printStackTrace();
        }
        return null;
    }

    public Object send(RpcRequest request){
        try ( Socket socket = newSocket();
              ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
              ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())){
            outputStream.writeObject(request);
            outputStream.flush();

            Object result = inputStream.readObject();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
