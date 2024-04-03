import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Cluster {
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

		System.out.println("\n\n\nAplying kruskals...");
		Double sum = Kruskal.KruskalAlgo(points.size(), adjacency);
		System.out.println("Sum (Kruskal): " + sum);

		System.out.println("Average (w.r.t. vertices): " + (sum / adjacency.length));
		System.out.println("Average (w.r.t. edges): " + (sum / (adjacency.length - 1)));
	}

	public static Double FindDiff(List<Double> A, List<Double> B) {
		Double sum = 0.0;

		for (int i = 0; i < A.size(); i++) {
			sum += Math.pow(A.get(i) - B.get(i), 2);
		}

		return Math.sqrt(sum);
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
				if (Double.compare(graph[i][j], 0d) == 0)
					continue;

				System.out.println((i + 1) + "<-->" + (j + 1) + "\t ==) " + graph[i][j]);
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
}

// https://favtutor.com/blogs/kruskal-algorithm-java
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

	public static Double KruskalAlgo(int n, Double[][] adjacency) {
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

		return ans;

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

