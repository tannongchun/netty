package com.my.data;

import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * @version 1.0
 * @description:
 *  传递的数据
 * @projectName: com.my.data
 * @className: myrpccode
 * @author:若水
 * @createTime:2018/8/14 23:45
 */
@Message
public class User  implements Serializable{

  private Integer id ;
  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString(){
    return "id => "+id + "; name => " +name;
  }
}
