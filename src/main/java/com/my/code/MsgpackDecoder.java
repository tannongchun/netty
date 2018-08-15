package com.my.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @version 1.0
 * @description:
 *     解码
 * @projectName: com.my.code
 * @className: myrpccode
 * @author:谭农春
 * @createTime:2018/8/14 21:18
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

  @Override
  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
try {
  final byte[] array;
  final int length = byteBuf.readableBytes();
  array = new byte[length];
  byteBuf.getBytes(byteBuf.readerIndex(), array, 0, length);
  MessagePack messagePack = new MessagePack();
  // 解码到集合中
  list.add(messagePack.read(array));
}catch (Exception e){
  e.printStackTrace();
}
  }
}
