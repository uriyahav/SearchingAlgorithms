import java.io.IOException;

public class Game {
	private boolean time , open;
	public Game() {
		// TODO Auto-generated constructor stub
	}
	public static void stratGame(String algo, boolean time, boolean open, int[] size, int[][] firstState, int[][] gameBoard) throws IOException {
		if(algo.equals("BFS")) //Search with BFS the solution
		{
			BFS bfs = new BFS(firstState,gameBoard, size);
			bfs.startAlgo(time,open);
		}
		if(algo.equals("DFID")) //Search with DFID the solution
		{

			DFID dfid = new DFID(firstState, size, gameBoard);
			dfid.startAlgo(time,open);
		}
		
		if(algo.equals("A*"))// Search with A* the solution
		{

			Astar astar = new Astar(firstState, gameBoard, size);
			astar.startAlgo(time,open);
		}
	
		if(algo.equals("IDA*")) //Search with IDA* the solution
		{

			IDAStar ida_star = new IDAStar(firstState, size, gameBoard);
			ida_star.startAlgo(time,open);
		}
		
	
		
		if(algo.equals("DFBnB")) //Search with DFBnB the solution
		{

			DFBnB dfbnb = new DFBnB(firstState, size, gameBoard, algo);
			dfbnb.startAlgo(time,open);
		}
	}
}
