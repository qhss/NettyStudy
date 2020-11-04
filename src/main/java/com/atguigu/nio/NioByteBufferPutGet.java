package com.atguigu.nio;

import java.nio.ByteBuffer;

public class NioByteBufferPutGet {
    public static void main(String[] args) {
        //创建一个buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putInt(100);
        buffer.putLong(9L);
        buffer.putChar('学');
        buffer.putShort((short)4);
        //取出
        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());

    }
}
