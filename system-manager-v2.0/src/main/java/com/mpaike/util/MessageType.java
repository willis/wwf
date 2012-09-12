package com.mpaike.util;

/**
 * Created with IntelliJ IDEA.
 * User: Chen
 * Date: 12-8-30
 * Time: 下午2:47
 * To change this template use File | Settings | File Templates.
 */
public enum MessageType{
    SUCCESS("200"),ERROR("300"),TIMEOUT("301");
    public String value;
    MessageType(String value){
        this.value = value;
    }
}