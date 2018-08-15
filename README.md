# 备注记录
## 粘包半包问题 
- Tcp 基于字节流的传输层通信协议，把数据流分区成适当长度的报文段，报文段长度有限，当传输大量数据，
需要将大报文拆分成为小报文对传输的报文进行粘包和拆包。
- 粘包半包产生原因（来自Netty 权威指南第二版）
    ````
        (1)应用程序write写入的字节大小大于套接口发送缓冲区大小;
        (2)进行MSS(最大传输段大小)大小的TCP分段;
        (3)以太网帧的payload大于MTU(数据链路层的最大传送单元)进行IP分片。
     ````   

### 粘包和半包解决方案
- （1）消息长度固定：累计读取到固定长度为LENGTH之后就认为读取到了一个完整的消息。然后将计数器复位，重新开始读下一个数据报文。

- （2）回车换行符作为消息结束符：在文本协议中应用比较广泛。

- （3）将特殊的分隔符作为消息的结束标志，回车换行符就是一种特殊的结束分隔符。

- （4）通过在消息头中定义长度字段来标示消息的总长度。

### 对应的编码解码器 

- （1）通过FixedLengthFrameDecoder 定长解码器来解决定长消息的黏包问题；

- （2）通过LineBasedFrameDecoder和StringDecoder来解决以回车换行符作为消息结束符的TCP黏包的问题；

- （3）通过DelimiterBasedFrameDecoder 特殊分隔符解码器来解决以特殊符号作为消息结束符的TCP黏包问题；

- （4）最后一种，也是本文的重点，通过LengthFieldBasedFrameDecoder 自定义长度解码器解决TCP黏包问题。
 

###  LengthFieldBasedFrameDecoder/LengthFieldPrepender 
- 自定义长度解码器解决TCP粘包问题 

### LineBasedFrameDecoder/StringDecoder
 - StringDecoder和LineBasedFrameDecoder的的组合就是按行切换的文本解码器
 
### 项目jar依赖 
   ```
          <dependencies>
            <!-- https://mvnrepository.com/artifact/org.msgpack/msgpack -->
            <dependency>
              <groupId>org.msgpack</groupId>
              <artifactId>msgpack</artifactId>
              <version>0.6.12</version>
            </dependency>
            <!-- https://mvnrepository.com/artifact/org.jboss.marshalling/jboss-marshalling-river -->
            <dependency>
              <groupId>org.jboss.marshalling</groupId>
              <artifactId>jboss-marshalling-river</artifactId>
              <version>1.4.11.Final</version>
            </dependency>
        
            <!-- https://mvnrepository.com/artifact/io.netty/netty-all -->
            <dependency>
              <groupId>io.netty</groupId>
              <artifactId>netty-all</artifactId>
              <version>5.0.0.Alpha1</version>
            </dependency>
        
   ```
项目下载地址[Github]();   

 
 