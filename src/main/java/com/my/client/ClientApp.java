package com.my.client;

/**
 * @version 1.0
 * @description:
 * @projectName: com.my.client
 * @className: myrpccode
 * @author:谭农春
 * @createTime:2018/8/15 0:06
 */
public class ClientApp {
  public  static  void main(String [] args){
    EchoClient client  = new EchoClient("localhost",8080,100);
    try {
      client.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
