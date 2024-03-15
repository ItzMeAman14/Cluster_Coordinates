import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class PGraph {

    public void Prim(Double G[][], int V) {
  
      Double INF = 9999999.0;
  
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
      System.out.println("Edge : Weight");
  
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
        System.out.println(x + " - " + y + " :  " + G[x][y]);
        selected[y] = true;
        no_edge++;
      }
    }

}


class get_data {
    List<List<String>> values = new ArrayList<>();
    List<List<Float>> DataList = new ArrayList<>();
    List<Float> temp = new ArrayList<>();

    get_data() {

        try {
            File obj = new File("testing_data.txt");
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
        try {
            for (int i = 0; i < values.size(); i++) {
                for (int j = 0; j < values.get(i).size(); j++) {
                    temp.add(Float.parseFloat(values.get(i).get(j)));
                }
                DataList.add(temp);
                temp = new ArrayList<>();
            }
        } catch (Exception d) {
            System.out.println("Cannot convert String to Float");
            System.out.println(d);
        }
        System.out.println(DataList);
    }

    List<Double> distance_points(){
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

class Clustering {

    public static void main(String[] args) {
        List<Double> coordinates_distance = new ArrayList<>();
        get_data obj = new get_data();
        obj.convert_data();
        coordinates_distance = obj.distance_points();
        System.out.println(coordinates_distance);

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
        
        // Converting List into Array to perform Prims Algorithm
        Double Adj_Mat_Array[][] = new Double[AdjMat_With_0.size()][AdjMat_With_0.get(0).size()];
        
        for(int i=0;i<AdjMat_With_0.size();i++){
            for(int j=0;j<AdjMat_With_0.get(0).size();j++){
                Adj_Mat_Array[i][j] = AdjMat_With_0.get(i).get(j);
            }
        }
        
        //Now here we have to apply Prims Algorithm in Adjacency Matrix
        //Making Object for prims Class
        
        PGraph obj_of_prims = new PGraph();
        obj_of_prims.Prim(Adj_Mat_Array, AdjMat_With_0.size()); 

    }
}