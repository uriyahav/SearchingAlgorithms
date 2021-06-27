import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class DFID extends GameAlgo {
	Node found;
	int cutoff =3;
	
	//construcor
	public DFID(int[][] firstState, int[] size, int[][] gameBoard) {
		super(firstState, gameBoard, size);
	}
	public void startAlgo(boolean time, boolean open) throws IOException {
		long startTime= System.currentTimeMillis();
		Output out= new Output();
		int result;

		for(int depth =0; depth< Integer.MAX_VALUE;depth++) {
			//1.1
			Hashtable<String, Node> H= new Hashtable<String, Node>();

			//1.2
			result= Limited_DFS(this.start, this.goal, depth, H, open);

			//1.3
			if(result != cutoff) {
				long endTime= System.currentTimeMillis();
				float sec= (endTime-startTime)/1000F;
				out.write(findPath(found), this.moves, findCost(found), sec, time, true);
				//End the loop.
				depth = Integer.MAX_VALUE;
				break;
			}

		}
	}
	private int Limited_DFS(Node n, int[][] goal, int limit, Hashtable<String, Node> H, boolean open) {
		boolean isCutoff;
		int result;
		//1
		if(isGoalState(n)) {
			found=n;
			return 1;
		}
		//2
		else if (limit ==0) return cutoff;
		//3
		else {
			if(open) {
				openListIter++;
				withOpen(H, openListIter);
			}
			//3.1
			H.put(n.getID(),n);
		
			//3.2
			isCutoff= false;
			//3.3 - all allowed operators
			List<Integer> emptySlot= findSlot(n.getState());
			List<Node> sons= findMove(n.getState(), n, emptySlot);
			if(sons.isEmpty()) return 2;
			for(Node g : sons) {
				//3.3.2
				//if(H.contains(g)) {
				if( H.containsKey(g.getID())) {
					//t--;
					continue;
				//do nothing
				}
				this.moves++;
				result= Limited_DFS(g, this.goal, limit-1, H, open);
				if(result== cutoff) isCutoff= true;
				///check that not empty to
				else if(result != 2) {
					return 1;

				}
			}
			H.remove(n.getID());
			if(isCutoff) return cutoff;
			//even 2 works
			else return 3;
		}
	}
}


