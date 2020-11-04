package com.atguigu.nio;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

/**
 * Scattering：将数据写入到buffer时，可以采用buffer数组依次写入（分散）
 * GatherinTe:从buffer读取数据时，可以采用buffer数组，依次读取
 */
public class ScatteringAndGatherinTe {
    public static void main(String[] args) throws Exception {

        //使用ServerSocketChannel和SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

    }
}
