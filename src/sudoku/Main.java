package sudoku;

import java.util.Scanner;

public class Main {

	/**
	 * @Ata
	 * Main method of a Sudoku generator that presents different arbitrary assignments in the size of 4*4. 
	 */

	public static void main(String[] args) {

		Sudoku sudoku = new Sudoku();		

		Scanner scan = new Scanner(System.in);
		System.out.println("Please press 1 to display Sudoku or press 0 to exit!");

		String inPut = scan.nextLine();
		int num = sudoku.checkInput(inPut);

		switch (num) {		
		case 1:
			System.out.println("Here is a Sudoku with size 4x4 \n");
			sudoku.displaySudoku(sudoku.createSudoku());
			break;   

		case 2:
			System.out.println("You are exit from the system :(");
			break;

		default:
			System.out.println("Entered option is incorrect :(");
			break;		
		}		
		scan.close();
	}
}
