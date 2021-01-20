package com.atguigu.nio.groupchat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {
    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int port=6667;

    //构造方法，初始化工作
    public GroupChatServer(){
        try {
            //得到选择器
            selector=Selector.open();
            //得到ServerSocketChannel
            listenChannel=ServerSocketChannel.open();
            //绑定端口6667
            listenChannel.socket().bind(new InetSocketAddress(port));
            //设置为非阻塞
            listenChannel.configureBlocking(false);
            //将listenChannel注册到selector上
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //监听
    public void listen(){
        try {

            //进行循环处理
            while(true){
                int count = selector.select(2000);
                if (count>0){//有事件要处理
                    //遍历得到的selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        //取出selectionKey
                        SelectionKey key = iterator.next();
                        //监听到accept
                        if (key.isAcceptable()){
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //将sc注册到selector上
                            sc.register(selector, SelectionKey.OP_READ);
                            //提示
                            System.out.println(sc.getRemoteAddress()+"上线");
                        }
                        if (key.isReadable()){//通道发生read事件，即通道是可读状态
                            //处理读事件（专门写方法）
                            readData(key);
                        }
                        //手动从集合中移除当前的selectionKey,防止重复操作
                        iterator.remove();

                    }
                }else{
//                    System.out.println("等待中");
                }
            }

        }catch (Exception e){

        }
        finally {

        }
    }

//  读取客户端消息
    private void readData(SelectionKey key){
        //定义一个socketChannel
        SocketChannel channel=null;
        try{
            //取到关联的channel
             channel = (SocketChannel)key.channel();
             //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count的值进行处理
            if (count>0){
                //把缓冲区的数据转成字符串
                String msg = new String(buffer.array());
                //输出该消息
                System.out.println("from 客户端"+msg);
                //向其他的客户端转发消息(去掉自己)，单独写一个方法
                sendInfoToOtherClients(msg,channel);
            }
        }catch (IOException e){
            try {
                System.out.println(channel.getRemoteAddress()+"离线了。。");
                //取消祖册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //转发消息给其他客户（通道）
    private void sendInfoToOtherClients(String msg,SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中。。。。");
        //遍历所有注册到selectpr上的SocketChannel,并排除自己（self）
        for (SelectionKey key:selector.keys()){
            //通过key取出对应的socketChannel
            Channel targerChannel = key.channel();
            //排除自己
            if (targerChannel instanceof SocketChannel&&targerChannel!=self){
                //转型
                SocketChannel desk=(SocketChannel)targerChannel;
                //将msg写入到buffer中
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                desk.write(buffer);

            }

        }

    }

    /**
     * ServerSocketChannel()
     * selector
     *
     * @param args
     */
    public static void main(String[] args) {

        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();

    }
}
