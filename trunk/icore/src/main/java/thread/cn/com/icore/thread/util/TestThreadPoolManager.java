package cn.com.icore.thread.util;

import junit.framework.TestCase;

public class TestThreadPoolManager extends TestCase {
	public void testManager() throws Exception {
		ThreadPoolManager pool = new ThreadPoolManager(10);
		for (int i = 0; i < 10; i++) {
			pool.addTask(new SimpleTask(i));
		}
		Thread.sleep(1000 * 10);
		pool.shutDown();
	}
}
