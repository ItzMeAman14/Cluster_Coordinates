import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class Kruskal {
	static int[] parent;
	static int[] rank;

	public static class Edge implements Comparable<Edge> {
		int source;
		int destination;
		Double distance;

		Edge(int vertex1, int vertex2, Double weight) {
			this.source = vertex1;
			this.destination = vertex2;
			this.distance = weight;
		}

		@Override
		public int compareTo(Edge o) {
			// comparing the edges by their weights.
			return Double.compare(this.distance, o.distance);
		}
	}

	public static List<Edge> KruskalAlgo(int n, Double[][] adjacency) {
		// 1. to store the edges in sorted order.
		// array of all the edges containing pair of vertices and weight given to that
		// edge.
		List<Edge> edges = new ArrayList<>();

		for (int i = 0; i < adjacency.length; i++) {
			for (int j = 0; j < i; j++) {
				if (Double.compare(adjacency[i][j], 0d) == 0)
					continue;
				edges.add(new Edge(i, j, adjacency[i][j]));
			}
		}

        // for (int i = 0; i < adjacency.length; i++) {
		// 	for (int j = 0; j < i; j++) {
		// 		System.out.println(adjacency[i][j]);
        //         // System.out.println();
		// 	}
		// }

        // for (int i = 0; i < edges.size(); i++) {
		// 	System.out.println(edges.get(i));
		// }

		Double ans = 0d;
		Collections.sort(edges);

		parent = new int[n + 1];
		rank = new int[n + 1];

		// assigning each element as a unique group and assigning rank 1 to them.
		for (int i = 0; i < parent.length; i++) {
			parent[i] = i;
			rank[i] = 1;
		}

		for (int i = 0; i < edges.size(); i++) {
			int vertex1 = edges.get(i).source;
			int vertex2 = edges.get(i).destination;
			Double weight = edges.get(i).distance;

			// checking if the vertices belong to same group or not.
			boolean flag = union(vertex1, vertex2);

			// if vertices are from unique groups that implies , if both vertices would be
			// connected, it won't create a loop.
			if (flag == false) {
				System.out.println(vertex1 + "<-->" + vertex2 + "\t==)\t" + weight);
				ans += weight;
			}
		}

		return edges;

	}

	public static int find(int component) {

		// if the component which is a leader already is returned.
		if (parent[component] == component) {
			return component;
		}

		// finding the parent or group leader of that element
		int temp = find(parent[component]);
		parent[component] = temp;
		return temp;
	}

	public static boolean union(int vertex1, int vertex2) {

		// finding the parent or group leader of both of these vertices.
		int parentOfVertex1 = find(vertex1);
		int parentOfVertex2 = find(vertex2);

		// if parent or leader of both vertices is same, this implies, that if they get
		// connected, it would create a loop
		if (parentOfVertex1 == parentOfVertex2) {
			return true;
		}

		// deciding the parent of vertex according to their ranks.
		if (rank[parentOfVertex1] > rank[parentOfVertex2]) {
			parent[parentOfVertex2] = parentOfVertex1;
		} else if (rank[parentOfVertex1] < rank[parentOfVertex2]) {
			parent[parentOfVertex1] = parentOfVertex2;
		} else {
			parent[parentOfVertex1] = parentOfVertex2;
			rank[parentOfVertex2]++;
		}

		return false;
	}
}



class PGraph {
    // Prims Algorithm
    public Double[] Prim(Double G[][], int V,List<Double> Coordinates) {
        
      Double INF = 9999999.0;
      Double PrimsMat[] = new Double[V-1];
      int PrimsMat_i = 0;  
      int no_edge; // number of edge
      // create a array to track selected vertex
      // selected will become true otherwise false
      boolean[] selected = new boolean[V];
  
      // set selected false initially
      Arrays.fill(selected, false);
  
      // set number of edge to 0
      no_edge = 0;
  
      // the number of egde in minimum spanning tree will be
      // always less than (V -1), where V is number of vertices in
      // graph
  
      // choose 0th vertex and make it true
      selected[0] = true;
  
      // print for edge and weight
    //   System.out.println("Edge : Weight");
  
      while (no_edge < V - 1) {
        // For every vertex in the set S, find the all adjacent vertices
        // , calculate the distance from the vertex selected at step 1.
        // if the vertex is already in the set S, discard it otherwise
        // choose another vertex nearest to selected vertex at step 1.
  
        Double min = INF;
        int x = 0; // row number
        int y = 0; // col number
  
        for (int i = 0; i < V; i++) {
          if (selected[i] == true) {
            for (int j = 0; j < V; j++) {
              // not in selected and there is an edge
              if (!selected[j] && G[i][j] != 0) {
                if (min > G[i][j]) {
                  min = G[i][j];
                  x = i;
                  y = j;
                }
              }
            }
          }
        }
        // System.out.println(x + " - " + y + " :  " + G[x][y]);
        if (G[x][y] != null){
            PrimsMat[PrimsMat_i] = G[x][y];
            PrimsMat_i++;
            
        }
        selected[y] = true;
        no_edge++;
      }
      //Returning All Edges with Shortest Path in PrimsMat Array
      return PrimsMat;
    }
}


class get_data {
    //Initializing Lists
    List<List<String>> values = new ArrayList<>();
    List<List<Double>> DataList = new ArrayList<>();
    List<Double> temp = new ArrayList<>();

    get_data() {
        // Taking data from the text file in String Form
        try {
            File obj = new File("testing_data.txt"); // File location is entered instead of data.txt
            Scanner myReader = new Scanner(obj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                values.add(Arrays.asList(data.split(",")));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An Error Occured.");
            e.printStackTrace();
        }
    }

    void convert_data() {
        //Converting Data readed from text file from String to Float
        try {
            for (int i = 0; i < values.size(); i++) {
                for (int j = 0; j < values.get(i).size(); j++) {
                    temp.add(Double.parseDouble(values.get(i).get(j)));
                }
                DataList.add(temp);
                temp = new ArrayList<>();
            }
        } catch (Exception d) {
            System.out.println("Cannot convert String to Float");
            System.out.println(d);
        }
        // DataList contain all the readed data
        // System.out.println(DataList);
    }

    List<Double> distance_points(){
        //Finding distance points using formula ^/(x1 -x2)^2 - (y1 - y2)^2   
        //Up here ^/ is square root
        List<Double> coordinates = new ArrayList<>();
        Double temp = 0.0;
        Double diff = 0.0;
        try{
        for(int i = 0;i<DataList.size();i++){
            for(int j = i+1;j<DataList.size();j++){
                for(int k = 0;k<DataList.get(i).size();k++){
                    temp += (DataList.get(i).get(k) - DataList.get(j).get(k));
                    // System.out.printf("%f %f\n",DataList.get(i).get(k),DataList.get(j).get(k));
                    temp = Math.pow(temp, 2);
                    diff += temp;
                    temp = 0.0;
                }
                diff = Math.sqrt(diff);
                // System.out.println("Diff = "+diff);
                coordinates.add(diff);
                temp = 0.0;
                diff = 0.0;
            }
        }
    }
    catch(IndexOutOfBoundsException d){
        System.out.println(d);
    }
        return coordinates;
    }

    List<List<Double>> MakeAdjacencyMat(){
        //Initializing Empty Nested List
        List<List<Double>> AdjMat = new ArrayList<>();
        List<Double> temp = new ArrayList<>();
        for(int i=0;i<DataList.size();i++){
            for(int j=0;j<DataList.size();j++){
                temp.add(0.0);
            }
            AdjMat.add(temp);
            temp = new ArrayList<>();
        }
        //AdjMat is now initialized with 0 of size N*M(DataList)
        
        return AdjMat;
    }

}

class test {

    public static void main(String[] args) {
        //Making objects
        List<Double> coordinates_distance = new ArrayList<>();
        get_data obj = new get_data();
        obj.convert_data();
        coordinates_distance = obj.distance_points();
        // Coordinates_distance contain all the distances between each points
        // System.out.println(coordinates_distance);
        //Setting Values in Adjacency Matrix
        List<List<Double>> AdjMat_With_0 =  obj.MakeAdjacencyMat();
        int k = 0;
        for(int i=0;i<AdjMat_With_0.size();i++){
            for(int j=i+1;j<AdjMat_With_0.size();j++){
                if(i!=j){
                    AdjMat_With_0.get(i).set(j,coordinates_distance.get(k));
                    k+=1;
                }
            }
        }
        //AdjMat_With_0 is now updated with Values of distance
        // System.out.println(coordinates_distance);
        // Converting List into Array to perform Prims Algorithm
        Double[][] Adj_Mat_Array = new Double[AdjMat_With_0.size()][AdjMat_With_0.get(0).size()];
        
        for(int i=0;i<AdjMat_With_0.size();i++){
            for(int j=0;j<AdjMat_With_0.get(0).size();j++){
                Adj_Mat_Array[i][j] = AdjMat_With_0.get(i).get(j);
            }
        }
        
        // Data for testing in Kruskal 

        // System.out.println("{");
        // for(int i=0;i<AdjMat_With_0.size();i++){
        //     System.out.printf("{");
        //     for(int j=0;j<AdjMat_With_0.get(0).size();j++){
        //         if(j==AdjMat_With_0.get(0).size()-1){
        //             System.out.printf("%f",Adj_Mat_Array[i][j]);
        //         }
        //         else{
        //             System.out.printf("%f,",Adj_Mat_Array[i][j]);
        //         }

        //     }
        //     if(i==AdjMat_With_0.size()-1){
        //         System.out.println("}");
                
        //     }
        //     else{
        //         System.out.println("},");
        //     }
        // }
        // System.out.println("};");
        //Now here we have to apply Prims Algorithm in Adjacency Matrix
        //Making Object for prims Class
        

        PGraph obj_of_prims = new PGraph();
        Double[] PrimsMat = obj_of_prims.Prim(Adj_Mat_Array, AdjMat_With_0.size(),coordinates_distance); 
        // for(int i=0;i<Adj_Mat_Array.length;i++){
        //     for(int j=0;j<Adj_Mat_Array[0].length;j++){
        //         System.out.printf("%f ",AdjMat_With_0.get(i).get(j));
        //     }
        //     System.out.println();
        // }


        for(int i=0;i<Adj_Mat_Array.length;i++){
            for(int j=0;j<Adj_Mat_Array[i].length;j++){
                System.out.printf("%f ",Adj_Mat_Array[i][j]);
            }
            System.out.println();
        }
        
        // System.out.println("Sum (Kruskal) : " + Kruskal.KruskalAlgo(Adj_Mat_Array.length, Adj_Mat_Array));

        Kruskal.KruskalAlgo(Adj_Mat_Array.length, Adj_Mat_Array);

        //Taking Average of all Coordinates filtered from Prims Algorithm
        Double sum = 0d;
        try{
            for(int i=0;i<PrimsMat.length;i++){
                // System.out.println(PrimsMat[i]);
                if (PrimsMat[i] != null){
                    sum += PrimsMat[i];
                }
            }
        }
        catch(NullPointerException d){
            System.out.println("A null value try to Sum(Error)");
        }

        // System.out.println("All the values between every coordinates");
        // System.out.println(coordinates_distance);

        // System.out.println("PrimsMaT Selected Nodes");
        // for (Double double1 : PrimsMat) {
        //     System.out.println(double1);
        // }
        // System.out.println(PrimsMat.length);

        // System.out.println("Sum (Prims) : " +sum);

        // Double avg_of_points_edges;//Consist of Average of all shortest path
        // Double avg_of_points_vertices;//Consist of Average of all vertices path
        // avg_of_points_edges = sum/(Len-1);  // Distances Average
        // avg_of_points_vertices = sum/(Len); // Total Points Average
        // System.out.println("Average of Edges = "+avg_of_points_edges);
        // System.out.println("Average of Vertices = "+avg_of_points_vertices);
    }
}