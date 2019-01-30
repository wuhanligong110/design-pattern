package nio.channel;

import nio.buffer.Buffers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * @ClassName ServiceSocketChannelDemo
 * @Description TODO
 * @Author Hxb
 * @Date 2019/1/24 10:59
 * @Version 1.0
 **/
public class ServiceSocketChannelDemo {

    public static class TCPEchoServer implements Runnable{

        private InetSocketAddress localAddress;

        public TCPEchoServer(int port){
            this.localAddress = new InetSocketAddress(port);
        }

        @Override
        public void run() {
            Charset utf8 = Charset.forName("UTF-8");

            ServerSocketChannel ssc = null;
            Selector selector = null;

            Random rnd = new Random();

            try {
                selector = Selector.open();
                ssc = ServerSocketChannel.open();

                ssc.configureBlocking(false);

                ssc.bind(localAddress,100);
                ssc.register(selector, SelectionKey.OP_ACCEPT);

            } catch (IOException e) {
                System.out.println("server start failed");
                return;
            }

            System.out.println("server start with address : " + localAddress);

            try{
                while (!Thread.currentThread().isInterrupted()){
                    int n = selector.select();
                    if(n == 0){
                        continue;
                    }

                    Set<SelectionKey> keySet = selector.selectedKeys();
                    Iterator<SelectionKey> it =  keySet.iterator();
                    SelectionKey key = null;

                    while (it.hasNext()){
                        key = it.next();
                        it.remove();

                        try {
                            if(key.isAcceptable()){
                                SocketChannel sc = ssc.accept();
                                sc.configureBlocking(false);

                                int interestSet = SelectionKey.OP_READ;
                                sc.register(selector, interestSet, new Buffers(256,256));
                                System.out.println("accept from " + sc.getRemoteAddress());
                            }

                            if(key.isReadable()){
                                Buffers buffers = (Buffers) key.attachment();
                                ByteBuffer readBuffer = buffers.getReadBuffer();
                                ByteBuffer writeBuffer = buffers.getWriteBuffer();

                                SocketChannel sc = (SocketChannel) key.channel();
                                sc.read(readBuffer);
                                readBuffer.flip();

                                CharBuffer cb = utf8.decode(readBuffer);
                                System.out.println(cb.array());

                                readBuffer.rewind();

                                writeBuffer.put("echo from service:".getBytes("UTF-8"));
                                writeBuffer.put(readBuffer);

                                readBuffer.clear();

                                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                            }

                            if(key.isWritable()){
                                Buffers buffers = (Buffers) key.attachment();
                                ByteBuffer writeBuffer = buffers.getWriteBuffer();
                                writeBuffer.flip();

                                SocketChannel sc = (SocketChannel) key.channel();

                                int len = 0;
                                while (writeBuffer.hasRemaining()){
                                    len = sc.write(writeBuffer);
                                    if(len == 0){
                                        break;
                                    }
                                }

                                writeBuffer.compact();

                                if(len != 0){
                                    key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
                                }
                            }
                        }catch(IOException e){
                            System.out.println("service encounter client error");
                            /*若客户端连接出现异常，从Seletcor中移除这个key*/
                            key.cancel();
                            key.channel().close();
                        }
                    }

                    Thread.sleep(rnd.nextInt(500));

                }
            }catch(InterruptedException e){
                System.out.println("serverThread is interrupted");
            } catch (IOException e1) {
                System.out.println("serverThread selecotr error");
            }finally{
                try{
                    selector.close();
                }catch(IOException e){
                    System.out.println("selector close failed");
                }finally{
                    System.out.println("server close");
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new TCPEchoServer(8080));
        thread.start();
        Thread.sleep(100000);
        thread.interrupt();
    }
}
