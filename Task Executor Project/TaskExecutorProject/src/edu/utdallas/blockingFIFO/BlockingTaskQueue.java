package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

public class BlockingTaskQueue {

	private int front;
	private int rear;
	private int size = 100;
	Task[] TaskQueue;

	private static BlockingTaskQueue queueObj;

	private BlockingTaskQueue() {

		TaskQueue = new Task[size];
		front = -1;
		rear = -1;
	}

	public static BlockingTaskQueue getInstance() {
		if (queueObj == null) {
			queueObj = new BlockingTaskQueue();
		}
		return queueObj;
	}

	public void add(Task task) {
		enQueue(task);

	}

	public Task take() {
		return deQueue();

	}

	public boolean isempty() {
		return (front == -1 && rear == -1);
	}

	public void enQueue(Task task) {

		try {

			synchronized (this) {

				while ((rear + 1) % size == front) {
					wait();

				}
				if (isempty()) {
					front++;
					rear++;
					TaskQueue[rear] = task;
				} else {
					rear = (rear + 1) % size;
					TaskQueue[rear] = task;
				}
				notifyAll();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	public Task deQueue() {
		Task task = null;
		try {
			synchronized (this) {
				while (isempty()) {
					/* Queue is empty
					Wait until new tasks are added to queue */
					wait();
				}
				if (front == rear) {
					task = TaskQueue[front];
					front = -1;
					rear = -1;

				} else {
					task = TaskQueue[front];
					front = (front + 1) % size;
					
				}
				notifyAll();
			}

		} catch (Exception e) {
			/* System.out.println("Exception while dequeing"); */
		}

		return task;

	}

}
