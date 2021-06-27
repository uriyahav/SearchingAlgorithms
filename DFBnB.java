import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DFBnB extends GameAlgo{
	String algo;
	Node ans= null;
	private NodeComperator heur; 
	public DFBnB(int[][] firstState, int[] size, int[][] gameBoard, String algo) {
		super(firstState, gameBoard, size);
		this.algo=algo;

	}

	public void startAlgo(boolean time, boolean open) throws IOException {
		int index=0;
		boolean flag = false;
		int max= fact(this.size[0]*this.size[1]);
		long startTime= System.currentTimeMillis();
		Output out= new Output();
		//1
		Stack<Node> L = new Stack<Node>();
		Hashtable<String, Node> H= new Hashtable<String, Node>();
		heur = new NodeComperator();
		L.push(this.start);
		H.put(this.start.getID(),this.start);
		//2
		int result;
		double t = Math.min(max, Integer.MAX_VALUE);
		//3
		while(!L.isEmpty()) {
			if(open) {
				openListIter++;
				withOpen(H, openListIter);
			}
			//3.1
			Node n = L.pop();
			//3.2 if n marked out remove from H
			if(n.getMark()) H.remove(n.getID());
			//3.3 else...
			else {  
				//3.3.1
				n.setMark();
				L.push(n);
				///3.3.2
				List<Integer> emptySlot= findSlot(n.getState());
				List<Node> N= findMove(n.getState(), n, emptySlot);
				//3.3.3
				Node[] sortedN= new Node[N.size()];
				int j =0;
				for(Node m: N) {
					sortedN[j++]=m;
				}
				Arrays.sort(sortedN, heur);

				//3.3.4
				for (int g = 0; g < sortedN.length; g++) {
					this.moves++;
					if(sortedN[g]!= null) {
						double f_g= sortedN[g].getF();
						//3.4.1
						if(f_g>= t) {
							//remove g and all nodes after it
							for(int x =g;x<sortedN.length;x++) {
								sortedN[x]=null;
							}
						}
						//3.4.2
						else if(H.containsKey(sortedN[g].getID())&&  H.get(sortedN[g].getID()).getMark()) {
							//remove g from N- continue
							sortedN[g]=null;
						}
						//3.4.3
						else if(H.containsKey(sortedN[g].getID())&& ! H.get(sortedN[g].getID()).getMark()) {
							if (f_g>= H.get(sortedN[g].getID()).getF()){
								//remove g from N
								sortedN[g]=null;
							}
							else {
								//remove g from L and H
								H.remove(sortedN[g].getID());
								L.remove(sortedN[g]);
							}
						}
						//3.4.4
						else if(isGoalState(sortedN[g])) {
							ans=sortedN[g];
							index = g;
							t=sortedN[g].getF();
							//remove g and all other from N
							for(int x =g;x<sortedN.length;x++) {
								sortedN[x]=null;
							}
						}

					}
				}
				
				//3.5
				//insert N in reverse order to L and  H if we didn't find goal state
				for (int i = sortedN.length-1;i>=0; i--) {
					if(sortedN[i]!= null) {
						L.push(sortedN[i]);
						H.put(sortedN[i].getID(), sortedN[i]);
					}

				}
				
			}
		}
		if(ans == null) {
			out.write("",H.size(),0,0,time,false);
		}
		else {
			long endTime= System.currentTimeMillis();
			float sec= (endTime-startTime)/1000F;
			out.write(findPath(ans), this.moves, findCost(ans), sec, time, true);
		}
	}

	private int fact(int n) {
		if (n < 0) {
			throw new RuntimeException("Can not calculate factorial of negative number...");
		}

		int result = 1;
		while (n > 0) {
			result *= n;
			n--;
		}

		return result;
	}
}

