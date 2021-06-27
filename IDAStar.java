import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class IDAStar extends GameAlgo{
	int iterCreate=0;
	public IDAStar(int[][] firstState, int[] size, int[][] gameBoard) {
		super(firstState, gameBoard, size);
	}

	public void startAlgo(boolean time, boolean open) throws IOException {
		long startTime= System.currentTimeMillis();
		boolean flag = false;
		Output out= new Output();
		//1
		Stack<Node> L = new Stack<Node>();
		Hashtable<String, Node> H= new Hashtable<String, Node>();
		//2
		int t = this.start.getF();
		//3
		while( t != Integer.MAX_VALUE && !flag) {
			//3.1
			int minF= Integer.MAX_VALUE;
			this.start.mark=false;
			//3.2
			L.push(this.start);
			H.put(this.start.getID(), this.start);
			//3.3
			while(!L.isEmpty()) {
				if(open) {
					openListIter++;
					withOpen(H, openListIter);
				}
				//3.3.1
				Node n = L.pop();
				//3.3.2 if n marked out remove from H
				if(n.getMark()) H.remove(n.getID());
				//3.3.3 else...
				else {
					n.setMark();
					L.push(n);
					List<Integer> emptySlot= findSlot(n.getState());
					List<Node> sons= findMove(n.getState(), n, emptySlot);
					for(Node g : sons) {
						
						int f_g = g.getF();
						//3.1
						if(f_g>t) {
							minF= Math.min(minF, f_g);
							continue;
						}
						//3.2
						if(H.containsKey(g.getID()) && g.getMark()){
							continue;
							//continue with the next operator.
						}
						//3.3
						if(H.containsKey(g.getID()) && !g.getMark()) {
							if(H.get(g.getID()).getF()> f_g) {
								H.remove(g.getID());
								L.remove(g);
							}
							else {
								continue;
							}
						}
						//3.4
						if(isGoalState(g)) {
							
							long endTime= System.currentTimeMillis();
							float sec= (endTime-startTime)/1000F;
							out.write(findPath(g), this.moves, findCost(g), sec, time, true);
							//finish program
							t= Integer.MAX_VALUE;
							L.clear();
							flag = true;
							break;
						}
						//3.5
							L.push(g);
							H.put(g.getID(), g);
							
							this.moves++;
					}
				}
				
			}
			//4
			t= minF;
			
		}
	}
}
