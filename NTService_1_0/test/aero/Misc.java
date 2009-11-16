package aero;

import org.junit.Test;

import aero.nettracer.serviceprovider.wt_1_0.services.ishares.service.CommandNotProperlyFormedException;

public class Misc {

	@Test
	public void testThis2() throws CommandNotProperlyFormedException {
		String command = "Test [asdf]\nTest2 [test3]";
		command = testCommand(command);
		System.out.println(command);
	}
	
	private static String testCommand(String command) throws CommandNotProperlyFormedException {
		if (command.contains("{") || command.contains("}")) throw new CommandNotProperlyFormedException();
		command = command.replaceAll("\\[.*\\]", "");
		return command;
	}
}
