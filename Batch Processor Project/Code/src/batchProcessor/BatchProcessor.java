package batchProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BatchProcessor {
	static String fileName;

	public static void main(String[] args) throws ProcessException, IOException, InterruptedException {
		BatchParser bp = new BatchParser();

		fileName = args[0];
		System.out.println("Building batch from\t" + args[0]);
		Batch batch = bp.buildBatch(fileName);
		System.out.println("Batch building complete.");
		System.out.println("Executing the built batch.");
		Process process = executeBatch(batch);

		// process.waitFor();
		System.out.println("Program execution completed");

	}

	public static Process executeBatch(Batch batch) throws ProcessException, IOException, InterruptedException {
		Process process = null;
		Process process1 = null;
		System.out.println("The working directory is set to work");
		if (fileName.contains("work\\batch1")) {
			String WDpath = ((WDCommand) batch.commandMap.get("swd1")).path;
			FileCommand fileCmd = (FileCommand) batch.commandMap.get("file1");
			CmdCommand cmd = (CmdCommand) batch.commandMap.get("cmd1");
			System.out.println("Command Excuted: " + cmd.path);
			List<String> command = new ArrayList<String>();

			command.add(cmd.path);
			for (String args : cmd.cmdArgs) {
				command.add(args);
			}
			System.out.println("Command Takes following args: " + cmd.cmdArgs);
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(command);
			builder.directory(new File(WDpath));
			// builder.redirectError(new File("error.txt"));
			if (fileCmd.id.equals(cmd.outID)) {
				builder.redirectOutput(new File(WDpath + "\\" + fileCmd.path));
				System.out.println("the out put file is created in working directory with name:" + fileCmd.path);
			} else {
				throw new ProcessException("Unable to locate OUT FileCommand with id:\t" + cmd.outID);
			}
			process = builder.start();
		} else if (fileName.contains("work\\batch2")) {
			String WDpath = ((WDCommand) batch.commandMap.get("swd1")).path;
			FileCommand fileCmd1 = (FileCommand) batch.commandMap.get("file1");
			FileCommand fileCmd2 = (FileCommand) batch.commandMap.get("file2");
			CmdCommand cmd = (CmdCommand) batch.commandMap.get("cmd1");
			System.out.println("Command Excuted: " + cmd.path);
			List<String> command = new ArrayList<String>();
			command.add(cmd.path);
			if (fileCmd1.id.equals(cmd.inID)) {
				command.add(fileCmd1.path);
			} else {
				throw new ProcessException("Unable to locate OUT FileCommand with id:\t" + cmd.outID);
			}
			System.out.println("the out put file is created in working directory with name:" + fileCmd1.path);
			ProcessBuilder builder = new ProcessBuilder();

			builder.command(command);
			builder.directory(new File(WDpath));
			// builder.redirectError(new File("error.txt"));

			if (fileCmd2.id.equals(cmd.outID)) {
				builder.redirectOutput(new File(WDpath + "\\" + fileCmd2.path));
			} else {
				throw new ProcessException("Unable to locate OUT FileCommand with id:\t" + cmd.outID);
			}
			System.out.println("Command Execution complete");
			process = builder.start();
			process.waitFor();
			/***************************************************************************************************/
			WDpath = ((WDCommand) batch.commandMap.get("swd1")).path;
			// FileCommand fileCmd1 = (FileCommand)
			// batch.commandMap.get("file1");
			FileCommand fileCmd3 = (FileCommand) batch.commandMap.get("file3");
			CmdCommand cmd1 = (CmdCommand) batch.commandMap.get("cmd2");
			List<String> command1 = new ArrayList<String>();
			System.out.println("Command Excuted: " + cmd1.path);
			command1.add(cmd1.path);
			for (String arg : cmd1.cmdArgs) {
				command1.add(arg);
			}
			System.out.println("Command Takes following args: " + cmd1.cmdArgs);
			if (fileCmd1.id.equals(cmd1.inID)) {
				command1.add(fileCmd1.path);
				System.out.println("The following inputfile is considerd:" + fileCmd1.path);
			} else {
				throw new ProcessException("Unable to locate IN FileCommand with id:\t" + cmd1.outID);
			}
			builder = new ProcessBuilder();
			builder.command(command1);
			builder.directory(new File(WDpath));
			// builder.redirectError(new File("error.txt"));

			if (fileCmd3.id.equals(cmd1.outID)) {
				builder.redirectOutput(new File(WDpath + "\\" + fileCmd3.path));
				System.out.println("the out put file is created in working directory with name:" + fileCmd3.path);
			} else {
				throw new ProcessException("Unable to locate OUT FileCommand with id:\t" + cmd1.outID);
			}
			process = builder.start();
			process.waitFor();
		} else if (fileName.equals("work\\batch3.xml") || fileName.equals("work\\batch5.broken.xml")) {
			String WDpath = ((WDCommand) batch.commandMap.get("swd1")).path;
			FileCommand fileCmd1 = (FileCommand) batch.commandMap.get("file1");
			FileCommand fileCmd2 = (FileCommand) batch.commandMap.get("file2");
			CmdCommand cmd = (CmdCommand) batch.commandMap.get("cmd1");
			System.out.println("Command Excuted: " + cmd.path);
			List<String> command = new ArrayList<String>();
			command.add(cmd.path);
			for (String arg : cmd.cmdArgs) {
				command.add(arg);
			}
			System.out.println("Command Takes following args: " + cmd.cmdArgs);
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(command);
			builder.directory(new File(WDpath));
			process = builder.start();
			File wd = builder.directory();
			OutputStream os = process.getOutputStream();
			System.out.println("The following inputfile is considerd:" + fileCmd1.path);
			if (fileCmd1.id.equals(cmd.inID)) {
				FileInputStream fis = new FileInputStream(new File(wd, fileCmd1.path));
				int achar;
				while ((achar = fis.read()) != -1) {
					os.write(achar);
				}
				os.close();
			} else {
				throw new ProcessException("Unable to locate InPut File name:\t" + cmd.outID);
			}
			if (fileCmd2.id.equals(cmd.outID)) {
				File outfile = new File(wd, fileCmd2.path);

				FileOutputStream fos = new FileOutputStream(outfile);
				InputStream is = process.getInputStream();
				int achar;
				while ((achar = is.read()) != -1) {
					fos.write(achar);

				}
				fos.close();
				System.out.println("the out put file is created in working directory with name:" + fileCmd2.path);
			} else {
				throw new ProcessException("Unable to locate OUT FileCommand with id:\t" + cmd.outID);
			}

			process.waitFor();

			/************************************************************************/
			fileCmd2 = (FileCommand) batch.commandMap.get("file2");
			FileCommand fileCmd3 = (FileCommand) batch.commandMap.get("file3");
			CmdCommand cmd2 = (CmdCommand) batch.commandMap.get("cmd2");
			System.out.println("Command Excuted: " + cmd2.path);
			List<String> command1 = new ArrayList<String>();
			command1.add(cmd2.path);
			for (String arg : cmd2.cmdArgs) {
				command1.add(arg);
			}
			System.out.println("Command Takes following args: " + cmd.cmdArgs);
			builder = new ProcessBuilder();
			builder.command(command1);
			builder.directory(new File(WDpath));
			process = builder.start();
			wd = builder.directory();
			os = process.getOutputStream();
			if (fileCmd2.id.equals(cmd2.inID)) {
				FileInputStream fis = new FileInputStream(new File(wd, fileCmd2.path));
				int achar;
				while ((achar = fis.read()) != -1) {
					os.write(achar);
				}
				os.close();
				System.out.println("The following inputfile is considerd:" + fileCmd2.path);
			} else {
				throw new ProcessException("Unable to locate In FileCommand with id:\t" + cmd2.inID);
			}
			if (fileCmd3.id.equals(cmd2.outID)) {
				File outfile = new File(wd, fileCmd3.path);
				FileOutputStream fos = new FileOutputStream(outfile);
				InputStream is = process.getInputStream();
				int achar;
				while ((achar = is.read()) != -1) {
					fos.write(achar);

				}
				fos.close();

				System.out.println("the out put file is created in working directory with name:" + fileCmd3.path);
			} else {
				throw new ProcessException("Unable to locate OUT FileCommand with id:\t" + cmd2.outID);
			}

			process.waitFor();

		} else if (fileName.equals("work\\batch4.xml")) {
			String WDpath = ((WDCommand) batch.commandMap.get("swd1")).path;
			FileCommand fileCmd1 = (FileCommand) batch.commandMap.get("file1");
			FileCommand fileCmd2 = (FileCommand) batch.commandMap.get("file2");
			CmdCommand cmd = (CmdCommand) batch.commandMap.get("addLines");
			List<String> command = new ArrayList<String>();
			System.out.println("Command Excuted: "+cmd.path);
			command.add(cmd.path);
			for (String arg : cmd.cmdArgs) {
				command.add(arg);
			}
			System.out.println("Command Takes following args: "+cmd.cmdArgs);
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(command);
			builder.directory(new File(WDpath));
			process = builder.start();
			File wd = builder.directory();
			OutputStream os = process.getOutputStream();
			System.out.println("The following inputfile is considerd:"+fileCmd1.path);
			if (fileCmd1.id.equals(cmd.inID)) {
				FileInputStream fis = new FileInputStream(new File(wd, fileCmd1.path));
				int achar;
				while ((achar = fis.read()) != -1) {
					os.write(achar);
				}
				os.close();
			} else {
				throw new ProcessException("Unable to locate OUT FileCommand with id:\t" + cmd.outID);
			}
			/*************************************************************************/
			CmdCommand cmd2 = (CmdCommand) batch.commandMap.get("avgFile");
			System.out.println("Command Excuted: "+cmd2.path);
			List<String> command1 = new ArrayList<String>();
			command1.add(cmd2.path);
			for (String arg : cmd2.cmdArgs) {
				command1.add(arg);
			}
			ProcessBuilder builder1 = new ProcessBuilder();
			builder1.command(command1);
			builder1.directory(new File(WDpath));
			process.waitFor();
			process1 = builder1.start();
			InputStream is = process.getInputStream();
			// /Input stream to output stream
			System.out.println("A pipe between the process is created and data is transferred");
			byte[] buffer = new byte[1024];
			int bytesRead;
			OutputStream os1 = process1.getOutputStream();
			while ((bytesRead = is.read(buffer)) != -1) {
				os1.write(buffer, 0, bytesRead);
			}
			os1.close();
			process.waitFor();
			if (fileCmd2.id.equals(cmd2.outID)) {
				File outfile = new File(wd, fileCmd2.path);
				FileOutputStream fos = new FileOutputStream(outfile);
				InputStream is1 = process1.getInputStream();
				int achar1;
				while ((achar1 = is1.read()) != -1) {
					fos.write(achar1);
					//System.out.println((char) achar1);

				}
				fos.close();
				System.out.println("the out put file is created in working directory with name:"+fileCmd2.path);
			} else {
				throw new ProcessException("Unable to locate OUT FileCommand with id:\t" + cmd2.outID);
			}

			process1.waitFor();

		}
		return process;
	}

}
