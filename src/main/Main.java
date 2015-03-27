package main;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		File fileTraining;
		File fileTesting;
		try {
			if (args.length != 2) {
				throw new InvalidArgumentsError(
						"This program must be given 2 filenames as parameters");

			}
			if (!args[0].startsWith("-") || !args[1].startsWith("-")) {
				throw new InvalidArgumentsError(
						"The parameters must start with '-'");

			}
		} catch (InvalidArgumentsError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fileTraining = new File(args[0]);
		fileTesting = new File(args[1]);

	}
}
