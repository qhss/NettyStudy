package com.atguigu.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class GroupChatClient {
    //定义属性
    private final String Host="127.0.0.1";
    private final int port=6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;
    //构造器初始化工作
    public GroupChatClient() throws IOException {
        selector=Selector.open();
        //连接服务器
        socketChannel=SocketChannel.open(new InetSocketAddress("127.0.0.1",port));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //将channel注册到selector上
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到userName
        userName=socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(userName+"is ok...");

    }
    //向服务器发送消息
    public void sendInfo(String info){

        info=userName+"说"+info;
        try{
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //读取从服务器端回复的消息
    public void readInfo(){
        try{
            int readChannels = selector.select();
            if (readChannels>0){//有可以用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isReadable()){
                        //得到相关的通道
                        SocketChannel sc =(SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        sc.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    iterator.remove();

                }

            }
            else{
//                System.out.println("没有可用的通道");
            }


        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();
        //启动一个线程，每隔三秒，从服务器端读取数据
        new Thread(){
            public void run(){
                while(true){
                    chatClient.readInfo();
                    try {
                     Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
        //发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }

    }
}
