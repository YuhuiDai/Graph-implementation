import java.util.ArrayList;

public class Graph {
		public static int max;
	   public static Vertex vertexList[]; 
	   public int adjMat[][];      
	   public int nVerts;          // current number of vertices
	   public String sortedArray[];
	   public static boolean connected;
	   
	   public Graph(int MAX_VERTS)               
	      {
		  max = MAX_VERTS;
		  connected = true;
	      vertexList = new Vertex[MAX_VERTS];
	      adjMat = new int[MAX_VERTS][MAX_VERTS];
	      nVerts = 0;
	      for(int j=0; j<MAX_VERTS; j++)      
	         for(int k=0; k<MAX_VERTS; k++)   
	            adjMat[j][k] = 0;
	      sortedArray = new String[MAX_VERTS];  // sorted vert labels
	      }  
	   
	   
	   
	   public static String[] display_high_degree(int k) {
		   System.out.println("Top " + k);
		   if (k< max+1 ) {
		   int[] degree_array = new int[max];
		   String[] name_array = new String[k];
		   for (int i = 0; i < vertexList.length; i++) {
			   degree_array[i] = vertexList[i].degree;
		   }
		   //sort the list
		   quickSort(vertexList, 0, max-1);
		   for (int i = 0; i < k; i++) {
			   name_array[i] = getV_withD(vertexList[i].degree).label;
		   }
		   return name_array;
		   } else {
			   System.out.println("bad");
			   return null;
		   }
	   }
	   
	   public static Vertex getV_withD(int degree){
		    Vertex vertex = null;
			for (int i = 0; i< vertexList.length; i++) {
				if (vertexList[i].degree == degree && vertexList[i].degreevisited == false){
					vertex = vertexList[i];
					vertexList[i].degreevisited = true;
					break;
				} 
			}
			return vertex;   
		}
	   
	   // sorting the list from big to small
	   public static void quickSort(Vertex[] thelist, int beg, int end) {
			
			if (beg >= end) {
				return;
			}
			
			int i = beg;
			int j = end-1;
			//set the pivot to be the far right of the array
			int pivot = end;
			
			Vertex temp;
			
			while (i < j) {
				
				while (thelist[i].degree > thelist[pivot].degree && i < end) {
					
					i++;
				}
				while (thelist[j].degree < thelist[pivot].degree && j > beg) {
					
					j--;
				}
//				System.out.println(i+" "+j);
				if (i < j) {
					temp = thelist[i];
					thelist[i] = thelist[j];
					thelist[j] = temp;
					i++;
					j--;
				}
			}
			//swap the temp and the pivot
			temp = thelist[i];
			thelist[i] = thelist[pivot];
			thelist[pivot] = temp;

				quickSort(thelist, i+1, end);

				quickSort(thelist, beg, i-1);

		}
	   
	   public void addVertex(Vertex a)
	      {
	      vertexList[nVerts++] = a;
	      }
	
	   public void addEdge(int start, int end)
	      {
	      adjMat[start][end] = 1;
	      adjMat[end][start] = 1;
	      vertexList[start].degree ++;
	      vertexList[end].degree ++;
	      }
	
	   public void displayVertex(int v)
	      {
	      System.out.println(vertexList[v].label);
	      }	   
	   
	   public boolean detect_cycle()  // toplogical sort
	      {int orig_nVerts = nVerts;
		   boolean cycle = false;
	      while(nVerts > 0)  // while vertices remain,
	         {
	         // get a vertex with no successors, or -1
	         int currentVertex = noSuccessors();
	         if(currentVertex == -1)       // must be a cycle
	            {
	            cycle = true;
	            break;
	            }
	         
	      // insert vertex label in sorted array (start at end)
	         sortedArray[nVerts-1] = vertexList[currentVertex].label;

	         deleteVertex(currentVertex);  // delete vertex
	         }  // end while

//	      // vertices all gone; display sortedArray
//	      System.out.print("Topologically sorted order: ");
//	      for(int j=0; j<orig_nVerts; j++)
//	         System.out.print( sortedArray[j] );
//	      System.out.println("");
	        
	      
	      return cycle;
	      }  

		public void deleteVertex(int delVert){
			System.out.println(delVert + " : " + nVerts);
			if(delVert != nVerts-1)      // if not last vertex,
			{                         // delete from vertexList
				for(int j=delVert; j<nVerts-1; j++)
					vertexList[j] = vertexList[j+1];
                             // delete row from adjMat
				for(int row=delVert; row<nVerts-1; row++)
					moveRowUp(row, nVerts);
				// delete col from adjMat
				for(int col=delVert; col<nVerts-1; col++)
					moveColLeft(col, nVerts-1);
			}
			nVerts--;                    // one less vertex
		}  // end deleteVertex

		private void moveRowUp(int row, int length){
			for(int col=0; col<length; col++)
				adjMat[row][col] = adjMat[row+1][col];
		}

		private void moveColLeft(int col, int length){
			for(int row=0; row<length; row++)
				adjMat[row][col] = adjMat[row][col+1];
		}

	   public int noSuccessors()  // returns vert with no successors
	      {                       // (or -1 if no such verts)
	      boolean isEdge;  // edge from row to column in adjMat

	      for(int row=0; row<nVerts; row++)  // for each vertex,
	         {
	         isEdge = false;                 // check edges
	         for(int col=0; col<nVerts; col++)
	            {
	            if( adjMat[row][col] > 0 )   // if edge to
	               {                         // another,
	               isEdge = true;
	               break;                    // this vertex
	               }                         //    has a successor
	            }                            //    try another
	         if( !isEdge )                   // if no edges,
	            return row;                  //    has no successors
	         }  
	      
	      return -1;                         // no such vertex
	      } 
	   
	   public void detect_connectivity(int beg)  { 
		   
		   if ( beg < max && vertexList[beg].wasVisited == false){
		   System.out.println("*************Graph Separation*******");
		  StackX theStack = new StackX();
		   
	      vertexList[beg].wasVisited = true; 
	      displayVertex(beg);                
	      theStack.push(beg);                 
	      int count = 1;
	      
	      while( !theStack.isEmpty() )      
	         {
	         
	         int v = getAdjUnvisitedVertex( theStack.peek() );
	         if(v == -1)                
	            theStack.pop();
	         else                           
	            {
	            vertexList[v].wasVisited = true;  
	            count ++;
	            displayVertex(v);                 
	            theStack.push(v);                 
	            }
	         }  

	      	if (count < max) {
	      		connected = false;
	      		detect_connectivity(beg+1);
	      	} 
	      
		  } else if (beg < max && vertexList[beg].wasVisited == true ) {
			   detect_connectivity(beg+1);
		   }
	  }  
	   
	   public void find_path(Vertex start, Vertex finish)  {  
		   //set everthing to not being visited
		   for(int j=0; j<nVerts; j++)          
	         vertexList[j].wasVisited = false;
		   
		   int beg = start.num;
		   int end = finish.num;
		   StackX theStack = new StackX();
		   boolean found = false;
		   
		   if ( beg < max){
//		   System.out.println("from "+ beg + " to " +end);
			   
			   vertexList[beg].wasVisited = true; 
			   //displayVertex(beg);                
			   theStack.push(beg);               
	      
			   while( !found && !theStack.isEmpty())      
			   {
	         
				   int v = getAdjUnvisitedVertex( theStack.peek() );
				   if (v == end) {
					   vertexList[v].wasVisited = true;
					   found = true;
					   
				   }
				   
				   if(v == -1) {               
					   theStack.pop();
				   } else  {
					   vertexList[v].wasVisited = true;  
					   //displayVertex(v);                 
					   theStack.push(v);                 
				   }
			   } 
			  
	      
		   	}
		   
		   if (theStack.isEmpty()) {
			   System.out.println("There is no path from " + start.label + " to " + finish.label);
			   return;
		   }
		   
		   ArrayList<Vertex> sub = new ArrayList<Vertex>();
			while (!theStack.isEmpty()) {
				int num = theStack.pop();
				for (int i = 0; i< vertexList.length; i++) {
					if (num == vertexList[i].num){
						Vertex v = vertexList[i];
						sub.add(0,v);
						break;
					} 
				}
			}
			if (sub.size() != 0) {
				System.out.println("This is the path from " + start.label + " to " + finish.label);
				for (int i = 0; i < sub.size(); i++) {
					System.out.println(sub.get(i).label);
				}
			}
	  }
	   
	   // returns an unvisited vertex adj to v
	   public int getAdjUnvisitedVertex(int v)
	      {
	      for(int j=0; j<nVerts; j++)
	         if(adjMat[v][j]==1 && vertexList[j].wasVisited==false)
	            return j;
	      return -1;
	      } 
	
}
