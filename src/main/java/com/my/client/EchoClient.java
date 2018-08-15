package com.my.client;

import com.my.code.MsgpackDecoder;
import com.my.code.MsgpackEncoder;
import com.my.handler.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.concurrent.GenericFutureListener;


/**
 * @version 1.0
 * @description:
 * @projectName: com.my.client
 * @className: myrpccode
 * @author:谭农春
 * @createTime:2018/8/14 21:24
 */
public class EchoClient {

  private final String host;
  private final int port;
  private final int sendNumber;

  public EchoClient(String host, int port, int sendNumber) {
    this.host = host;
    this.port = port;
    this.sendNumber = sendNumber;
  }

  public void run ()throws Exception {
   // 配置客户端
    EventLoopGroup group = new NioEventLoopGroup();
    try{
      // 客户端辅助类
      Bootstrap bootstrap = new Bootstrap();
      // 通过option 配置TCP参数
      bootstrap.group(group).
          channel(NioSocketChannel.class)
          .option(ChannelOption.TCP_NODELAY,true)
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
          .handler(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              // 粘包/半包处理
              socketChannel.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
              // 添加处理通道
              socketChannel.pipeline().addLast("msgpack decoder",new MsgpackDecoder());
              socketChannel.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
              // 解码
              socketChannel.pipeline().addLast("msgpack encoder",new MsgpackEncoder());
              // 自定义处理器
              socketChannel.pipeline().addLast(new EchoClientHandler(sendNumber));

            }
          });
        // 发起异步回调
      ChannelFuture f = bootstrap.connect(host,port).sync();

      f.addListener(new GenericFutureListener<ChannelFuture>() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
          if(future.isSuccess()){
            System.out.println("Client is running success ");
          }

        }
      });
      // 等待关闭
      f.channel().closeFuture().sync();

    }finally {
      // 主动释放资源
      group.shutdownGracefully();
    }

  }

}
