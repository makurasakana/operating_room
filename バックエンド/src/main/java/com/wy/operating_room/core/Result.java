package com.wy.operating_room.core;

import lombok.Data;

@Data
public class Result<T> {
    private boolean success = true;
    private int status = 0;
    private T data;
    private String msg;

    public static Result ofsuccess(Object obj) {
        return new Result<Object>() {{
            setData(obj);
        }};
    }

    public static Result oflost(String _msg, int status) {
        return new Result<Object>() {{
            setMsg(_msg);
            setStatus(status);
        }};
    }

    public static Result oflost(int status) {
        return new Result<Object>() {{
            setStatus(status);
            String msg = Config.getMsg(status);
            setMsg(msg);
        }};
    }


    public boolean isSuccess() {
        return success;
    }
}
