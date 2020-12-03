package com.atguigu.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
       if (evt instanceof IdleStateEvent){
           //将evt向下转型IdleStateEvent
           IdleStateEvent event=(IdleStateEvent)evt;
           String eventTye = null;
           switch (event.state()){
               case READER_IDLE:
                   eventTye="读空闲";
                   break;
               case WRITER_IDLE:
                   eventTye="写空闲";
                   break;
               case ALL_IDLE:
                   eventTye="读写空闲";
                   break;
           }
           System.out.println(ctx.channel().remoteAddress() +"---超时时间--" + eventTye);
           System.out.println("服务器做相应处理。。");
       }
    }
}
