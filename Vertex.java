
public class Vertex {
	public String label;  
	public int num;
	public boolean wasVisited;
	public int degree;
	public boolean degreevisited;
	
	public Vertex(String name, int n){ 
		label = name; 
		num = n;
		degree = 0;
		wasVisited = false;
		degreevisited = false;
		
	}
	
	
}
