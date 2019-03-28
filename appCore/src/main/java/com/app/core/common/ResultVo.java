package com.app.core.common;

import java.util.HashMap;
import java.util.Map;

public class ResultVo {


    /**
     * 操作标识，1-成功，2-失败
     */
    private int operationFlag;

    /**
     * 操作成功
     */
    public static final int OPERATION_FLAG_SUCCESS = 1;

    /**
     * 操作失败
     */
    public static final int OPERATION_FLAG_FAIL = 0;

    /**
     * 返回信息，异常时，存放返回异常信息
     */
    private String retMessage;

    /**
     * 返回信息map，异常时，存放返回异常信息
     */
    private Map<String,Object> dataMap;

    public int getOperationFlag() {
        return operationFlag;
    }

    public void setOperationFlag(int operationFlag) {
        this.operationFlag = operationFlag;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void pushDataMap(String key ,Object value){
        if(null==dataMap){
            dataMap = new HashMap<String,Object>();
        }
        dataMap.put(key,value);
    }
}
