package padillo;

import java.util.Scanner;

public class Padillo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

//		int arr[][] = new int[3][3];
//		
//		//user input
//		for (int x = 0; x < arr.length; x++) {
//			for (int y = 0; y < arr[x].length; y++) {
//				System.out.print("Enter Number: ");
//				arr[x][y] = sc.nextInt();
//			}
//		}
//		
//		//print out the array
//		for (int x = 0; x < arr.length; x++) {
//			for (int y = 0; y < arr[x].length; y++) {
//				System.out.print(arr[x][y] + " ");
//			}
//			//System.out.println();
//		}
//		
//		System.out.println("\n");
//		
//		//enhanced for-loop to print the array
//		for (int[] x: arr) {
//			for (int y : x) {
//				System.out.print(y + " ");
//			}
//			//System.out.println();
//		}
//		
//		System.out.println();
//		
//		for (int x = 0; x < arr.length; x++) {
//			for (int y = 0; y < arr[x].length; y++) {
//				if (arr[x][y] == 5)
//				System.out.println("Number 5 is found at indexes " + x + ":" + y);
//			}
//			//System.out.println();
//		}

		int numbers[][] = { { 120, 250, 789, 7 }, { 58, 79, 31, 87 }, { 8, 1, 2, 3 }, { 99, 98, 97, 96 } };

		// prints the array in table form
		for (int x = 0; x < numbers.length; x++) {
			for (int y = 0; y < numbers[x].length; y++) {
				System.out.printf("%5d", numbers[x][y]);
			}
			System.out.println();
		}

		// finds the index of #1 and change its value to 50
		for (int x = 0; x < numbers.length; x++) {
			for (int y = 0; y < numbers[x].length; y++) {
				if (numbers[x][y] == 1) {
					System.out.println("Number 1 is found at indices " + x + " and " + y);
					numbers[x][y] = 50;
				}
			}
		}

		System.out.println();

		// prints the updated 2D array
		for (int x = 0; x < numbers.length; x++) {
			for (int y = 0; y < numbers[x].length; y++) {
				System.out.printf("%5d", numbers[x][y]);
			}
			System.out.println();
		}

		// finds the index of #1 and change its value to 50
		for (int x = 0; x < numbers.length; x++) {
			for (int y = 0; y < numbers[x].length; y++) {
					numbers[1][y] = 0;
			}
		}

		System.out.println("\nAll values in 2ND row changed to 0");

		// prints the updated 2D array
		for (int x = 0; x < numbers.length; x++) {
			for (int y = 0; y < numbers[x].length; y++) {
				System.out.printf("%5d", numbers[x][y]);
			}
			System.out.println();
		}

		sc.close();
	}

}
