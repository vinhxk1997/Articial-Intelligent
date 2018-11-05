package vinh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;


public class Bai8so {
	//
	// Trang thái kết thúc
	public static final int b[][] = { { 1, 2, 3 }, { 8, 0, 4 }, { 7, 6, 5} };
	//
	// Mảng lưu vị trí đúng của các giá trị trong trạng thái kết thúc
	public static int endState[][] = { {1, 1}, {0, 0}, {0, 1}, {0, 2}, {1, 2}, {2, 2}, {2, 1}, {2, 0}, {1, 0} };
	//
	// Mảng toán tử tương ứng với các thao tác di chuyển : right, left, down, up
	public static int dx[] = { 1, -1, 0 , 0 };
	public static int dy[] = { 0, 0, 1 , -1 };
	
	public static State NULL = new State();
	//
	// HashMap dùng để lưu vết cũng như loại bỏ các trạng thái trùng nhau
	public static HashMap<State, State> map1 = new HashMap<>();
	public static HashMap<State, State> map2 = new HashMap<>();
	public static HashMap<State, State> map3 = new HashMap<>();
	
	
	public static void breadth_first_search(State begin, State end) {		
		// Hàng đợi lựu các trạng thái kề chưa xét
		Queue<State> queue = new LinkedList<State>();
		// Ban đầu thêm trạng thái đầu tiên vào hàng đợi
		queue.add(begin);       
		 // Lưu vết trạng thái đầu với đối tượng NULL -> thuật tiện cho in lời giải 
		map1.put(begin, NULL); 
		
		while(!queue.isEmpty()) {
			// Lấy ra trạng thái ở đầu hàng đợi
			State cur = queue.poll();    
			
			// Kiểm tra nếu trạng thái hiện tại đã bằng trạng thái kết thúc
			// In lời giải và kết thúc hàm bằng lệnh return
			if(cur.equals(end)) {
				System.out.println("OK");
				printSolution(end, map1);				
				return;
			}
			
			// Duyệt mảng toán tử để thay đổi các trạng thái
			for(int i = 0; i < dx.length; ++i) {
				// Lấy ra vị trí của ô số 0 trong trạng thái hiện tại
				int local[] = cur.getLocal(0);
				
				// Thay đổi trạng thái bằng các cộng dx và dy tương ứng với toạn độ của ô số 0
				int x = local[0] + dx[i], y = local[1] + dy[i];
				State tmp = new State(cur.base);
				
				// Kiểm tra xem có thể di chuyển được không và trạng thái đố đã tồn tại trong map hay chưa
				if(tmp.canMove(x, y, local[0], local[1]) && !map1.containsKey(tmp)) {
					
					// Nếu có thể di chuyển và chưa từng tồn tại trước đó
					// Ta thêm trạng thái vào hàng đợi
					// Lưu vết trạng thái hiện tại với trạng thái trước đó
					queue.add(tmp);
					map1.put(tmp, cur);
				}
			}
		}
		
	}

	public static void best_first_search(State begin, State end) {		
		
		// Hàng đợi ưu tiên lưu các đối tượng và giúp lấy ra đối tượng có  giá trị nhỏ nhất trong hàng đợi
		PriorityQueue<State> queue = new PriorityQueue<>();
		// Ban đầu thêm trạng thái đầu tiên vào hàng đợi
		queue.add(begin);
		 // Lưu vết trạng thái đầu với đối tượng NULL -> thuật tiện cho in lời giải 
		map2.put(begin, NULL);
		
		
		while(!queue.isEmpty()) {
			// Lấy ra trạng thái có giá trị của hàm đánh giá nhỏ nhất
			State data = queue.poll();
			State cur = data;
			
			// Kiểm tra nếu trạng thái hiện tại đã bằng trạng thái kết thúc
			// In lời giải và kết thúc hàm bằng lệnh return
    		if(cur.equals(end)) {
    			System.out.println("OK");
    			printSolution(end, map2);    		
    			return;
    		}
    		
    		// Phần này tương tự comment trên
    		for(int i = 0; i < dx.length; ++i) {
    			int local[] = cur.getLocal(0);
				int x = local[0] + dx[i], y = local[1] + dy[i];
				State tmp = new State(cur.base);
				if(tmp.canMove(x, y, local[0], local[1]) && !map2.containsKey(tmp)) {
					queue.add(tmp);
					map2.put(tmp, cur);
				}
			}
		}
	}
	
	public static void hill_climb(State begin, State end) {		
		// Ngăn xếp lưu trạng thái kề
		Stack<State> st = new Stack<>();
		st.push(begin);
		map3.put(begin, NULL);
		
		// List lưu các trạng thái kề được sinh ra
		List<State> list = new ArrayList<>();
		
		while(!st.isEmpty()) {
			// Lấy phần tử top của ngăn xếp
			State cur = st.pop();
			
			// Kiểm tra nếu trạng thái hiện tại đã bằng trạng thái kết thúc
			// In lời giải và kết thúc hàm bằng lệnh return
			if(cur.equals(end)) {
				System.out.println("OK");
				printSolution(end, map3);				
				return;
			}
			
			list.clear();
			// Các thao tác tương tự các phần trên
			for(int i = 0; i < dx.length; ++i) {
				int local[] = cur.getLocal(0);
				int x = local[0] + dx[i], y = local[1] + dy[i];
				State tmp = new State(cur.base);
				
				if(tmp.canMove(x, y, local[0], local[1]) && !map3.containsKey(tmp)) {
					// Thêm trạng thái kề vào danh sách
					list.add(tmp);
					map3.put(tmp, cur);
				}
			}
			
			// Sắp xếp lại danh sách
			// Push toàn bộ phần tử trong danh sách vào hàng đợi
			Collections.sort(list, Collections.reverseOrder());
			for(State item : list) st.push(item);
		}
		
	}

	public static void main(String[] args) throws Exception {
		int a1[][] = { { 2, 8, 3 },
					   { 1, 6, 4 },
					   { 7, 0, 5} };
		State begin =  new State(a1);
		State end = new State(b);
		
		breadth_first_search(begin, end);
		System.out.println("-----------------------");
		best_first_search(begin, end);
		System.out.println("-----------------------");
		hill_climb(begin, end);
		
	}
	
	// Hàm in lời giải dùng đệ quy theo map lưu vết
	public static void printSolution(State cur, HashMap<State, State> map) {
		// Nếu gặp null tương ứng với trạng thái đầu
		// Hoặc không tồn tại trạng thái trong danh sách -> Dừng
    	if(!map.containsKey(cur)) return;
    	if(map.get(cur) == null) return;
    	
    	// Gọi đệ quy tìm tiếp các trạng thái còn lại
    	printSolution(map.get(cur), map);    	
    	
    	// In ra trạng thái cha của trạng thái hiện tại
    	System.out.println(cur.toString());
    }
	
	// Lớp đối tượng trạng thái
	public static class State implements Comparable<State>{
		public final int N = 3;
		
		// Gồm base là một matrix 3x3 
		public int base[][] = new int[N][N];
		
		// Các hàm tạo cơ bản
		public State() { base = new int[N][N]; };
		public State(int a[][]) {
			for(int i = 0; i < N; ++i)
				for(int j = 0; j < N; ++j) base[i][j] = a[i][j];
		}
		
		// Hàm xét có thể chuyển trạng thái hay không
		// Tham số:
		// x, y là tọa độ cần di chuyển đến
		// i, j là toạn độ của vị trí số 0
		public boolean canMove(int x, int y, int i, int j) {
			
			// Nếu x, y nằm ngoài phạm vi matrix sẽ dừng và trả vài sai
			if(x < 0 || x >= N) return false;
			if(y < 0 || y >= N) return false;
			
			// Ngược lại ta swap vị trí x, y với vị trí số 0 (i,j)
			// Và trả về đúng
			int tmp = base[i][j];
			base[i][j] = base[x][y];
			base[x][y] = tmp;
			return true;
		}
		
		// Hàm lấy tọa độ của giá trị x
		public int[] getLocal(int x) {
			for(int i = 0; i < N; ++i)
				for(int j = 0; j < N; ++j) if(base[i][j] == x) return new int[] { i, j };
			return new int[] { 0, 0 };
		}
		
		// Hàm đánh giá
		// Trả về tổng số bước ít nhất để tạo thành trạng thái kết thúc
		public int get() {
			int res = 0;
			for(int i = 0; i < N; ++i)
				for(int j = 0; j < N; ++j) {
					int local1[] = getLocal(base[i][j]);
					int local2[] = endState[base[i][j]];
					int x1 = local1[0], x2 = local2[0], y1 = local1[1], y2 = local2[1];
					if(base[i][j] != 0) res += Math.abs(x1 - x2) + Math.abs(y1 - y2);
				}
			return res;
		}
		
		@Override
		public int hashCode() {
			return Arrays.deepHashCode(base);
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this == obj) {
                return true;
            } else if(obj == null) {
                return false;
            }else if(obj instanceof State ) {
                State st = (State) obj;
                for(int i = 0; i < N; ++i)
                	for(int j = 0; j < N; ++j) if(base[i][j] != st.base[i][j]) return false;
                return true;
            }
            return false;
		}
		
		@Override
		public String toString() {
			StringBuilder build = new StringBuilder();
			for(int i = 0; i < N; ++i) {
				String tmp = base[i][0] + "";
				for(int j = 1; j < N; ++j) tmp += " " + base[i][j];
				build.append(tmp + "\n");
			}
			return build.toString();
		}
		@Override
		public int compareTo(State o) {
			return this.get()-o.get();
		}
	}
}
