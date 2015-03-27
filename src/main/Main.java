package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		File fileTraining;
		File fileTesting;

		// First make sure that the arguments are valid
		try {
			if (args.length != 2) {
				throw new InvalidArgumentsError(
						"This program must be given 2 filenames as parameters");

			}
		} catch (InvalidArgumentsError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

		fileTraining = new File(args[0]);
		fileTesting = new File(args[1]);

		// START OF ALGORITHM
		try {
			Scanner scanTest = new Scanner(fileTesting);
			int numTotal = 0;
			int numCorrect = 0;

			// Loop through the test file
			while (scanTest.hasNext()) {
				// Read in the next test iris
				double sepLen = scanTest.nextDouble();
				double sepWid = scanTest.nextDouble();
				double petLen = scanTest.nextDouble();
				double petWid = scanTest.nextDouble();
				String actualType = scanTest.nextLine();

				// Values to store the nearest neighbour
				double closestDistance = Double.MAX_VALUE;
				String perceivedType = null;

				Scanner scanTrain = new Scanner(fileTraining);
				// Loop through the training file for each test iris
				while (scanTrain.hasNext()) {
					// Find the relative distances between the training set
					// values and the test set values
					double sepLenRel = sepLen - scanTrain.nextDouble();
					double sepWidRel = sepWid - scanTrain.nextDouble();
					double petLenRel = petLen - scanTrain.nextDouble();
					double petWidRel = petWid - scanTrain.nextDouble();
					String type = scanTrain.nextLine();

					// Find the distance (don't bother square rooting it to find
					// the actual distance as it only needs to be compared to
					// the other training set distances)
					double distance = (sepLenRel * sepLenRel)
							+ (sepWidRel * sepWidRel) + (petLenRel * petLenRel)
							+ (petWidRel * petWidRel);
					if (distance < closestDistance){
						perceivedType = type;
						closestDistance = distance;
					}
				}
				scanTrain.close();

				// Update numbers
				if (actualType.equals(perceivedType)) {
					numCorrect++;
				}
				numTotal++;
			}
			scanTest.close();
			// Print out the percentage that were correct
			System.out.println("Total number of test cases: " + numTotal);
			System.out.println("Total number correct: " + numCorrect);
			System.out.println("Percentage correct: " + (numCorrect*100/numTotal));
		} catch (FileNotFoundException e) {
			System.err.println("File was not found!");
			e.printStackTrace();
			System.exit(0);
		}
	}
}
