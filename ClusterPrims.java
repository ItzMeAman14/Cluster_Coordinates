/* Sample Output:
Points found:
1: [3.5, 7.9, 4.8]
2: [2.9, 13.3, 1.2]
3: [17.9, 13.2, 5.0]
4: [17.7, 0.5, 15.4]
5: [19.0, 7.0, 11.9]
6: [3.6, 1.0, 3.1]
7: [0.5, 18.1, 18.7]

Adjacency:
	  1	  2	  3	  4	  5	  6	  7	
	-----	-----	-----	-----	-----	-----	-----	
1 |	0.0	6.518	15.346	19.203	17.072	7.107	17.5	
2 |	6.518	0.0	15.474	24.177	20.332	12.466	18.304	
3 |	15.346	15.474	0.0	16.416	9.341	18.893	22.682	
4 |	19.203	24.177	16.416	0.0	7.496	18.718	24.829	
5 |	17.072	20.332	9.341	7.496	0.0	18.724	22.621	
6 |	7.107	12.466	18.893	18.718	18.724	0.0	23.353	
7 |	17.5	18.304	22.682	24.829	22.621	23.353	0.0	

MST:
	  1	  2	  3	  4	  5	  6	  7	
	-----	-----	-----	-----	-----	-----	-----	
1 |	0.0	6.518	15.346	0.0	0.0	7.107	17.5	
2 |	6.518	0.0	0.0	0.0	0.0	0.0	0.0	
3 |	15.346	0.0	0.0	0.0	9.341	0.0	0.0	
4 |	0.0	0.0	0.0	0.0	7.496	0.0	0.0	
5 |	0.0	0.0	9.341	7.496	0.0	0.0	0.0	
6 |	7.107	0.0	0.0	0.0	0.0	0.0	0.0	
7 |	17.5	0.0	0.0	0.0	0.0	0.0	0.0
*/

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

	public static List<List<Double>> getPointsFromFile() {
		return getPointsFromFile("data.txt");
	}

	public static List<List<Double>> getPointsFromFile(String fileName) {
		List<List<Double>> points = new ArrayList<>();
		try (Scanner sc = new Scanner(new File("data.txt"))) {
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
		List<List<Double>> points = getPointsFromFile();

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

		printGraph(adjacency, "Adjacency:");

		adjacency = Prim(adjacency, adjacency.length);

		printGraph(adjacency, "MST:");
	}
}
