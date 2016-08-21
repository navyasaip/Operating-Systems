package batchProcessor;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class BatchParser {
	Batch buildBatch(String batchFileName) {
		Batch batch = new Batch();
		try {
			File f = new File(batchFileName);

			FileInputStream fis = new FileInputStream(f);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fis);

			Element pnode = doc.getDocumentElement();
			NodeList nodes = pnode.getChildNodes();

			for (int idx = 0; idx < nodes.getLength(); idx++) {
				Node node = nodes.item(idx);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					//System.out.println(node.getNodeName());
					if (node.getNodeName().equals("pipe")) {
						NodeList pipeNodes = node.getChildNodes();
						for (int idx1 = 0; idx1 < pipeNodes.getLength(); idx1++) {
							Node cmdNode = pipeNodes.item(idx1);
							if (cmdNode.getNodeType() == Node.ELEMENT_NODE) {
								//System.out.println("Parsing "+cmdNode.getNodeName());
								Element elem1 = (Element) cmdNode;
								Command command = buildCommand(elem1);
								batch.addCommand(command);
							}
						}
					}
					Element elem = (Element) node;
					Command command = buildCommand(elem);
					batch.addCommand(command);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return batch;
	}

	private Command buildCommand(Element elem) throws DOMException,
			ProcessException {
		String cmdName = elem.getNodeName();
		Command cmd = null;
		if (cmdName == null) {
			throw new ProcessException("unable to parse command from "
					+ elem.getTextContent());
		} else if ("wd".equalsIgnoreCase(cmdName)) {
			System.out.println("Parsing wd");
			cmd = new WDCommand();
			cmd.parse(elem);

		} else if ("file".equalsIgnoreCase(cmdName)) {
			System.out.println("Parsing file");
			cmd = new FileCommand();
			cmd.parse(elem);
			// Command cmd = FileCommand.parse(elem);
		} else if ("cmd".equalsIgnoreCase(cmdName)) {
			System.out.println("Parsing cmd");
			cmd = new CmdCommand();
			cmd.parse(elem);

			// Command cmd = CmdCommand.parse(elem);
			// parseCmd(elem); // Example of parsing a cmd element
		} else if ("pipe".equalsIgnoreCase(cmdName)) {
			System.out.println("Parsing pipe");
			cmd = new PipeCommand();
			cmd.parse(elem);
			// Command cmd = PipeCommand.parse(elem);
		} else {
			throw new ProcessException("Unknown command " + cmdName + " from: "
					+ elem.getBaseURI());
		}
		return cmd;

	}

}
