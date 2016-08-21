package batchProcessor;

import org.w3c.dom.Element;

public class FileCommand extends Command {
	String path;
	
	@Override
	public String describe() {		
		return "A File command with path"+path+"is executed.";
	}

	
	@Override
	public void parse(Element element) throws ProcessException {
		id = element.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in CMD Command");
		}
		//System.out.println("ID: " + id);

		path = element.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in CMD Command");
		}
	//	System.out.println("Path: " + path);

		
	}

	

}
