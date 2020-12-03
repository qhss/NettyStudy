package com.atguigu.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //因为是基于Http协议，所以要使用Http的编码和解码器
                            pipeline.addLast(new HttpServerCodec());
                            //是以块方式写，添加ChunkedWriter处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            /**
                             * 1、http数据在传输过程中是分裂的,HttpObjectAggregator就可以将多个段聚合
                             * 2、这就是为什么，当浏览器发送大量数据时，就会发出多次http请求
                             * 3、8192为块的大小
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            /**
                             * 1、对于websocket，它的数据是以帧的形式传递的
                             * 2、可以看到 WebsocketFrame 下面有六个子类
                             * 3、浏览器请求时：ws://localhost:7000/hello 表示请求的uri
                             * 4、WebSocketServerProtocolHandler 核心功能是将 http 协议升级为 ws 协议，保持长连接
                             * 5、从Http协议升级到Websocket协议，是通过StatusCode 101（Switching Protocols）来切换的。
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            //自定义Handler，处理业务逻辑
                            pipeline.addLast(new MyTextWebSocketFrameHandler());
                        }
                    });
            ChannelFuture sync = serverBootstrap.bind(7000).sync();
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

