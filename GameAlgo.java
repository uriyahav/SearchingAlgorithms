import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;


public class GameAlgo {
	protected int [][] state;
	protected int []  size;
	protected int moves=1;
	protected int [][] goal;
	protected Node start;
	protected int openListIter=0;

	public GameAlgo(int[][] firstState, int[][] goal, int[] size) {
		this.state= firstState;
		this.goal= goal;
		this.size = size;
		// init the first Node.
		start = new Node(firstState,0,"NULL",this.size, this); 
	}
	public int[][] getState(){
		return state;
	}

	/** function to find all optional sons to develop- trying to choose the 'best son' of the current node
	 * @param gameboard- current state 
	 * @param father- current Node\
	 * @param pos- position of the blank in the board
	 * @return set of sons

	 */
	protected List<Node> findMove(int [][] curState, Node father, List<Integer> pos){
		List<Node> childrens= new ArrayList<Node>();
		int[][]  stateSon= new int[size[0]][size[1]];
		copyMat(stateSon, curState);
		Node son;

		//in case of 2 blanks- check if can move 2 blanks:
		if(pos.size()==4) {
			pos= order(pos);
			// if in the same row together
			if((pos.get(0)==pos.get(2)) && (Math.abs(pos.get(1)-pos.get(3))==1)) {
				//we can move U , D
				//check if can go U
				if(canGo(pos, father, "UU", 1)){
					son = createNode2(stateSon, "U", pos);
					childrens.add(son);
					son.setFather(father);
					copyMat(stateSon, curState);
				}
				//check if can go D
				if(canGo(pos , father,"DD", 1)){
					son = createNode2(stateSon, "D", pos);
					childrens.add(son);
					son.setFather(father);
					copyMat(stateSon, curState);
				}
			}
			//if in the same column together
			else if((pos.get(1)== pos.get(3))&&(Math.abs(pos.get(0)-pos.get(2))==1)) {
				//we can move L , R
				//check if can go L
				if(canGo(pos, father, "LL", 1)){
					son = createNode2(stateSon, "L", pos);
					childrens.add(son);
					son.setFather(father);
					copyMat(stateSon, curState);
				}
				//check if can go R
				if(canGo(pos, father, "RR", 1)){
					son = createNode2(stateSon, "R", pos);
					childrens.add(son);
					son.setFather(father);
					copyMat(stateSon, curState);
				}
			}	
		}
		//in case of 2 blanks not together- sort them in order detayled in the task

		int times=1;
		// go through all possible casses based on position info to move 1 blank
		while(times<pos.size()) {
			//check if can go L
			if(canGo(pos, father, "L", times)){
				son = createNode(stateSon, "L", pos.subList(times-1, times+1));
				childrens.add(son);
				son.setFather(father);
				copyMat(stateSon, curState);
			}
			//check if can go U
			if(canGo(pos, father, "U", times)){
				son = createNode(stateSon, "U", pos.subList(times-1, times+1));
				childrens.add(son);
				son.setFather(father);
				copyMat(stateSon, curState);
			}
			//check if can go R
			if(canGo(pos, father, "R", times)){
				son = createNode(stateSon, "R", pos.subList(times-1, times+1));
				childrens.add(son);
				son.setFather(father);
				copyMat(stateSon, curState);
			}
			//chack if can go D
			if(canGo(pos, father, "D", times)){
				son = createNode(stateSon, "D", pos.subList(times-1, times+1));
				childrens.add(son);
				son.setFather(father);
				copyMat(stateSon, curState);
			}
			times+=2;
		}

		return childrens;
	}
	/**
	 * function to check if we can play the  game(move the blnak) to a current direction
	 * @param pos- position of the  blank
	 * @param father- current node we want to develop son from
	 * @param direction- direction to go to
	 * @param times- parameter in case  of 2 blanks , default case times=1
	 * @return true if we can play current direction false if not
	 */
	private boolean canGo(List<Integer> pos, Node father, String direction, int times) {
		switch(direction) {
		case "UU":
		//	if(father.getID().equals("143256700")) System.out.println("here");
			if((pos.get(times-1)!=this.size[0]-1)&&(!father.getdir().contains("&")&&(!father.getdir().contains("D"))|| father.getFather()==null)) return true;
			else return false;
		case "DD":
			
			//System.out.println("here"+father.getID());
			if((pos.get(times-1)!=0)&&(!father.getdir().contains("&")||(!father.getdir().contains("U"))|| father.getFather()==null)) { 
		//		if(father.getID().equals("143256700")) System.out.println("hereee");
				return true;
			}
			else return false;
		case "LL":
			if((pos.get(times)!=this.size[1]-1)&&(!father.getdir().contains("&")&&(!father.getdir().contains("R"))|| father.getFather()==null)) return true;
			else return false;
		case "RR":
			if((pos.get(times)!=0)&&(!father.getdir().contains("&")&&(!father.getdir().contains("L"))|| father.getFather()==null)) return true;
			else return false;
		case "U":
			if((pos.get(times-1)!=this.size[0]-1)&&(!father.getdir().contains("D")|| father.getFather()==null)) return true;
			else return false;
		case "D":
			if((pos.get(times-1)!=0)&&(!father.getdir().contains("U")|| father.getFather()==null)) return true;
			else return false;
		case "L":
			if((pos.get(times)!=this.size[1]-1)&&(!father.getdir().contains("R")|| father.getFather()==null)) return true;
			else return false;
		default:
			//case R	
			if((pos.get(times)!=0)&&(!father.getdir().contains("L")|| father.getFather()==null)) return true;
			else return false;
		}	
	}
	private List<Integer> order(List<Integer> pos) {
		List<Integer> sorted= new ArrayList<Integer>();
		// choose poition of blank in first row
		if(pos.get(0)>pos.get(2)) {
			sorted.add(pos.get(2));
			sorted.add(pos.get(3));
			sorted.add(pos.get(0));
			sorted.add(pos.get(1));
		}
		//in the same row- the left one first
		else if(pos.get(0)==pos.get(2)) {
			if(pos.get(1)< pos.get(3)) {
				//the same as pos
				sorted.addAll(pos);
			}
			else {
				sorted.add(pos.get(0));
				sorted.add(pos.get(3));
				sorted.add(pos.get(2));
				sorted.add(pos.get(1));
			}
		}
		//else the same as pose
		else {
			sorted.addAll(pos);
		}				
		return sorted;
	}
	/** Function to play the game and go to the next gameboad position by moving  2 block together
	 * 
	 * @param curState- current state of the gameboard
	 * @param direction- which direction to go to
	 * @param pos - position of blanks
	 * @return new node representing the current state of the gameboard.
	 */
	private Node createNode2(int[][] curState, String direction, List<Integer> pos) {
		Node s =null;
		int cost=0;
		int[][] newState= curState;
		//check direction

		if(direction.equals("U")) {
			//blank go up switch with the number
			newState[pos.get(0)][pos.get(1)]= curState[pos.get(0)+1][pos.get(1)];
			newState[pos.get(2)][pos.get(3)]= curState[pos.get(2)+1][pos.get(3)];
			curState[pos.get(0)+1][pos.get(1)]=0;
			curState[pos.get(2)+1][pos.get(3)]=0;
			cost= 7;
			String nodeDirection=Integer.toString(newState[pos.get(0)][pos.get(1)])+"&"+ Integer.toString(newState[pos.get(2)][pos.get(3)])+"U";
			s = new Node(newState,cost,nodeDirection,this.size, this);
		}
		else if(direction.equals("D")) {
			//blank go down & switch with the number
			newState[pos.get(0)][pos.get(1)]= curState[pos.get(0)-1][pos.get(1)];
			newState[pos.get(2)][pos.get(3)]= curState[pos.get(2)-1][pos.get(3)];
			curState[pos.get(0)-1][pos.get(1)]=0;
			curState[pos.get(2)-1][pos.get(3)]=0;
			cost= 7;
			String nodeDirection=Integer.toString(newState[pos.get(0)][pos.get(1)])+"&"+ Integer.toString(newState[pos.get(2)][pos.get(3)])+"D";
			s = new Node(newState,cost,nodeDirection,this.size, this);
		}
		else if(direction.equals("L")){
			//blank go down & switch with the number
			newState[pos.get(0)][pos.get(1)]= curState[pos.get(0)][pos.get(1)+1];
			newState[pos.get(2)][pos.get(3)]= curState[pos.get(2)][pos.get(3)+1];
			curState[pos.get(0)][pos.get(1)+1]=0;
			curState[pos.get(2)][pos.get(3)+1]=0;
			cost= 6;
			String nodeDirection=Integer.toString(newState[pos.get(0)][pos.get(1)])+"&"+ Integer.toString(newState[pos.get(2)][pos.get(3)])+"L";
			s = new Node(newState,cost,nodeDirection,this.size, this);

		}
		else {
			//blank go right & switch with the number
			newState[pos.get(0)][pos.get(1)]= curState[pos.get(0)][pos.get(1)-1];
			newState[pos.get(2)][pos.get(3)]= curState[pos.get(2)][pos.get(3)-1];
			curState[pos.get(0)][pos.get(1)-1]=0;
			curState[pos.get(2)][pos.get(3)-1]=0;
			cost= 6;
			String nodeDirection=Integer.toString(newState[pos.get(0)][pos.get(1)])+"&"+ Integer.toString(newState[pos.get(2)][pos.get(3)])+"R";
			s = new Node(newState,cost,nodeDirection,this.size, this);
		}

		return s;
	}
	private boolean avoidLoop(Node son, Node father) {
		boolean loop = true;
		String sonOp= son.getdir();
		sonOp.substring(0,  son.getdir().length()-1);
		String fatherOp= father.getdir();
		fatherOp.substring(0,  father.getdir().length()-1);
		if(sonOp.equals(fatherOp)) loop= false;
		return loop;
	}


	/** Function to play the game and go to the next gameboad position.
	 * 
	 * @param curState- current state of the gameboard
	 * @param direction- which direction to go to
	 * @param pos - position of blanks
	 * @return new node representing the current state of the gameboard.
	 */
	private Node createNode(int[][] curState, String direction , List<Integer> pos) {
		Node s=null ;
		int cost=0;
		int[][] newState= curState;
		//check direction

		if(direction.equals("U")) {
			//blank go up switch with the number
			newState[pos.get(0)][pos.get(1)]= curState[pos.get(0)+1][pos.get(1)];
			curState[pos.get(0)+1][pos.get(1)]=0;
			cost= 5;
			String nodeDirection=Integer.toString(newState[pos.get(0)][pos.get(1)])+"U";
			s = new Node(newState,cost,nodeDirection,this.size, this);
		}
		else if(direction.equals("D")) {
			//blank go up switch with the number
			newState[pos.get(0)][pos.get(1)]= curState[pos.get(0)-1][pos.get(1)];
			curState[pos.get(0)-1][pos.get(1)]=0;
			cost= 5;
			String nodeDirection=Integer.toString(newState[pos.get(0)][pos.get(1)])+"D";
			s = new Node(newState,cost,nodeDirection,this.size, this);
		}

		else if(direction.equals("L")){
			//blank go go right & switch with the number
			newState[pos.get(0)][pos.get(1)]= curState[pos.get(0)][pos.get(1)+1];
			curState[pos.get(0)][pos.get(1)+1]=0;
			cost= 5;
			String nodeDirection=Integer.toString(newState[pos.get(0)][pos.get(1)])+"L";
			s = new Node(newState,cost,nodeDirection,this.size, this);
		}
		else {
			//blank go left & switch with the number
			newState[pos.get(0)][pos.get(1)]= curState[pos.get(0)][pos.get(1)-1];
			curState[pos.get(0)][pos.get(1)-1]=0;
			cost= 5;
			String nodeDirection=Integer.toString(newState[pos.get(0)][pos.get(1)])+"R";
			s = new Node(newState,cost,nodeDirection,this.size, this);
		}

		return s;
	}
	/**
	 * help function to make a deep copy between 2 matrix
	 * @param stateSon- destination matrix
	 * @param gameboard2- mat we copy from
	 */

	private void copyMat(int[][] stateSon, int[][] gameboard2) {
		for (int i = 0; i < gameboard2.length; i++) {
			for (int j = 0; j < gameboard2[0].length; j++) {
				stateSon[i][j]= gameboard2[i][j];
			}
		}

	}
	/**
	 * recursive function to find the path from a given node to the root of the tree
	 * @param n- the given node
	 * @return-  string representing the path
	 */
	protected String findPath(Node n) {
		if (n.getFather()==null)
			return "";
		else
			return findPath(n.getFather())+"-"+ n.getdir();

	}
	/**
	 * recursive function to find the cost from the root to a given node.
	 * @param n- the given node
	 * @return- int representing the cost  to the node 
	 */
	protected int findCost(Node n) {
		if(n.getFather()== null) return 0;
		else  return findCost(n.getFather()) +n.getCost();
	}
	/**
	 * function to check if we are in the goal state in a given node.
	 * @param n- the node we check
	 * @return true if goal state , else false.
	 */
	protected boolean isGoalState(Node n) {
		for (int i = 0; i < this.size[0]; i++) {
			for (int j = 0; j < this.size[1]; j++) {
				//maybe with flag
				if(n.getState()[i][j] != this.goal[i][j]) return false;
			}
		}
		return true;
	}

	/**
	 * print open list at each iteration of the game if choosed to.
	 * @param openList hash table to  print
	 * @param it the iteration we are.
	 */
	protected void withOpen(Hashtable<String, Node> openList , int it) {
		System.out.println("Iteration Number "+ it + " :");
		int i =0;
		for(Entry<String,Node> e: openList.entrySet()) {
			String key = e.getKey();
			Node n = e.getValue();
			System.out.println ("("+i+") "+ "State: "+key+" Value: " + n.getdir());
			i++;
		}
		System.out.println("########################");
	}
	/**
	 * function to find empty slots in the current gameboard
	 * @param gameboard- cur state of the game
	 * @return array representing the indexes of the empty slots in the gameboard
	 */
	protected List<Integer> findSlot(int[][] gameboard) {
		List<Integer> emptySlots= new ArrayList<Integer>();
		for (int i = 0; i < size[0]; i++) {
			for(int j=0;j<size[1];j++) {
				if(gameboard[i][j]==0) {
					emptySlots.add(i);
					emptySlots.add(j);
				}
			}
		}
		return emptySlots;
	}

}


