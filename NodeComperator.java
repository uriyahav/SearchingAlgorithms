import java.util.Comparator;

public class NodeComperator implements Comparator<Node> {
	public NodeComperator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Node n1, Node n2) {
		if(n1.getF()>n2.getF()) return 1;
		else if( n1.getF()<n2.getF()) return -1;
		else if(n1.getF()== n2.getF()) {
			//take who born first.
			if(n1.getitCreate()>n2.getitCreate()) {
				return 1;
			}
			else return -1;
		}
		return 0;
	}


}
