package com.atguigu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel03 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while(true){//循环读取
            byteBuffer.clear();//清空buffer
            int read = fileChannel01.read(byteBuffer);
            if(read==-1){//读取完
                break;
            }
            //将buffer中的数据读取到fileChannel02中--2.txt
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);


        }
        fileInputStream.close();
        fileOutputStream.close();



    }
}
