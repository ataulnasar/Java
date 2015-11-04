package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class Sudoku {

	/**
	 * @Ata
	 * 
	 * 
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

	/*Create a Sudoku*/
	protected  List<Object> createSudoku(){
		int size = 4;
		List<Integer> arrayIn = new ArrayList<Integer>();
		List<Object> arrayOut = new ArrayList<Object>();
		List<Integer> arrayTemp;

		for (int i = 0; i < size; i++) {
			arrayIn.add(i+1);
		}		
		Collections.shuffle(arrayIn);

		for (int i = 0; i < size; i++) {

			arrayTemp = new ArrayList<Integer>();
			for (int j = 0; j <size; j++) {
				int temp = ((arrayIn.get(i)+(1*j))%4)+1;
				arrayTemp.add(temp);
			}
			arrayOut.add(arrayTemp);
		}
		Collections.shuffle(arrayOut);

		return arrayOut;
	}

	/*Display the Sudoku on console*/
	protected void displaySudoku(List<Object> array){
		for (int i = 0; i < array.size(); i++) {
			@SuppressWarnings("unchecked")
			List<Integer> arrayIn = (ArrayList<Integer>) array.get(i);
			for (int j = 0; j < arrayIn.size(); j++) {
				if((i+j)%2 ==0 && (i+j)!=0 ){
					System.out.print(arrayIn.get(j)+" ");
				}else {
					System.out.print(0+" ");
				}
				if(j==1){
					System.out.print("| ");
				}
			}
			System.out.print("\n");
			if(i==1) {
				System.out.print("----|----\n");
			}				
		}
	}

	/*This is a helper function that checks convert- 
	 * input string to integer or show an error*/
	private int checkInput(String inputString){
		int number = 0;
		try {
			number = Integer.parseInt(inputString);			
		}
		catch (NumberFormatException e) {
			number = 2;
		}
		return number;
	}
}