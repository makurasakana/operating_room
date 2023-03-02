package com.wy.operating_room.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "report")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Report implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String uid;

  private String oid;

  private String type;

  private String detail;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private java.util.Date time;

  private String wasted;

  @Transient
  private  String state;
  @Transient
  private  String name;
  @Transient
  private  String location;
}
