import java.io.File; 
import java.io.FileNotFoundException;  
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
/**
 * Class that read the input txt file init&start solveing the puzzle game.
 *
 */
public class Ex1 {

	public static Game game;

	public static void main(String[] args) throws IOException
	{
		boolean time=false,open = false;
		String algo=""; 
		int row = 1;
		int matrixRow2=0;
		int matrixRow = 0;
		int[] size = new int[2];
		int[][] gameBoard = null;
		int[][] firstState = null;
		try {
			//Using File to read the Input txt
			File myObj = new File("input.txt");
			Scanner myReader = new Scanner(myObj);
			String[] getInfo;
			while (myReader.hasNextLine()) 
			{   
				String currentLine = myReader.nextLine();
				//Using switch cases to find the data we need from the input.txt
				switch(row) 
				{
				case 1:
					//Algorithm to use.
					algo = currentLine;

					break;

				case 2:
					//with time?

					getInfo = currentLine.split(" ", 4);
					if(getInfo[0].equals("with")) time = true;
					break;


				case 3:
					//Open or no open
					getInfo = currentLine.split(" ", 4);
					if(getInfo[0].equals("with")) open = true;
					break;

				case 4:
					//board size m*n
					getInfo = currentLine.split("x", 2);
					size[0] = Integer.parseInt(getInfo[0]); 
					size[1] = Integer.parseInt(getInfo[1]); 
					gameBoard = new int[size[0]] [size[1]];
					firstState = new int[size[0]] [size[1]];
					break;
				}
				//read firstState board
				if(row>=5 && row<5+size[0]) {
					getInfo= currentLine.split(",", size[1]);
					for (int j = 0; j < getInfo.length; j++) {
						if(!getInfo[j].equals("_")) firstState[matrixRow][j] = Integer.parseInt(getInfo[j]);
					}
					matrixRow++;
				}
				if(row>= 6+size[0]) {
					//read the goal state
					
					getInfo = currentLine.split(",", size[1]);
					for(int i=0;i<getInfo.length;i++) {
						if(!getInfo[i].equals("_")) gameBoard[matrixRow2][i] = Integer.parseInt(getInfo[i]); 
					}
					matrixRow2++;
				}
				row++;
			}

			myReader.close();
		} 

		catch (FileNotFoundException e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		} 

		game.stratGame(algo, time, open, size, firstState, gameBoard);
	}

}







//}