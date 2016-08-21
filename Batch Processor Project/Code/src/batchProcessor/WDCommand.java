package batchProcessor;

import org.w3c.dom.Element;

public class WDCommand  extends Command{
	
	String path;
	@Override
	public String describe() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void parse(Element element) throws ProcessException {
		id = element.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in WD Command");
		}
		//System.out.println("ID: " + id);

		path = element.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in WD Command");
		}
		//System.out.println("Path: " + path);

				
	}

	

}
