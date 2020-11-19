package com.atguigu.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * 自定义一个handle,需要继承netty封装（规定）好的某个适配器
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    //读取实际数据
    //ChannelHandlerContext ctx：上下文对象，含有管道pipeline，通道channel，地址
    //Object msg客户端发送的数据，默认是Object
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx"+ctx);
        //将msg转成一个ByteBuf
        //ByteBuf是netty提供的，不是nio提供的ByteBUffer
        ByteBuf buf=(ByteBuf) msg;
        System.out.println("客户端发送消息是"+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址是"+ctx.channel().remoteAddress());
        //任务队列,用户自定义的普通任务，任务提交到NIOEventLoop的taskQueue中
        ctx.channel().eventLoop().execute(new Runnable(){
            @Override
            public void run() {
                //...
                try {
                    Thread.sleep(10*1000);
                    //将数据写到缓冲区并刷新
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello world2",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常"+e.getMessage());
                }
            }
        });
        //任务提交到scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable(){
            @Override
            public void run() {
                //...
                try {
                    Thread.sleep(5*1000);
                    //将数据写到缓冲区并刷新
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello world3",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常"+e.getMessage());
                }
            }
        },5, TimeUnit.SECONDS);
   }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓冲并刷新
       ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端",CharsetUtil.UTF_8));
    }

    /**
     * 处理异常，关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
