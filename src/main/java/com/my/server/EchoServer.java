package com.my.server;

import com.my.code.MsgpackDecoder;
import com.my.code.MsgpackEncoder;
import com.my.handler.EchoClientHandler;
import com.my.handler.EchoServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.logging.Logger;


/**
 * @version 1.0
 * @description:
 * @projectName: com.my.client
 * @className: myrpccode
 * @author:若水
 * @createTime:2018/8/14 21:24
 */
public class EchoServer {


  private final int port;
  public EchoServer( int port) {

    this.port = port;

  }

  public void run ()throws Exception {
   // 配置服务器
    EventLoopGroup acceptorGroup = new NioEventLoopGroup();
    EventLoopGroup IOGroup = new NioEventLoopGroup();
    try{
      // 服务端辅助类
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(acceptorGroup,IOGroup).
          channel(NioServerSocketChannel.class)
          // 传输的块大小
          .option(ChannelOption.SO_BACKLOG,100)
          // 设置打印级别
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(new ChannelInitializer<SocketChannel>(){
              @Override
              protected void initChannel(SocketChannel socketChannel) throws Exception {
                // 半包
                socketChannel.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
                // 添加处理通道
                socketChannel.pipeline().addLast("msgpack decoder",new MsgpackDecoder());

                socketChannel.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
                // 解码
                socketChannel.pipeline().addLast("msgpack encoder",new MsgpackEncoder());
                // 自定义处理器
                socketChannel.pipeline().addLast(new EchoServerHandler());
              }
          });
        // 发起异步回调
      ChannelFuture f = bootstrap.bind(port).sync();

      f.addListener(new GenericFutureListener<ChannelFuture>() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
          if(future.isSuccess()){
            System.out.println("Server is running success ");
          }
        }
      });
      // 等待关闭
      f.channel().closeFuture().sync();

    }finally {
      // 主动释放资源
      acceptorGroup.shutdownGracefully();
      IOGroup.shutdownGracefully();
    }

  }

}
