package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.*;
import edu.utdallas.blockingFIFO.*;

public class TaskExecutorImpl implements TaskExecutor {

	int num_threads;
	TaskRunner[] runnerPool = new TaskRunner[10];

	public TaskExecutorImpl(int n) {
		try {
			num_threads = n;
			
			for (int i = 0; i < num_threads; i++) {
				runnerPool[i] = new TaskRunner();
				new Thread(runnerPool[i]).start();
				
			}
		} catch (Exception e) {
			//System.out.println("thread exception");

		}
	}

	public void addTask(Task task) {
		
		BlockingTaskQueue.getInstance().add(task);

	}

	
}
