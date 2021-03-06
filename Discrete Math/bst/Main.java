import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		InputStream stream = new FileInputStream("bst.in");
		Scanner inf = new Scanner(stream);
		PrintWriter ouf = new PrintWriter(new File("bst.out"));
		while (true) {
			String line = inf.nextLine();
			if (line == null)
				break;
			String[] input = line.split("\\s");
			String request = input[0];
			int key = Integer.parseInt(input[1]);
			switch (request) {
			case "insert":
				BlackRedTree.insert(key);
				break;
			case "exists":
				if (BlackRedTree.exists(key)) {
					ouf.println("true");
				} else {
					ouf.println("false");
				}
				break;
			case "next":
				int result = BlackRedTree.next(key);
				if (result == Integer.MAX_VALUE) {
					ouf.println("none");
				} else {
					ouf.println(result);
				}
				break;
			case "prev":
				result = BlackRedTree.prev(key);
				if (result == Integer.MIN_VALUE) {
					ouf.println("none");
				} else {
					ouf.println(result);
				}
				break;
			case "delete":
				BlackRedTree.delete(key);
				break;
			}
			/*System.out.println(request + ": " + key);
			System.out.println("size: " + BlackRedTree.size());
			BlackRedTree.print();*/

		}
		ouf.close();
	}

	// mega scanner from Zakhar Voit
	static public class Scanner {
		BufferedReader in;
		StringTokenizer tok;

		public Scanner(InputStream in) {
			this.in = new BufferedReader(new InputStreamReader(in));
			tok = new StringTokenizer("");
		}

		public String nextToken() {
			while (!tok.hasMoreTokens()) {
				tok = new StringTokenizer(next());
			}

			return tok.nextToken();
		}

		public String nextLine() throws IOException {
			return in.readLine();
		}

		private String tryReadNextLine() {
			try {
				return in.readLine();
			} catch (IOException e) {
				throw new InputMismatchException();
			}
		}

		public String next() {
			String newLine = tryReadNextLine();
			if (newLine == null)
				throw new InputMismatchException();
			return newLine;
		}

		public int nextInt() {
			return Integer.parseInt(nextToken());
		}

		public long nextLong() {
			return Long.parseLong(nextToken());
		}

		public double nextDouble() {
			return Double.parseDouble(nextToken());
		}

		public boolean EOF() {
			while (!tok.hasMoreTokens()) {
				String newLine = tryReadNextLine();
				if (newLine == null)
					return true;
				tok = new StringTokenizer(newLine);
			}
			return false;
		}

	}

	static public class BlackRedTree {
		private static Node root;

		private enum colors {
			RED, BLACK
		};

		private static class Node {
			private int data;
			private Node left, right, parent;

			private colors color;

			public Node(int newData, Node newParent) {
				data = newData;
				color = colors.RED;
				left = null;
				right = null;
				parent = newParent;
			}

			private void add(int newData) {
				/*
				 * if (left != null) { left.parent = this; } if (right != null)
				 * { right.parent = this; }
				 */
				if (data == newData) {
					return;
				}
				if (data > newData) {
					if (left == null) {
						left = new Node(newData, this);
						left.balance();
					} else {
						left.add(newData);
					}
				} else {
					if (right == null) {
						right = new Node(newData, this);
						right.balance();
					} else {
						right.add(newData);
					}
				}
			}

			private boolean contain(int request) {
				if (data == request) {
					return true;
				}
				if (request < data) {
					if (left == null) {
						return false;
					} else {
						return left.contain(request);
					}
				} else {
					if (right == null) {
						return false;
					} else {
						return right.contain(request);
					}
				}
			}

			private int recSize() {
				int result = 0;
				if (left != null) {
					result += left.recSize();
				}
				if (right != null) {
					result += right.recSize();
				}
				return 1 + result;
			}

			private int upperBound(int request, int curmax) {
				if (data > request) {
					if (left == null) {
						return data;
					}
					return left.upperBound(request, data);
				} else {
					if (right == null) {
						return curmax;
					}
					return right.upperBound(request, curmax);
				}
			}

			private int lowerBound(int request, int curmin) {
				if (data < request) {
					if (right == null) {
						return data;
					}
					return right.lowerBound(request, data);
				} else {
					if (left == null) {
						return curmin;
					}
					return left.lowerBound(request, curmin);
				}
			}

			/*
			 * private void overBalance() { if (left != null) { left.parent =
			 * this; left.overBalance(); } if (right != null) { right.parent =
			 * this; right.overBalance(); } }
			 */

			private void balance() {
				if (color == colors.BLACK) {
					return;
				}
				Node parent, grandpa, uncle;
				parent = this.parent;
				if (parent == null) {
					color = colors.BLACK;
					return;
				}
				if (parent.color == colors.BLACK) {
					return;
				}
				grandpa = parent.parent;
				if (grandpa == null) {
					return;
				}
				if (grandpa.data > parent.data) {
					uncle = grandpa.right;
				} else {
					uncle = grandpa.left;
				}
				if (uncle != null && uncle.color == colors.RED) {
					grandpa.color = colors.RED;
					parent.color = colors.BLACK;
					uncle.color = colors.BLACK;
					grandpa.balance();
				} else {
					if (parent.data < grandpa.data) {
						if (data > parent.data) {
							parent.right = left;
							if (left != null) {
								left.parent = parent;
							}
							left = parent;
							parent.parent = this;
							this.parent = grandpa;
							grandpa.left = this;
						} else {
							grandpa.left = parent.right;
							if (parent.right != null) {
								parent.right.parent = grandpa;
							}
							if (grandpa.parent != null) {
								if (grandpa.data < grandpa.parent.data) {
									grandpa.parent.left = parent;
								} else {
									grandpa.parent.right = parent;
								}
							}
							parent.parent = grandpa.parent;
							grandpa.parent = parent;
							parent.right = grandpa;
							grandpa.color = colors.RED;
							parent.color = colors.BLACK;
						}
					} else {
						if (data < parent.data) {
							parent.left = right;
							if (right != null) {
								right.parent = parent;
							}
							right = parent;
							parent.parent = this;
							this.parent = grandpa;
							grandpa.right = this;
						} else {
							grandpa.right = parent.left;
							if (parent.left != null) {
								parent.left.parent = grandpa;
							}
							if (grandpa.parent != null) {
								if (grandpa.data < grandpa.parent.data) {
									grandpa.parent.left = parent;
								} else {
									grandpa.parent.right = parent;
								}
							}
							parent.parent = grandpa.parent;
							grandpa.parent = parent;
							parent.left = grandpa;
							grandpa.color = colors.RED;
							parent.color = colors.BLACK;
						}
					}
					parent.balance();
				}
			}

			private void delete(int key) {
				if (data == key) {
					if (left == null && right == null) {
						if (data > parent.data) {
							parent.right = null;
						} else {
							parent.left = null;
						}
						return;
					}
					if (left != null) {
						Node bench = left;
						while (bench.right != null) {
							bench = bench.right;
						}
						if (bench.data > bench.parent.data) {
							if (bench.left == null) {
								bench.parent.right = null;
							} else {
								bench.parent.right = bench.left;
								bench.left.parent = bench.parent;
							}
						} else {
							if (bench.left == null) {
								bench.parent.left = null;
							} else {
								bench.parent.left = bench.left;
								bench.left.parent = bench.parent;
							}
						}
						data = bench.data;
					} else {
						Node bench = right;
						while (bench.left != null) {
							bench = bench.left;
						}
						if (bench.data > bench.parent.data) {
							if (bench.right == null) {
								bench.parent.right = null;
							} else {
								bench.parent.right = bench.right;
								bench.right.parent = bench.parent;
							}
						} else {
							if (bench.right == null) {
								bench.parent.left = null;
							} else {
								bench.parent.left = bench.right;
								bench.right.parent = bench.parent;
							}
						}
						data = bench.data;
					}
					return;
				}
				if (data > key) {
					if (left != null) {
						left.delete(key);
					}
				} else {
					if (right != null) {
						right.delete(key);
					}
				}
			}
		}

		public static void print() {
			write(" ", root);
		}

		public static int size() {
			if (root == null) {
				return 0;
			}
			return root.recSize();
		}

		private static void write(String prefix, Node tree) {
			if (tree != null) {
				write(prefix + " ", tree.left);
				System.out
						.println(prefix
								+ "data: "
								+ tree.data
								+ " color: "
								+ tree.color
								+ " parent: "
								+ ((tree.parent == null) ? "null"
										: tree.parent.data)
								+ " parent.left: "
								+ ((tree.parent != null && tree.parent.left != null) ? tree.parent.left.data
										: "null")
								+ " parent.right: "
								+ ((tree.parent != null && tree.parent.right != null) ? tree.parent.right.data
										: "null"));
				write(prefix + " ", tree.right);
			}
		}

		public static void delete(int key) {
			if (root == null) {
				return;
			}
			if (root.data == key && root.left == null && root.right == null) {
				root = null;
				return;
			}
			root.delete(key);
			// root.overBalance();
		}

		public static void insert(int newData) {
			if (root == null) {
				root = new Node(newData, null);
				root.color = colors.BLACK;
			} else {
				root.add(newData);
				while (root.parent != null)
					root = root.parent;
				// root.overBalance();
			}
		}

		public static boolean exists(int request) {
			if (root == null) {
				return false;
			}
			return root.contain(request);
		}

		public static int next(int request) {
			if (root == null) {
				return Integer.MAX_VALUE;
			}
			return root.upperBound(request, Integer.MAX_VALUE);
		}

		public static int prev(int request) {
			if (root == null) {
				return Integer.MIN_VALUE;
			}
			return root.lowerBound(request, Integer.MIN_VALUE);
		}

	}
}
