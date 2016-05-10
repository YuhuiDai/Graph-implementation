import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main5 {
	
	// find the corresponding number associated with a certain person, which help with building the adj matrix
	public static int getN(String name, ArrayList<Vertex> myarray){
		int num = -1;
		for (int i = 0; i< myarray.size(); i++) {
			if (name.equals(myarray.get(i).label)){
				num = myarray.get(i).num;
				break;
			} 
		}
		return num;   
	}

	public static void main(String[] args) {
		try{
			// ########## get info from customer file #############
			Scanner edge_input = new Scanner(new File(args[0]));
		
			//Graph theGraph = new Graph();
			ArrayList<String> mylist = new ArrayList<String>();
			ArrayList<Vertex> vertexlist = new ArrayList<Vertex>();
			ArrayList<String> file_content = new ArrayList<String>();
			int count = 0;
			
			// reading the file and store the info from the file onto an arraylist
			while (edge_input.hasNext()) {
				
				String myinput = edge_input.nextLine();
				file_content.add(myinput);
				String[] my_input = myinput.split(", ");
				//theGraph.vertexList[i] = my_input[0];
				if ( mylist.contains(my_input[0]) != true) {
					mylist.add(my_input[0]);
					Vertex newthing = new Vertex(my_input[0],count);
					count ++;
					vertexlist.add(newthing);
				}
				if ( mylist.contains(my_input[1]) != true) {
					mylist.add(my_input[1]);
					Vertex newthing = new Vertex(my_input[1],count);
					count ++;
					vertexlist.add(newthing);
				}	
				
			}	
			
			int max = mylist.size();
//			
			Graph theGraph = new Graph(max);
			System.out.println(theGraph.vertexList.length);
			
			
			//establish graph, including the vertexlist
			for (int i = 0; i< vertexlist.size(); i++) {
				
				theGraph.addVertex(vertexlist.get(i));
			}
			
			// building edges
			for (int i = 0; i< file_content.size(); i++) {
				String[] str = file_content.get(i).split(", ");
				int first = getN(str[0], vertexlist);
				int second = getN(str[1], vertexlist);
				theGraph.addEdge(first, second);
				//System.out.println(first + "|" + second);
			}
			
			
			// testing connectivity and printing connecting parts
			System.out.println("*****testing connectivity and printing connecting parts*****");
			theGraph.detect_connectivity(0);
			if (theGraph.connected == false) {
				System.out.println("The graph is disconnected");
			} else {
				System.out.println("The graph is connected");
			}
			
			// retrieve high degree k and print names, you can choose whichever number you like to know as long as it is within the bounds
			System.out.println("*****retrieve high degree k and print names, I chose top 3, but you can change to whatever*****");
			String[] names = theGraph.display_high_degree(3);
			System.out.println("High Degree Names:");
			for (int i = 0; i < names.length; i++) {
				
				System.out.println(names[i]);
			}
			
			// give two nodes to find the path
			System.out.println("*****give two nodes to find the path, you can change the paramenters*****");
			theGraph.find_path(theGraph.vertexList[1], theGraph.vertexList[19]);
			
			
			// detect whether there is a cycle in the graph and display it if so
			System.out.println("*****detect whether there is a cycle in the graph and display it if so*****");
			boolean cycle = theGraph.detect_cycle();
			
			if (cycle == false) {
				System.out.println("There is no cycle in this graph");
			} else {
				System.out.println("The Graph has cycles");
	            theGraph.detect_connectivity(0);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}



