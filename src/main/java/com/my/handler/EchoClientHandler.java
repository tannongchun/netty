package com.my.handler;


import com.my.data.User;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 * @version 1.0
 * @description:
 * @projectName: com.my.handler
 * @className: myrpccode
 * @author:谭农春
 * @createTime:2018/8/14 23:26
 */
public class EchoClientHandler extends ChannelHandlerAdapter {

  private final int sendNumber ;

  public EchoClientHandler(int sendNumber) {
    this.sendNumber = sendNumber;
  }


  private User[]  users(){

    User[] users = new User[sendNumber];
    User u =null;
    for(int i = 0 ;i <sendNumber ;i ++){
      u  = new User();
      u.setId(i);
      u.setName(" transport  is " + i );
      users[i] = u ;
    }
    return users;
  }

 @Override
  public  void channelActive(ChannelHandlerContext ctx){
 // 激活
   User [] users =users();
   for( User u : users){
     ctx.write(u);
   }
   // 刷新
   ctx.flush();

 }

  @Override
  public  void channelRead(ChannelHandlerContext ctx,Object msg){
  System.out.println( " Client receive the msgpack message :" + msg);
    ctx.write(msg);
  }

  @Override
  public  void channelReadComplete(ChannelHandlerContext ctx ){
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    // Close the connection when an exception is raised.
    cause.printStackTrace();
    ctx.close();
  }


}
