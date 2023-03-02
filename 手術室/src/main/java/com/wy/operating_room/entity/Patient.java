package com.wy.operating_room.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "patient")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Patient implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "id_card")
  private String idCard;

  private String name;

  private String sex;

  private Integer age;

  private String dept;

  private Integer bed;

  private String state;

  private String allergy;

  private String history;


}
