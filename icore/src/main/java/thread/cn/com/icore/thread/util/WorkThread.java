package cn.com.icore.thread.util;

import java.util.Queue;

public class WorkThread extends Thread {
	/**
	 * 线程关闭的标识位
	 */
	private boolean shutDown = false;

	/**
	 * 线程池管理器
	 */
	ThreadPoolManager mgr;

	/**
	 * 任务队列
	 */
	private Queue<Task> taskQueue;

	public WorkThread(ThreadPoolManager mgr, Queue<Task> taskQueue, String name) {
		super(name);
		this.mgr = mgr;
		this.taskQueue = taskQueue;
	}

	@Override
	public void run() {
		while (!shutDown) {
			Task task;
			// 如果任务队列不为空，则取出一个任务并开始执行，否则线程等等
			if (!taskQueue.isEmpty()) {
				synchronized (taskQueue) {
					task = taskQueue.poll();
				}
				task.execute();
				// 任务执行完毕之后释放线程到空闲线程队列中
				mgr.releaseThread(this);
			} else {
				try {
					synchronized (taskQueue) {
						taskQueue.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void shutDown() {
		this.shutDown = true;
	}
}
