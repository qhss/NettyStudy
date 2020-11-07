package com.atguigu.nio.groupchat;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class GroupChatClient {
    //定义属性
    private final String Host="127.0.0.1";
    private final int port=6667;
    private Selector selector;
    private SocketChannel socketChannel;

    public static void main(String[] args) {


    }
}
