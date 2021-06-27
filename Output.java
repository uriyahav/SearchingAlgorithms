import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Output {
public Output() {	// TODO Auto-generated method stub
}

public void write(String pathOp, int numNodes, int cost, float sec, boolean time, boolean ans) throws IOException {
	File f= new File("output.txt");
	FileWriter myWriter= new FileWriter(f);
	//if find path- succeed
	if(ans) {
		pathOp= pathOp.substring(1, pathOp.length());
		myWriter.write(pathOp);
		myWriter.write("\n");
		myWriter.write("Num: "+ numNodes);
		myWriter.write("\n");
		myWriter.write("Cost: "+ cost);
		myWriter.write("\n");
		//time?
		if(time) myWriter.write(sec+ " seconds");
	}
	else {
		myWriter.write("no path");
		myWriter.write("Num: "+ numNodes);
	}
	myWriter.close();
}

}
