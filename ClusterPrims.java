/* Sample Output:
Points found:
1: [0.4, 0.3, 5.0, 6.8, 12.1, 6.9]
2: [17.4, 4.4, 14.6, 15.7, 17.3, 13.7]
3: [17.5, 12.4, 4.6, 3.6, 4.4, 2.2]
4: [14.3, 5.2, 2.6, 15.3, 19.1, 9.6]
5: [11.4, 4.4, 16.3, 10.6, 8.9, 5.6]
6: [15.6, 15.2, 19.0, 0.9, 4.7, 12.5]
7: [11.3, 7.9, 4.1, 1.8, 6.7, 6.1]
8: [10.8, 19.0, 18.3, 14.1, 11.6, 8.2]
9: [7.6, 0.3, 10.7, 16.1, 14.1, 18.1]

Adjacency:
	  1	  2	  3	  4	  5	  6	  7	  8	  9	
	-----	-----	-----	-----	-----	-----	-----	-----	-----	
1 |	0.0	23.462	23.035	18.749	17.084	27.749	15.237	26.267	17.328	
2 |	23.462	0.0	24.68	13.208	14.18	22.77	22.87	18.322	12.563	
3 |	23.035	24.68	0.0	21.767	17.858	18.229	9.093	21.777	28.014	
4 |	18.749	13.208	21.767	0.0	18.408	28.174	19.152	22.557	15.245	
5 |	17.084	14.18	17.858	18.408	0.0	17.347	15.609	15.615	16.618	
6 |	27.749	22.77	18.229	28.174	17.347	0.0	18.427	16.683	26.564	
7 |	15.237	22.87	9.093	19.152	15.609	18.427	0.0	22.468	22.765	
8 |	26.267	18.322	21.777	22.557	15.615	16.683	22.468	0.0	22.934	
9 |	17.328	12.563	28.014	15.245	16.618	26.564	22.765	22.934	0.0	

MST:
	  1	  2	  3	  4	  5	  6	  7	  8	  9	
	-----	-----	-----	-----	-----	-----	-----	-----	-----	
1 |	0.0	0.0	0.0	0.0	0.0	0.0	15.237	0.0	0.0	
2 |	0.0	0.0	0.0	13.208	14.18	0.0	0.0	0.0	12.563	
3 |	0.0	0.0	0.0	0.0	0.0	0.0	9.093	0.0	0.0	
4 |	0.0	13.208	0.0	0.0	0.0	0.0	0.0	0.0	0.0	
5 |	0.0	14.18	0.0	0.0	0.0	0.0	15.609	15.615	0.0	
6 |	0.0	0.0	0.0	0.0	0.0	0.0	0.0	16.683	0.0	
7 |	15.237	0.0	9.093	0.0	15.609	0.0	0.0	0.0	0.0	
8 |	0.0	0.0	0.0	0.0	15.615	16.683	0.0	0.0	0.0	
9 |	0.0	12.563	0.0	0.0	0.0	0.0	0.0	0.0	0.0	
Average (w.r.t. vertices): 12.465333333333334
Average (w.r.t. edges): 14.0235
*/

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class KruskalAlgorithm {
    private int V; // Number of vertices
    private List<int[]> edges;

    public KruskalAlgorithm(int V) {
        this.V = V;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int src, int dest, Double weight) {
        edges.add(new int[]{src, dest, (int) (weight * 1000)}); // Multiply weight by 1000 to handle double values
    }

    private int find(int[] parent, int i) {
        if (parent[i] != i)
            parent[i] = find(parent, parent[i]);
        return parent[i];
    }

    private void union(int[] parent, int[] rank, int x, int y) {
        int xRoot = find(parent, x);
        int yRoot = find(parent, y);

        if (rank[xRoot] < rank[yRoot])
            parent[xRoot] = yRoot;
        else if (rank[yRoot] < rank[xRoot])
            parent[yRoot] = xRoot;
        else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }

    public void kruskalMST(Double[][] adjacencyMatrix) {
        List<int[]> result = new ArrayList<>();
        int[] parent = new int[V];
        int[] rank = new int[V];

        for (int i = 0; i < V; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        // Convert double weights to integer (multiply by 1000)
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    addEdge(i, j, adjacencyMatrix[i][j] * 1000);
                }
            }
        }

        edges.sort(Comparator.comparingInt(e -> e[2]));

        int e = 0;
        int i = 0;

        while (e < V - 1 && i < edges.size()) {
            int[] edge = edges.get(i++);
            int x = find(parent, edge[0]);
            int y = find(parent, edge[1]);

            if (x != y) {
                result.add(edge);
                union(parent, rank, x, y);
                e++;
            }
        }

        for (int[] edge : result) {
            System.out.println(edge[0] + " - " + edge[1] + ": " + edge[2] / 1000.0); // Convert back to double
        }

    }

    // public static void main(String[] args) {
    //     int V = 4;
    //     KruskalAlgorithm kruskal = new KruskalAlgorithm(V);
    //
    //     // Example adjacency matrix with double values
    //     double[][] adjacencyMatrix = {
    //             {0, 10.5, 6.2, 5},
    //             {10.5, 0, 0, 15.3},
    //             {6.2, 0, 0, 4.7},
    //             {5, 15.3, 4.7, 0}
    //     };
    //
    //     kruskal.kruskalMST(adjacencyMatrix);
    // }
}
public class ClusterPrims {
	public static Double FindDiff(List<Double> A, List<Double> B) {
		Double sum = 0.0;

		for (int i = 0; i < A.size(); i++) {
			sum += Math.pow(A.get(i) - B.get(i), 2);
		}

		return Math.sqrt(sum);
	}

	// https://www.programiz.com/dsa/prim-algorithm
	public static Double[][] Prim(Double G[][], int V) {
		int no_edge;

		boolean[] selected = new boolean[V];

		Arrays.fill(selected, false);

		no_edge = 0;

		selected[0] = true;

		Double MST[][] = new Double[V][V];
		for (Double[] i : MST) {
			Arrays.fill(i, 0.0d);
		}

		while (no_edge < V - 1) {
			Double min = Double.POSITIVE_INFINITY;
			int x = 0; // row number
			int y = 0; // col number

			for (int i = 0; i < V; i++) {
				if (selected[i] == false)
					continue;
				for (int j = 0; j < V; j++) {
					if (selected[j] || Double.compare(G[i][j], 0) == 0)
						continue;
					if (Double.compare(min, G[i][j]) > 0) {
						min = G[i][j];
						x = i;
						y = j;
					}
				}
			}
			MST[x][y] = G[x][y];
			MST[y][x] = G[x][y];
			selected[y] = true;
			no_edge++;
		}

		return MST;
	}

	public static void printGraph(Double[][] graph, String message) {
		System.out.println();
		System.out.println(message);
		printGraph(graph);
	}

	public static void printGraph(Double[][] graph) {
		System.out.print("\t");
		for (int j = 0; j < graph.length; j++) {
			System.out.print("  " + (j + 1) + "\t");
		}
		System.out.println();
		System.out.print("\t");
		for (int j = 0; j < graph.length; j++) {
			System.out.print("-----\t");
		}
		System.out.println();
		for (int i = 0; i < graph.length; i++) {
			System.out.print((i + 1) + " |\t");
			for (int j = 0; j < graph.length; j++) {
				if (graph[i][j] == null)
					graph[i][j] = 0.000d;
				System.out.print(graph[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public static void printSelectedEdges(Double[][] graph) {
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < i; j++) {
				if (Double.compare(graph[i][j], 0d) == 0) continue;

				System.out.println((i+1) + "<-->" + (j+1) + "\t ==) " + graph[i][j]);
			}
		}
	}

	public static List<List<Double>> getPointsFromFile() {
		return getPointsFromFile("data.txt");
	}

	public static List<List<Double>> getPointsFromFile(String fileName) {
		List<List<Double>> points = new ArrayList<>();
		try (Scanner sc = new Scanner(new File(fileName))) {
			while (sc.hasNext()) {
				List<Double> point = new ArrayList<>();

				String pointInString = sc.nextLine();
				String[] pointSplitted = pointInString.split(",");
				for (String coord : pointSplitted) {
					point.add(Double.parseDouble(coord));
				}

				points.add(point);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return points;
	}

	public static void main(String[] args) {
		List<List<Double>> points = getPointsFromFile("Iris.txt");

		if (points.size() <= 0)
			return;

		System.out.println("Points found:");
		for (int i = 0; i < points.size(); i++) {
			System.out.println((i + 1) + ": " + points.get(i));
		}

		Double[][] adjacency = new Double[points.size()][points.size()];
		for (int i = 0; i < points.size() - 1; i++) {
			for (int j = i + 1; j < points.size(); j++) {
				adjacency[i][j] = FindDiff(points.get(i), points.get(j));
				adjacency[j][i] = FindDiff(points.get(i), points.get(j));
			}
		}

		// printGraph(adjacency, "Adjacency:");
		// printSelectedEdges(adjacency);

		adjacency = Prim(adjacency, adjacency.length);

		// printGraph(adjacency, "MST:");

		KruskalAlgorithm kA = new KruskalAlgorithm(points.size());
		kA.kruskalMST(adjacency);

	// 	Double sum = 0d;
	// 	int count = 0;
	// 	for (int i = 0; i < adjacency.length; i++) {
	// 		for (int j = 0; j < i; j++) {
	// 			if (Double.compare(adjacency[i][j], 0d) == 0) continue;
	// 			sum += adjacency[i][j];
	// 			count ++;
	// 		}
	// 	}
	//
	// 	System.out.println(sum);
	// 	System.out.println(count);
	// 	System.out.println("Average (w.r.t. vertices): " + (sum / adjacency.length));
	// 	System.out.println("Average (w.r.t. edges): " + (sum / (adjacency.length - 1)));
	}
}
