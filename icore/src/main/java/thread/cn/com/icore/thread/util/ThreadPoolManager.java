package cn.com.icore.thread.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadPoolManager {
	public static int DEFAULT_POOL_SIZE = 5;
	public static int POOL_SIZE = 0;

	/**
	 * 空闲线程
	 */
	private Queue<WorkThread> idleThread;

	/**
	 * 任务队列
	 */
	private Queue<Task> taskQueue;

	/**
	 * 线程池大小
	 */
	private int poolSize;

	public ThreadPoolManager() {
		this(DEFAULT_POOL_SIZE);
	}

	public ThreadPoolManager(int poolSize) {
		if (poolSize < 0) {
			this.poolSize = DEFAULT_POOL_SIZE;
		} else {
			this.poolSize = poolSize;
		}
		idleThread = new ConcurrentLinkedQueue<WorkThread>();
		taskQueue = new ConcurrentLinkedQueue<Task>();
		init();
	}

	/**
	 * 初始化线程池，新建 N 个空闲线程
	 * 
	 */
	private void init() {
		System.out.println("启动线程池...");
		synchronized (taskQueue) {
			for (int i = 0; i < poolSize; i++) {
				WorkThread workThread = new WorkThread(this, taskQueue,
						"Thread " + i);
				idleThread.add(workThread);
				POOL_SIZE++;
				workThread.start();
			}
		}
	}

	/**
	 * 关闭线程池，关闭线程池中各个线程 在调用该方法后，线程并没有马上关闭，而是在线程任务执行完之后关闭
	 * 
	 */
	public void shutDown() {
		System.out.println("关闭线程池...");
		synchronized (taskQueue) {
			for (WorkThread thread : idleThread) {
				thread.shutDown();
			}
		}
	}

	/**
	 * 添加任务并唤醒各因无任务而等待的空闲线程
	 * 
	 * @param task
	 * @throws Exception
	 */
	public void addTask(Task task) throws Exception {
		synchronized (taskQueue) {
			taskQueue.add(task);
			taskQueue.notifyAll();
		}
	}

	/**
	 * 获取空闲线程，当线程池内无空闲线程时等待
	 * 
	 * @return
	 * @throws Exception
	 */
	public WorkThread getIdleThread() throws Exception {
		if (idleThread.isEmpty()) {
			System.out.println("获取空闲线...");
			idleThread.wait();
		}
		synchronized (idleThread) {
			return idleThread.poll();
		}
	}

	/**
	 * 释放线程
	 * 
	 * @param thread
	 */
	public void releaseThread(WorkThread thread) {
		System.out.println("释放线程 [" + thread.getName() + "] ...");
		synchronized (idleThread) {
			idleThread.add(thread);
			idleThread.notifyAll();
		}
	}
}
