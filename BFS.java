import java.util.Hashtable;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.io.IOException;
import java.util.ArrayDeque;

public class BFS extends GameAlgo {

	//constructor
	public BFS(int[][] state, int[][] goal, int[] size) {
		super(state, goal,size);
	}

	public void startAlgo(boolean time, boolean open) throws IOException {
		long startTime= System.currentTimeMillis();
		Output out= new Output();
		//1
		Queue<Node> q = new ArrayDeque<>();
		Hashtable<String, Node> openList= new Hashtable<String, Node>();
		Hashtable<String, Node> closedList= new Hashtable<String, Node>();
		q.add(this.start);
		openList.put(this.start.getID(), this.start);
		//2
		while(!q.isEmpty()) {
			//if open print the iteration
			if(open) {
				openListIter++;
				withOpen(openList, openListIter);

			}
			openList.entrySet();
			//3.1
			Node n = q.poll();
			openList.remove(n.getID(), n);
			
			//3.2
			closedList.put(n.getID(), n);
			List<Node> sons;
			List<Integer> emptySlot= findSlot(n.getState());
			//check if have  2 blanks by checking flag in the end of the array
			sons= findMove(n.getState(), n, emptySlot);
			//3.3
			for(Node s: sons) {
				this.moves++;
				//3.3.2
				if(!openList.containsKey(s.getID()) && !closedList.containsKey(s.getID())) {
				//	this.moves++;
					if(isGoalState(s)) {
						//take time and & while
						long endTime= System.currentTimeMillis();
						float sec= (endTime-startTime)/1000F;
						out.write(findPath(s), this.moves, findCost(s), sec, time, true);
						q.clear();
						openList.clear();
						break;
					}
					q.add(s);
					openList.put(s.getID(),s);
				}			
			}
		}
	}
}