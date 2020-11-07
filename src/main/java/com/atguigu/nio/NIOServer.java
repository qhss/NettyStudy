package com.atguigu.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws Exception {
        //创建ServerSocketChannel->ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selector对象
        Selector selector = Selector.open();
        //绑定端口号6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel注册到selector，关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while (true){
            //这里等待一秒，如果没有事件发生（连接事件），返回
            if (selector.select(1000)==0){//没有事件发生
                System.out.println("服务器等待了一秒，无连接");
                continue;
            }
            //如果返回的大于0，就得到相应的selectionKeys集合
            //如果返回的>0,表示已经获取到关注的事件
            // selector.selectedKeys()返回关注事件的集合
            //通过selectionKeys，反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历selctionKeys
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while(keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                //根据key,对通道发生的事件进行相应的处理
                if (key.isAcceptable()){//如果是OP_ACCEPT,有新的客户端连接
                    //给该客户端生成一个serverSocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);

                    //将socketChannel注册到selector,关注事件为OP_READ，同时给socketChannel关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接成功 生成了一个 socketChannel"+socketChannel.hashCode());

                }
                if (key.isReadable()){//发生OP_READ
                    //通过key，反向获取到对应的channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到chanel关联的buffer
                    ByteBuffer byteBuffer =(ByteBuffer) key.attachment();
                    channel.read(byteBuffer);
                    System.out.println("from客户端"+new String(byteBuffer.array()));


                }
                //手动从集合中移除当前的selectionKey,防止重复操作
                keyIterator.remove();

            }
        }


    }
}
