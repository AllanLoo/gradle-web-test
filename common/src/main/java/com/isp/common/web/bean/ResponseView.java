package com.isp.common.web.bean;

/**
 * Created by Administrator on 2015/9/7.
 * ϵͳ��Ӧʵ���࣬����ajax����
 */
public class ResponseView {
    /**
     * ��������Ӧ�쳣
     */
    public final static int SERVER_RESPONSE_ERROR = 500;
    /**
     * ��������Ӧ�ɹ�
     */
    public final static int SERVER_RESPONSE_SUCCESS = 200;

    /**
     * ״̬
     */
    private int status;
    /**
     * ��Ϣ
     */
    private String message;
    /**
     * ����
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
