import java.util.Comparator;
import java.util.List;

public class Node{
	private Node father;
	private int[][] state;
	private int cost=0;
	private int moves = 0;
	private String path,id,dir;
	public boolean mark = false;
	public GameAlgo game;
	private int itCreate=0;

	public Node(int[][] state,int cost,String dir,int[]size, GameAlgo game) {
		this.state = new int [size[0]][size[1]];
		//deep copy
		deepCopy(this.state,state);
		this.cost = cost;
		this.dir = dir;
		this.id = nodeIdGenertor(this.state,size);
		this.game=game;
	}

	public Node getFather() {
		return father;
	}

	public void setFather(Node father) {
		this.father = father;
	}

	public String getID() {
		return this.id;
	}
	public int[][] getState(){
		return this.state;
	}

	public int getCost() {
		return cost;
	}

	public int getMoves() {
		return moves;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getdir() {
		return dir;
	}
	public boolean getMark() {
		return mark;
	}
	public void setMark() {
		this.mark = true;
	}
	public void setitCreate(int itCreate) {
		this.itCreate=itCreate;
	}
	public int getitCreate() {
		return itCreate;
	}
	private void deepCopy(int[][] a, int[][] b) {
		for(int i=0; i<a.length; i++)
			for(int j=0; j<a[i].length; j++)
				a[i][j]=b[i][j];

	}

	/**
	 *This function gives to each node an id by his
	 *number order on the game board.
	 *Then we can easily find duplicated nodes.
	 *
	 * @param gameboard - a state of the puzzel. 
	 * @return a string of the id
	 */
	public String nodeIdGenertor(int[][]gameboard,int[]size) {
		StringBuilder id = new StringBuilder();
		for(int i=0;i<size[0];i++) {
			for(int j=0;j<size[1];j++) {
				id.append(Integer.toString(gameboard[i][j]));
			}
		}
		String saltStr = id.toString();
		return saltStr;

	}
	/**
	 * function to evaluate which node to develop by using 2  function
	 *  F(n)= g(n) + h(n) , where f(n) is the best estimate of a lowest cost
		path from the initial state to a goal state
		and g(n) is the cost to n from root

	 * @param n node we want to calculate Heuristic function
	 * @return grade evaluate for the node
	 */
	public int getF() {
		return (int)( heuristicFunc()*0.8+ game.findCost(this)*0.3);
	}
	/**
	 *	This is Heuristic Function.
	 *	This function will be an inclusion of Manhattan function
	 *	 we will calculate the distance of the x + y of the grid.
	 *	@return Grade for this node.
	 */
	private double heuristicFunc() {
		int result=0;
		for(int i = 0; i < getState().length; i++) {
			for(int j = 0; j < getState()[0].length; j++) {
				result += (manhattanDist(getState()[i][j],i,j));
			}
		}
		return result;
	}

	/**
	 * Calculate the number based on Manhattan Heuristic
	 */
	private int manhattanDist(int val, int x, int y) {
		int x2 = 0,y2 = 0;
		//maybe 1? or try something else
		if (val ==0) return 1;
		for(int i = 0; i < getState().length; i++)
			for(int j = 0; j < getState()[0].length; j++)
				if(game.goal[i][j] == val ) {
					x2 = i;
					y2 = j;
				}	
		int dist =  Math.abs(x - x2) +  Math.abs(y - y2);
		return dist;
	}

	/**
	 * Calculate Number based on Misplaced Tiles
	 * 
	 * @param nodeState
	 * @return number of steps
	 */
	public int getMisplacedTilesHeuristic(int[][] nodeState) {

		int number_misplaced_tiles = 0;
		// Iterate over the matrix to compare if each tiles has correct number
		for (int i = 0; i < nodeState.length; i++) {
			for (int j = 0; j < nodeState[0].length; j++) {
				if (nodeState[i][j] != 0 && nodeState[i][j] != this.game.goal[i][j]) {
					number_misplaced_tiles++;
				}
			}
		}
		return number_misplaced_tiles;
	}


}
