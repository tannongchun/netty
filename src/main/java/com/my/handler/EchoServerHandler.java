package com.my.handler;


import com.my.data.User;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @version 1.0
 * @description:
 * @projectName: com.my.handler
 * @className: myrpccode
 * @author:谭农春
 * @createTime:2018/8/14 23:26
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelHandlerAdapter {


  public EchoServerHandler() {
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg)
      throws Exception {
    System.out.println("Receive client : [" + msg + "]");
    // 返回客户端
    ctx.writeAndFlush(msg);
  }

  @Override
  public  void channelReadComplete(ChannelHandlerContext ctx ){
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();// 发生异常，关闭链路
  }


}
