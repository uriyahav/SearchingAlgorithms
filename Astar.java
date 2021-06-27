import java.io.IOException;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Astar extends GameAlgo {
	int iterCreate=0;
	public Astar(int[][] firstState, int[][] gameBoard, int[] size) {
		super(firstState, gameBoard, size);
	}

	public void startAlgo(boolean time, boolean open) throws IOException {
		long startTime= System.currentTimeMillis();
		Output out= new Output();
		boolean found =  false;
		NodeComperator cmp = new NodeComperator();
		//List<Node> sons;
		//1&2
		Hashtable<String, Node> openList= new Hashtable<String, Node>();
		Hashtable<String, Node> closedList= new Hashtable<String, Node>();
		PriorityQueue<Node> L = new PriorityQueue<Node>(cmp);
		this.start.setitCreate(++iterCreate);
		L.add(this.start);
		//this.moves++;
		openList.put(this.start.getID(), this.start);
		//3
		while(!L.isEmpty()) {
			//if open print the iteration
			if(open) {
				openListIter++;
				withOpen(openList, openListIter);
			}
			///3.1
			Node n = L.poll();
			openList.remove(n.getID(), n);
			//this.moves++;
			//3.2
			if(isGoalState(n)) {
				long endTime= System.currentTimeMillis();
				float sec= (endTime-startTime)/1000F;
				out.write(findPath(n), this.moves, findCost(n), sec, time, true);
				//end while
				L.clear();
				break;
			}
			//3.3
			closedList.put(n.getID(), n);
			List<Integer> emptySlot= findSlot(n.getState());			
			List<Node> sons= findMove(n.getState(), n, emptySlot);

			//3.4
			for(Node s: sons) {
				
				//3.4.2
				if (!closedList.containsKey(s.getID()) && !openList.containsKey(s.getID())) {
					
					s.setitCreate(++iterCreate);
					L.add(s);
					openList.put(s.getID(), s);
					this.moves++;
				}
				else if(openList.containsKey(s.getID())) {
					Node t= openList.get(s.getID());
					//this.moves++;
					if(findCost(t)>findCost(s)) {
						openList.replace(t.getID(), s);
					}
					
				}
			}

		}
	}
}

