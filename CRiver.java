package vinh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class CRiver {

	public static HashMap<State, State> map1 = new HashMap<>();
	public static HashMap<State, State> map2 = new HashMap<>();
	public static HashMap<State, State> map3 = new HashMap<>();
	public static HashMap<State, State> map4 = new HashMap<>();	
	
	static int[] dx = { 1, 0, 1, 2, 0 };
	static int[] dy = { 0, 1, 1, 0, 2 };

	public static boolean check(State st) {
		if (st.getA() != 0 && st.getB() != 0) {
			if (st.getA() >= st.getB())
				return true;
		} else if (st.getA() == 0 || st.getB() == 0)
			return true;
		return false;
	}

	public static boolean checkAll(State st1, State st2) {
		return (check(st2) && check(st1) && st1.getA() >= 0 && st1.getB() >= 0 && st2.getA() >= 0 && st2.getB() >= 0);
	}

	public static void BFS(State begin, State end) {
		Queue<State> queue = new LinkedList<State>();

		queue.add(begin);

		map1.put(begin, new State(3, 3, 0));

		while (!queue.isEmpty()) {
			State s1 = queue.poll();
			State s2 = new State(3 - s1.a, 3 - s1.b, s1.c);

			if (s1.equals(end)) {
				System.out.println("OK");
				return;
			}

			int c = s1.getC();
			if (c == 1) {
				for (int i = 0; i < dx.length; ++i) {
					State tmp1 = new State(s1.getA() - dx[i], s1.getB() - dy[i], 0);
					State tmp2 = new State(s2.getA() + dx[i], s2.getB() + dy[i], 0);

					if (checkAll(tmp1, tmp2) && !map1.containsKey(tmp1)) {
						queue.add(tmp1);
						map1.put(tmp1, s1);
					}
				}
			} else {
				for (int i = 0; i < dx.length; ++i) {
					State tmp1 = new State(s1.getA() + dx[i], s1.getB() + dy[i], 1);
					State tmp2 = new State(s2.getA() - dx[i], s2.getB() - dy[i], 1);

					if (checkAll(tmp1, tmp2) && !map1.containsKey(tmp1)) {
						queue.add(tmp1);;
						map1.put(tmp1, s1);
					}
				}
			}
		}
	}

	public static void DFS(State begin, State end) {
		Stack<State> stack = new Stack<State>();

		stack.push(begin);

		map2.put(begin, new State(3, 3, 0));

		while (!stack.isEmpty()) {
			State s1 = stack.pop();
			State s2 = new State(3 - s1.a, 3 - s1.b, s1.c);

			if (s1.equals(end)) {
				System.out.println("OK");
				return;
			}

			int c = s1.getC();
			if (c == 1) {
				for (int i = 0; i < dx.length; ++i) {
					State tmp1 = new State(s1.getA() - dx[i], s1.getB() - dy[i], 0);
					State tmp2 = new State(s2.getA() + dx[i], s2.getB() + dy[i], 0);

					if (checkAll(tmp1, tmp2) && !map2.containsKey(tmp1)) {
						stack.push(tmp1);
						map2.put(tmp1, s1);
					}
				}
			} else {
				for (int i = 0; i < dx.length; ++i) {
					State tmp1 = new State(s1.getA() + dx[i], s1.getB() + dy[i], 1);
					State tmp2 = new State(s2.getA() - dx[i], s2.getB() - dy[i], 1);

					if (checkAll(tmp1, tmp2) && !map2.containsKey(tmp1)) {
						stack.push(tmp1);
						map2.put(tmp1, s1);
					}
				}
			}
		}
	}

	public static void BestFirst(State begin, State end) {
		PriorityQueue<State> queue = new PriorityQueue<State>();

		queue.add(begin);
		map3.put(begin, new State(3, 3, 0));

		while (!queue.isEmpty()) {
			State s1 = queue.poll();
			State s2 = new State(3 - s1.a, 3 - s1.b, s1.c);

			if (s1.equals(end)) {
				System.out.println("OK");
				return;
			}

			int c = s1.getC();
			if (c == 1) {
				for (int i = 0; i < dx.length; ++i) {
					State tmp1 = new State(s1.getA() - dx[i], s1.getB() - dy[i], 0);
					State tmp2 = new State(s2.getA() + dx[i], s2.getB() + dy[i], 0);

					if (checkAll(tmp1, tmp2) && !map3.containsKey(tmp1)) {
						queue.add(tmp1);
						map3.put(tmp1, s1);
					}
				}
			} else {
				for (int i = 0; i < dx.length; ++i) {
					State tmp1 = new State(s1.getA() + dx[i], s1.getB() + dy[i], 1);
					State tmp2 = new State(s2.getA() - dx[i], s2.getB() - dy[i], 1);

					if (checkAll(tmp1, tmp2) && !map3.containsKey(tmp1)) {
						queue.add(tmp1);
						map3.put(tmp1, s1);
					}
				}
			}
		}
	}

	public static void HillClimb(State begin, State end) {		
		Stack<State> stack = new Stack<>();
		ArrayList<State> nextState = new ArrayList<>();
		
		stack.push(begin);
		map4.put(begin, new State(3, 3, 0));

		while (!stack.isEmpty()) {
			State s1 = stack.pop();
			State s2 = new State(3 - s1.a, 3 - s1.b, s1.c);

			if (s1.equals(end)) {
				System.out.println("OK");
				return;
			}

			int c = s1.getC();
			if (c == 1) {
				for (int i = 0; i < dx.length; ++i) {
					State tmp1 = new State(s1.getA() - dx[i], s1.getB() - dy[i], 0);
					State tmp2 = new State(s2.getA() + dx[i], s2.getB() + dy[i], 0);

					if (checkAll(tmp1, tmp2) && !map4.containsKey(tmp1)) {
						nextState.add(tmp1);
						map4.put(tmp1, s1);
					}
				}
			} else {
				for (int i = 0; i < dx.length; ++i) {
					State tmp1 = new State(s1.getA() + dx[i], s1.getB() + dy[i], 1);
					State tmp2 = new State(s2.getA() - dx[i], s2.getB() - dy[i], 1);

					if (checkAll(tmp1, tmp2) && !map4.containsKey(tmp1)) {
						nextState.add(tmp1);
						map4.put(tmp1, s1);
					}
				}
			}
			Collections.sort(nextState);
			for(State item:nextState) stack.push(item);
		}
	}

	public static void printSolution(State cur, HashMap<State, State> map) {
		if (!map.containsKey(cur))
			return;
		if (map.get(cur) == null)
			return;
		printSolution(map.get(cur), map);
		System.out.println(cur.toString());
	}

	public static void run_bfs(State begin, State end) {
		BFS(begin, end);
		printSolution(end, map1);
	}

	public static void run_dfs(State begin, State end) {
		DFS(begin, end);
		printSolution(end, map2);
	}

	public static void run_bestFirst(State begin, State end) {
		BestFirst(begin, end);
		printSolution(end, map3);
	}

	public static void run_hillClimb(State begin, State end) {
		HillClimb(begin, end);
		printSolution(end, map4);
	}

	public static void main(String[] args) {
		State begin = new State(3, 3, 1);
		State end = new State(0, 0, 0);
		run_bfs(begin, end);
		run_dfs(begin, end);
		run_bestFirst(begin, end);
		run_hillClimb(begin, end);
	}

	public static class State implements Comparable<State> {
		private int a;
		private int b;
		private int c;

		public State() {
		}

		public State(int A, int B, int C) {
			this.a = A;
			this.b = B;
			this.c = C;
		}

		public int getA() {
			return a;
		}

		public void setA(int T) {
			this.a = T;
		}

		public int getB() {
			return b;
		}

		public void setB(int B) {
			this.b = B;
		}

		public int getC() {
			return c;
		}

		public void setC(int C) {
			this.c = C;
		}

		@Override
		public int hashCode() {
			return this.a * 23 + this.b * 31 + this.c * 37;
		}

		public int get() {
			return this.a + this.b;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			} else if (obj == null) {
				return false;
			} else if (obj instanceof State) {
				State st = (State) obj;
				if (st.getC() == c && st.getB() == b && st.getA() == a) {
					return true;
				}
			}
			return false;
		}

		@Override
		public String toString() {
			return "( " + a + " , " + b + " , " + c + " )";
		}

		public int count() {
			return this.a + this.b;
		}

		@Override
		public int compareTo(State state) {
			if (this.a == state.a)
				return this.count() > state.count() ? 1 : 0;
			return this.a > state.a ? 1 : 0;
		}
	}
}