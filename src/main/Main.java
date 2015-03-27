package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class Main {

	/**
	 * Run the main with 2 arguments, the training and test sets for the iris
	 * plant I only have this one method as there is not much code.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// First make sure that the arguments are valid
		try {
			if (args.length != 2) {
				throw new InvalidArgumentsError(
						"This program must be given  2 filenames as parameters");
			}
		} catch (InvalidArgumentsError e) {
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println("\n\nWhat k value would you like to use?");
		Scanner in = new Scanner(System.in);
		int k = in.nextInt();

		// Create the files
		File fileTraining = new File(args[0]);
		File fileTesting = new File(args[1]);

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
				double[] closestDistances = createMaxValueArray(k);
				String[] perceivedTypes = new String[k];

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

					// If this distance is in the closest k then save the
					// distance and the type as a nearest neighbour
					for (int count = 0; count < k; count++) {
						if (distance < closestDistances[count]) {
							shuffle(closestDistances, perceivedTypes, count);
							perceivedTypes[count] = type;
							closestDistances[count] = distance;
							break;
						}
					}
				}
				scanTrain.close();

				// Update numbers
				Map<String, Integer> typeMap = new HashMap<String, Integer>();
				for (String type : perceivedTypes) {
					if (typeMap.containsKey(type)) {
						typeMap.put(type, typeMap.get(type) + 1);
					} else {
						typeMap.put(type, 0);
					}
				}
				Set<Entry<String, Integer>> set = typeMap.entrySet();
				int majority = 0;
				String majorityType = null;
				for (Entry<String, Integer> entry : set) {
					if (entry.getValue() > majority) {
						majority = entry.getValue();
						majorityType = entry.getKey();
					}
				}
				if (actualType.equals(majorityType)) {
					numCorrect++;
				}
				numTotal++;
			}
			scanTest.close();

			// Print out the percentage that were correct
			System.out.println("Total number of test cases: " + numTotal);
			System.out.println("Total number correct: " + numCorrect);
			System.out.println("Percentage correct: "
					+ (numCorrect * 100 / numTotal));

		} catch (FileNotFoundException e) {
			System.err.println("File was not found!");
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Shuffles the indexes of each array up one from the given index
	 * 
	 * @param closestDistances
	 * @param perceivedTypes
	 * @param count
	 */
	private static void shuffle(double[] closestDistances,
			String[] perceivedTypes, int count) {
		System.out.println("Count: " + count);
		System.out.println("Length: " + closestDistances.length);
		for (int index = closestDistances.length - 1; index > count; index--) {
			closestDistances[index] = closestDistances[index - 1];
			perceivedTypes[index] = perceivedTypes[index - 1];
		}
	}

	private static double[] createMaxValueArray(int num) {
		double[] array = new double[num];
		for (int i = 0; i < num; i++) {
			array[i] = Double.MAX_VALUE;
		}
		return array;
	}
}
