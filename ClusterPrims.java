import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClusterPrims {
	public static Double FindDiff(List<Double> A, List<Double> B) {
		Double sum = 0.0;

		for (int i = 0; i < A.size(); i++) {
			sum += Math.pow(A.get(i) - B.get(i), 2);
		}

		return Math.round(Math.sqrt(sum) * 1000) / 1000.0d;
	}

	//https://www.programiz.com/dsa/prim-algorithm
	public static Double[][] Prim(Double G[][], int V) {
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
		Double MST[][] = new Double[V][V];
		for (Double[] i : MST) {
			Arrays.fill(i, 0.0d);
		}

		while (no_edge < V - 1) {
			// For every vertex in the set S, find the all adjacent vertices
			// , calculate the distance from the vertex selected at step 1.
			// if the vertex is already in the set S, discard it otherwise
			// choose another vertex nearest to selected vertex at step 1.

			Double min = Double.POSITIVE_INFINITY;
			int x = 0; // row number
			int y = 0; // col number

			for (int i = 0; i < V; i++) {
				if (selected[i] == true) {
					for (int j = 0; j < V; j++) {
						// not in selected and there is an edge
						if (!selected[j] && Double.compare(G[i][j], 0) != 0) {
							if (Double.compare(min, G[i][j]) > 0) {
								min = G[i][j];
								x = i;
								y = j;
							}
						}
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

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(new File("data.txt"))) {
			List<List<Double>> points = new ArrayList<>();
			while (sc.hasNext()) {
				List<Double> point = new ArrayList<>();

				String pointInString = sc.nextLine();
				String[] pointSplitted = pointInString.split(",");
				for (String coord : pointSplitted) {
					point.add(Double.parseDouble(coord));
				}

				points.add(point);
			}

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

			System.out.println();
			System.out.println("Adjacency:");
			printGraph(adjacency);

			adjacency = Prim(adjacency, adjacency.length);

			System.out.println();
			System.out.println("MST:");
			printGraph(adjacency);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
