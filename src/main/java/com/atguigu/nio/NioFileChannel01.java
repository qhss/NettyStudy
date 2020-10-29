package com.atguigu.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel01 {
    public static void main(String[] args) throws Exception {
        String str="hello world";
        //创建一个输出流-》channel
        FileOutputStream fileOutputStream = new FileOutputStream("c:\\nettytest\\file01.txt");
        //通过输出流获取对应的文件FileChannel
        //fileChannel的真实类型是FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个缓冲区ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将str放入到byteBuffer中
        byteBuffer.put(str.getBytes());
        //对byteBuffer进行反转
        byteBuffer.flip();
        //将byteBuffer的数据写入到fileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();

    }
}
