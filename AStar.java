package vinh;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class AStar {
	ArrayList<Edge> listEdge;
	Vertex startVertex = null;
	Vertex endVertex = null;
	Vertex currVertex;
	int totalDist = 0;
	ArrayList<Vertex> listVertex;
	PriorityQueue<Vertex> listOpen;
	ArrayList<Vertex> listClose;
	HashMap<String, String> vertexHistory;
	
	public static void main(String[] args) {
		String fileName = "findWay-v3.txt";
		ArrayList<Edge> listEdge = new ArrayList<>();
		ArrayList<Vertex> listVertex = new ArrayList<>();
		FileReader fileReader;
		BufferedReader bufferedReader;
		try {
			String line;
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			// đọc đỉnh đến khi gặp 'edge'
			while ((line = bufferedReader.readLine()).equals("edge") != true) {
				String[] data = line.split(" ");
				listVertex.add(new Vertex(data[0], Integer.parseInt(data[1])));
			}
			// đọc cạnh
			while ((line = bufferedReader.readLine()) != null) {
				String[] data = line.split(" ");
				listEdge.add(new Edge(data[0], data[1], Integer.parseInt(data[2])));
			}
			bufferedReader.close();
		} catch (IOException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		}
		// Arad là đỉnh đầu, Bucharest là đỉnh kết
		AStar astar = new AStar(listEdge, listVertex, "Arad", "Bucharest");
		astar.astarSearch();
	}
	
	public AStar(ArrayList<Edge> listEdge, ArrayList<Vertex> listVertex, String start, String end) {
		listOpen = new PriorityQueue<>();
		listClose = new ArrayList<>();
		this.listEdge = listEdge;
		vertexHistory = new HashMap<String, String>();
		this.listVertex = listVertex;
		this.startVertex = findVertex(start);
		// đặt khoảng cách từ điếm đầu đến chính nó bằng 0
		startVertex.distMin = 0;
		this.endVertex = findVertex(end);
	}
	
	void astarSearch(){
		// thêm điểm đầu vào danh sách chờ duyệt
				listOpen.add(startVertex);
				// thêm điểm đầu vào lịch sử đường đi
				vertexHistory.put(startVertex.name, "null");
				while (!listOpen.isEmpty()) {
					// lấy ra đỉnh đầu tiên trong listOpen
					currVertex = listOpen.poll();
					// nếu trùng điểm kết thì in kết quả
					if (currVertex.equals(endVertex)) {
						printSolution();
						break;
					}
					// thêm đỉnh hiện tại vào listClose (list đã xét xong)
					listClose.add(currVertex);
					// list các đỉnh kề với đỉnh hiện tại
					ArrayList<Vertex> newVertexs = new ArrayList<>();
					for (Edge edge : listEdge) {
						/*thêm đỉnh thứ 2 của cung đường đi vào list kề nếu điểm đang xét là đỉnh đầu
						 của cung*/
						if (edge.v1.equals(currVertex.name) == true) {
							Vertex vertex = findVertex(edge.v2);
							newVertexs.add(vertex);
							/* thêm đỉnh đầu của cung đường đi vào list kề nếu điểm đang xét là đỉnh thứ 2
							 của cung*/
						} else if (edge.v2.equals(currVertex.name) == true) {
							Vertex vertex = findVertex(edge.v1);
							newVertexs.add(vertex);
						}
					}
					addToOpenList(newVertexs, currVertex);
				}
	}
	
	private void addToOpenList(ArrayList<Vertex> newVertexs, Vertex oldVertex) {
		for (Vertex vertex : newVertexs) {
			int a = oldVertex.distMin + getDist(vertex.name, oldVertex.name);
			//nếu đỉnh đang xét đã có trong listOpen (1 đỉnh có thể kề nhiều đỉnh)
			if (listOpen.contains(vertex)) {
				/* nếu khoảng cách ngắn nhất từ điểm đầu đến điểm hiện tại lớn hơn tổng của khoảng cách ngắn
				 nhất đến điểm trước + độ dài cung đường đi nối điểm trước đến điểm hiện tại
				 thì cập nhật lại khoảng cách ngắn nhất*/				
				if (vertex.distMin > a) {
					vertex.distMin = a;
					vertex.h = vertex.distMin + vertex.distEnd;
					//thêm đỉnh hiện tại vào map đường đi truy vết nếu đường đi này chưa tồn tại
					if (!vertexHistory.containsKey(vertex.name))
						vertexHistory.put(vertex.name, oldVertex.name);
				}
			}
			//nếu đỉnh hiện tại chưa có trong listOpen
			if (!listOpen.contains(vertex)) {
				//cập nhật lại khoảng cách ngắn nhất đến điểm hiện tại (mặc định là dương vô cùng)
				vertex.distMin = a;
				vertex.h = vertex.distMin + vertex.distEnd;
				//thêm đỉnh hiện tại vào map đường đi truy vết
				if (!vertexHistory.containsKey(vertex.name)) {
					vertexHistory.put(vertex.name, oldVertex.name);
					listOpen.add(vertex);
				}
			}
			//nếu đỉnh hiện tại đã có trong listClose (những điểm đã xét xong)
			if (listClose.contains(vertex)) {
				/* nếu khoảng cách ngắn nhất đến điểm hiện tại lớn hơn tổng khoảng cách ngắn
				 nhất đến điểm trc + độ dài cung đường đi nối điểm trước đến điểm hiện tại
				 thì xóa khỏi listClose và thêm lại vào listOpen để xét lại*/
				if (vertex.distMin > a) {
					listOpen.add(vertex);
					listClose.remove(vertex);
				}
			}
		}
	}

	void printSolution() {
		ArrayList<String> result = new ArrayList<>();
		String current = endVertex.name;
		//truy vết từ dưới lên
		while (current.equals("null") != true) {
			result.add(current);
			current = vertexHistory.get(current);
		}
		//đảo ngược dãy kết quả để in từ trên xuống
		Collections.reverse(result);
		System.out.print(result.get(0));
		for (int i = 1; i < result.size(); i++) {
			System.out.print("->" + result.get(i));
		}
		//tính đường đi giữa các cạnh
		for (int i = 0; i < result.size() - 1; i++) {
			totalDist += getDist(result.get(i), result.get(i + 1));
		}
		System.out.println("\nCost: " + totalDist);
	}
	//tìm đỉnh theo tên
	public Vertex findVertex(String name) {
		for (Vertex vertex : listVertex) {
			if (vertex.name.equals(name) == true)
				return vertex;
		}
		return null;
	}
	//tìm cạnh theo 2 đỉnh đầu và đích
	public Edge findEdge(Vertex start, Vertex end) {
		String name = start.name + end.name;
		for (Edge edge : listEdge) {
			if (edge.name1.equals(name) == true || edge.name2.equals(name) == true)
				return edge;
		}
		return null;
	}
	//tính khoảng cách cung đường đi
	public int getDist(String start, String end) {
		int result = 0;
		String name = start + end;
		for (Edge edge : listEdge) {
			if (edge.name1.equals(name) == true || edge.name2.equals(name) == true) {
				result = edge.dist;
			}
		}
		return result;
	}
	
	public static class Edge {
		public String v1, v2, name1, name2;
		public int dist;

		public Edge(String v1, String v2, int dist) {
			super();
			this.v1 = v1;
			this.v2 = v2;
			this.name1 = v1 + v2;
			this.name2 = v2 + v1;
			this.dist = dist;
		}
	}

	public static class Vertex implements Comparable<Vertex> {
		public String name;

		public int distMin = Integer.MAX_VALUE;

		public int distEnd;

		public int h;

		public Vertex() {
			super();
		}

		public Vertex(String name, int distEnd) {
			this.name = name;
			this.distEnd = distEnd;
		}

		public int compareTo(Vertex other) {
			return this.h - other.h;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + distMin;
			result = prime * result + distEnd;
			result = prime * result + h;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vertex other = (Vertex) obj;
			if (distMin != other.distMin)
				return false;
			if (distEnd != other.distEnd)
				return false;
			if (h != other.h)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	}
}

