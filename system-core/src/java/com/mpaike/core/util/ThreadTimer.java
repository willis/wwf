package com.mpaike.core.util;

public class ThreadTimer extends Thread
{
  Thread target;
  int timeout;

  public void run()
  {
    try
    {
      sleep(this.timeout);
    } catch (InterruptedException ie) {
      return;
    }
    this.target.interrupt();
  }

  public ThreadTimer(Thread target, int timeout)
  {
    this.target = target;
    this.timeout = timeout;
  }
}