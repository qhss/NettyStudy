package com.atguigu.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    //定义一个Channel组，管理所有的Channel
    //GlobalEventExecutor.INSTANCE是全局事件执行器，是一个单例
    private static ChannelGroup channelGroup=new  DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ss HH:mm:ss");

    //此方法表示建立连接，一旦建立连接，就第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //该方法会将 channelGroup 中所有 channel 遍历，并发送消息，而不需要我们自己去遍历
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + sdf.format(new Date()) + "加入聊天\n");
        //将当前的Channel加入到 ChannelGroup
        channelGroup.add(channel);
    }


    //表示channel处于活跃状态，提示xxx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + "上线了~");
    }

    //表示channel处于不活跃状态，提示xxx下线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " " + sdf.format(new Date()) + "离线了~");
    }

    //表示channel断开连接，将xxx客户离开信息推送给在线客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() +" "+ sdf.format(new Date()) + "离开了\n");
        System.out.println("当前channelGroup大小 ：" + channelGroup.size());
    }

    /**
     *　
     * @param ctx
     * @param msg
     * @throws Exception
     */
    //读取数据，并进行消息转发
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取当前channel
        Channel channel = ctx.channel();
        //遍历channelGroup，根据不同的情况回送不同的消息
        channelGroup.forEach(item->{
            if (item!=channel){
                item.writeAndFlush("[客户]" + channel.remoteAddress() + "发送了消息：" + msg + "\n");
            }else{
                item.writeAndFlush("[自己]发送了消息"+msg+"\n");
            }
        });

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
