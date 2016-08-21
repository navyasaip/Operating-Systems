package batchProcessor;

import org.w3c.dom.Element;

public abstract class Command {
	String id;
	public abstract String describe();
	
	public abstract void parse(Element element) throws ProcessException;
	

}
