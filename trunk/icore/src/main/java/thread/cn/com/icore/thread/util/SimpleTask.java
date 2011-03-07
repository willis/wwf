package cn.com.icore.thread.util;

import java.util.Date;

public class SimpleTask implements Task {  
	  
    int num = 0;  
      
    public SimpleTask(int num) {  
        this.num = num;  
    }  
  
    public void execute() {  
        try {  
            System.out.println("[" + new Date() + "] Task[" + getNum() + "]:I have worked in thread [" + Thread.currentThread().getName() + "]");  
            Thread.sleep(Math.round(Math.random() * 1000));  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
  
    public int getNum() {  
        return num;  
    }  
  
    public void setNum(int num) {  
        this.num = num;  
    }  
}  
