package com.wy.operating_room.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "status")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Status implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String uid;

  private String pid;

  private String oid;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private java.util.Date time;

  @Column(name = "systolic_pressure")
  private Integer systolicPressure;

  @Column(name = "diastolic_pressure")
  private Integer diastolicPressure;

  @Column(name = "heart_rate")
  private Integer heartRate;

  private Integer breath;

  private Double temperature;

  @Column(name = "SpO2")
  private Integer spo2;

  private String wasted;

  @Transient
  private String pname;
  @Transient
  private String uname;
}
