package com.my.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @version 1.0
 * @description:
 *     Netty 与第三方编码集成
 * @projectName: com.my.code
 * @className: myrpccode
 * @author:谭农春
 * @createTime:2018/8/14 21:13
 */
public class MsgpackEncoder  extends MessageToByteEncoder<Object>{
  @Override
  protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
    try {
      MessagePack pack = new MessagePack();
      // 序列化 将对象转成字节
      byte[] raw = pack.write(o);
      byteBuf.writeBytes(raw);
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
