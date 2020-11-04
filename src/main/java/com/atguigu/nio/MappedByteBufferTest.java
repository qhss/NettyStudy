package com.atguigu.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 说明：mappedBuffer可以让文件直接在内存（堆外内存）中修改，操作系统不需要拷贝一次
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt","rw");
        //获取对应的文件通道
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数1：FileChannel.MapMode.READ_WRITE，使用的是读写模式
         * 参数2：0：可以直接修改的的起始位置
         * 参数3: 5：是映射的内存大小，即将1.txt多少个字节映射到内存()
         * 可以直接修改的内存范围就是0-5
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0,(byte)'5');
        mappedByteBuffer.put(4,(byte)'h');
        randomAccessFile.close();
        System.out.println("修改成功");


    }
}
