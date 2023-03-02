package com.wy.operating_room.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "operation")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Operation implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String pid;

  private String location;

  private String diagnose;

  private String name;

  private String anesthesiologist;

  @Column(name = "circulating_nurse")
  private String circulatingNurse;

  private String operator;

  private String supporter;

  private String status;

  @Column(name = "start_time")
  private java.util.Date startTime;

  @Column(name = "end_time")
  private java.util.Date endTime;


  @Transient
  private String pname;
  @Transient
  private String dept;
  @Transient
  private int bed;
}
