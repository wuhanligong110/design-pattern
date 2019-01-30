package nio.buffer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @ClassName BufferDemo
 * @Description TODO
 * @Author Hxb
 * @Date 2019/1/24 9:54
 * @Version 1.0
 **/
public class BufferDemo {

    public static void  decode(String str) throws UnsupportedEncodingException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        byteBuffer.put(str.getBytes("UTF-8"));
        byteBuffer.flip();

        Charset utf8 = Charset.forName("UTF-8");
        CharBuffer charBuffer = utf8.decode(byteBuffer);

        char[] charArr = Arrays.copyOf(charBuffer.array(),charBuffer.limit());
        System.out.println(charArr);
    }

    public static String decode(byte[] bytes){
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        byteBuffer.put(bytes);
        byteBuffer.flip();

        Charset utf8 = Charset.forName("UTF-8");
        CharBuffer charBuffer = utf8.decode(byteBuffer);

        char[] charArr = Arrays.copyOf(charBuffer.array(),charBuffer.limit());
        return String.copyValueOf(charArr);
    }

    public static byte[] encode(String str){
        CharBuffer charBuffer = CharBuffer.allocate(128);
        charBuffer.append(str);
        charBuffer.flip();

        Charset utf8 = Charset.forName("UTF-8");
        ByteBuffer byteBuffer = utf8.encode(charBuffer);

        byte[] bytes = Arrays.copyOf(byteBuffer.array(),byteBuffer.limit());
        return  bytes;
    }


    public static void main(String[] args){

        byte[] bytes = BufferDemo.encode("Avail");
        System.out.println(Arrays.toString(bytes));

        String str = BufferDemo.decode(bytes);
        System.out.println(str);
    }
}
