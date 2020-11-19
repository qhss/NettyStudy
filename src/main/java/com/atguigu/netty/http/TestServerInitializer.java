package com.atguigu.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入一个netty提供的HttpServerCodec(编解码器) codec=>[coder-decoder]
        //HttpServerCodec说明 是netty提供的编解码器
        pipeline.addLast("MyHttpServer",new HttpServerCodec());
        //增加一个自定义的处理器
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());


    }
}
