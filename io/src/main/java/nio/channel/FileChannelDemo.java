package nio.channel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @ClassName FileChannelDemo
 * @Description TODO
 * @Author Hxb
 * @Date 2019/1/24 10:27
 * @Version 1.0
 **/
public class FileChannelDemo {

    public static void main(String[] args) {
        try {
            File file = new File("f:/nio_utf8.data");
            if(!file.exists()){
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            FileChannel fc = fos.getChannel();

            ByteBuffer bb = ByteBuffer.allocate(64);
            bb.put("Hello,Avail ! \n".getBytes("UTF-8"));
            bb.flip();
            fc.write(bb);
            bb.clear();

            bb.put("你好，先生！".getBytes("UTF-8"));
            bb.flip();
            fc.write(bb);
            bb.clear();

            fos.close();
            fc.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Path path = Paths.get("f:/nio_utf8.data");
            FileChannel fc = FileChannel.open(path);

            ByteBuffer bb = ByteBuffer.allocate((int)fc.size()+1);
            Charset utf8 = Charset.forName("UTF-8");

            fc.read(bb);
            bb.flip();

            CharBuffer cb = utf8.decode(bb);
            System.out.println(cb.toString());
            bb.clear();
            fc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
