package com.mpaike.util;

/**
 * Created with IntelliJ IDEA.
 * User: Chen
 * Date: 12-8-29
 * Time: 下午9:00
 * To change this template use File | Settings | File Templates.
 */
public class Message {
    
    public static final String CALLBACKTYPE_CLOSECURRENT = "closeCurrent";
    public static final String CALLBACKTYPE_FORWARD = "forward";
    
    private String statusCode;
    private String message;
    private String navTabId;
    private String rel;
    private String callbackType;
    private String forwardUrl;
    private String confirmMsg;

    public Message() {
    }

    public Message(String navTabId) {
        this.navTabId = navTabId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getNavTabId() {
        return navTabId;
    }

    public void setNavTabId(String navTabId) {
        this.navTabId = navTabId;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }
    
    public String getConfirmMsg() {
        return confirmMsg;
    }

    public void setConfirmMsg(String confirmMsg) {
        this.confirmMsg = confirmMsg;
    }
    
}
