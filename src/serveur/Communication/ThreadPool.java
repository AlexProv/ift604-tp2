package serveur.Communication;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {

	private final BlockingQueue<Runnable> queue;
	private final Thread[] threads;

	public ThreadPool(int numThreads) {
		queue = new LinkedBlockingQueue<Runnable>();
		threads = new Thread[numThreads];

		int i = 0;
		for (Thread t : threads) {
			i++;
			t = new Worker("Thread "+i);
			t.start();
		}
	}
	
	public void addTask(Runnable runnable){
		try {
			queue.put(runnable);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class Worker extends Thread {
		
		public Worker(String name){
			super(name);
		}
		
		public void run() {
			while (true) {
				try {
					Runnable runnable = queue.take();
					runnable.run();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
