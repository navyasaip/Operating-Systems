package batchProcessor;

import java.util.HashMap;
import java.util.Map;

public class Batch {
	private String workingDir;
	Map<String, Command> commandMap;

	public Batch() {
		commandMap = new HashMap<String, Command>();
	}

	public String getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(String workingDir) {
		this.workingDir = workingDir;
	}

	public void addCommand(Command command) {
		commandMap.put(command.id, command);
	}
}
