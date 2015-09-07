package com.isp.common.web.bean;

/**
 * Created by Administrator on 2015/9/7.
 * 系统响应实体类，用于ajax返回
 */
public class ResponseView {
    /**
     * 服务器响应异常
     */
    public final static int SERVER_RESPONSE_ERROR = 500;
    /**
     * 服务器响应成功
     */
    public final static int SERVER_RESPONSE_SUCCESS = 200;

    /**
     * 状态
     */
    private int status;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private Object data;

    public ResponseView(){

    }
    public ResponseView(int status,String message){
        this.status = status;
        this.message = message;
    }

    public ResponseView(Object data){
        this.status = SERVER_RESPONSE_SUCCESS;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
