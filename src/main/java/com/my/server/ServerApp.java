package com.my.server;

/**
 * @version 1.0
 * @description:
 * @projectName: com.my.server
 * @className: myrpccode
 * @author:谭农春
 * @createTime:2018/8/15 0:08
 */
public class ServerApp {
  public  static  void main(String [] args){
    EchoServer server = new EchoServer(8080);
    try {
      server.run();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
