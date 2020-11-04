package com.atguigu.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * 使用通道的transfer方法实现文件的拷贝
 */
public class NioFileChannel04 {
    public static void main(String[] args) throws Exception{
        //创建相关流
        FileInputStream fileInputStream = new FileInputStream("c:\\Users\\老阙\\Desktop\\a2.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("c:\\nettytest\\netty_test.jpg");
        //获取各个流对应的channel
        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        //使用transFrom完成拷贝
        destCh.transferFrom(sourceCh,0,sourceCh.size());
        //关闭相关的通道和流
        destCh.close();
        sourceCh.close();
        fileInputStream.close();
        fileOutputStream.close();


    }
}
