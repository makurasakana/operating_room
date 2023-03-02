package com.wy.operating_room.entity;

import lombok.Data;

@Data
public class Result {
    public int code;
    public String msg;
    public int count;
    public int page;
    public int limit;
    public Object data;
}
