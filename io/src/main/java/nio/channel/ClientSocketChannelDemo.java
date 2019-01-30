package nio.channel;

import nio.buffer.Buffers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * @ClassName ClientSocketChannelDemo
 * @Description TODO
 * @Author Hxb
 * @Date 2019/1/24 15:30
 * @Version 1.0
 **/
public class ClientSocketChannelDemo {

    public static class TCPEchoClient implements Runnable{

        private String name;
        private Random rnd = new Random();

        private InetSocketAddress remoteAddress;

        public TCPEchoClient(String name, InetSocketAddress remoteAddress) {
            this.name = name;
            this.remoteAddress = remoteAddress;
        }

        @Override
        public void run() {
            Charset utf8 = Charset.forName("UTF-8");
            Selector selector;

            try {
                SocketChannel sc = SocketChannel.open();
                sc.configureBlocking(false);
                selector = Selector.open();

                int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;

                sc.register(selector, interestSet, new Buffers(256,256));
                sc.connect(remoteAddress);

                while (!sc.finishConnect()){}
                System.out.println(name + " finished connection");
            } catch (IOException e) {
                System.out.println("client connect failed");
                return;
            }

            try {
                int i = 1;
                while (!Thread.currentThread().isInterrupted()){
                    selector.select();

                    Set<SelectionKey> keySet = selector.selectedKeys();
                    Iterator<SelectionKey> it = keySet.iterator();

                    while (it.hasNext()){
                        SelectionKey key = it.next();
                        it.remove();

                        Buffers buffers = (Buffers) key.attachment();
                        ByteBuffer readBuffer = buffers.getReadBuffer();
                        ByteBuffer writeBuffer = buffers.getWriteBuffer();

                        SocketChannel sc = (SocketChannel) key.channel();

                        if(key.isReadable()){
                            sc.read(readBuffer);
                            readBuffer.flip();
                            CharBuffer cb = utf8.decode(readBuffer);
                            System.out.println(cb.array());
                            readBuffer.clear();
                        }

                        if(key.isWritable()){
                            writeBuffer.put((name + " " + i).getBytes("UTF-8"));
                            writeBuffer.flip();
                            sc.write(writeBuffer);
                            writeBuffer.clear();
                            i++;
                        }
                    }

                    Thread.sleep(1000 + rnd.nextInt(1000));
                }
            } catch(InterruptedException e){
                System.out.println(name + " is interrupted");
            }catch(IOException e){
                System.out.println(name + " encounter a connect error");
            }finally{
                try {
                    selector.close();
                } catch (IOException e1) {
                    System.out.println(name + " close selector failed");
                }finally{
                    System.out.println(name + "  closed");
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {

        InetSocketAddress remoteAddress = new InetSocketAddress("127.0.0.1",8080);

        Thread ta = new Thread(new TCPEchoClient("thread a", remoteAddress));
        Thread tb = new Thread(new TCPEchoClient("thread b", remoteAddress));
        Thread tc = new Thread(new TCPEchoClient("thread c", remoteAddress));
        Thread td = new Thread(new TCPEchoClient("thread d", remoteAddress));

        ta.start();
//        tb.start();
//        tc.start();

        Thread.sleep(5000);

        /*结束客户端a*/
//        ta.interrupt();

        /*开始客户端d*/
//        td.start();

    }
}
