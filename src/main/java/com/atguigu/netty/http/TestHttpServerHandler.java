package com.atguigu.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 *1.SimpleChannelInboundHandler是ChannelInboundHandlerAdapter子类
 * 2.HttpObject客户端和服务器端相互通讯的数据被封装成HttpObject
 *
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
        //
        if (msg instanceof HttpRequest){
            System.out.println("msg 类型 = " + msg.getClass());
            System.out.println("客户端地址：" + channelHandlerContext.channel().remoteAddress());

            //获取
            HttpRequest request = (HttpRequest) msg;
            //获取uri，进行路径过滤
            URI uri = new URI(request.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico，不做响应");
            }
            //回复信息给浏览器（http协议）
            ByteBuf content= Unpooled.copiedBuffer("hello 我是服务器", CharsetUtil.UTF_8);
            //构造一个http响应，即HttpResponse
          FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
          response.headers().set(HttpHeaderNames.CONTENT_TYPE,"txt/plain");
          response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
          //将构建好的reponse返回
            channelHandlerContext.writeAndFlush(response);

        }

    }
}
