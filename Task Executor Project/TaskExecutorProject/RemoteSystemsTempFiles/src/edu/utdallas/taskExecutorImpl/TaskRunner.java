package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockingTaskQueue;
import edu.utdallas.taskExecutor.Task;

public class TaskRunner implements Runnable {

	public void run() {
		while (true) {
			Task task = BlockingTaskQueue.getInstance().take();
			try {
				while (task != null) /* Execute tasks obtained from BlockingFifo Queue */
					task.execute();

			} catch (Exception e) {
				// System.out.println("Exception in taskrunner");
			}
		}
	}
}
