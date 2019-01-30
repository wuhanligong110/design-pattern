package nio.buffer;

import java.nio.ByteBuffer;

/**
 * @ClassName Buffers
 * @Description TODO
 * @Author Hxb
 * @Date 2019/1/24 10:20
 * @Version 1.0
 **/
public class Buffers {

    ByteBuffer readBuffer;
    ByteBuffer writeBuffer;

    public Buffers(int readCapacity, int writeCapacity){
        readBuffer = ByteBuffer.allocate(readCapacity);
        writeBuffer = ByteBuffer.allocate(writeCapacity);
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public ByteBuffer getWriteBuffer() {
        return writeBuffer;
    }
}
