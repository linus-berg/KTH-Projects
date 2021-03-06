import edu.princeton.cs.algs4.*;

public class Graph {
  private ST<String, SET<String>> st;

  private int E;

  public Graph() {
    st = new ST<String, SET<String>>();
  }

  public int V() {
    return st.size();
  }

  public int E() {
    return E;
  }

  private void validateVertex(String v) {
    if (!hasVertex(v))
      throw new IllegalArgumentException(v + " is not a vertex");
  }

  public int degree(String v) {
    validateVertex(v);
    return st.get(v).size();
  }

  public void addEdge(String v, String w) {
    if (!hasVertex(v))
      addVertex(v);
    if (!hasVertex(w))
      addVertex(w);
    if (!hasEdge(v, w))
      E++;
    st.get(v).add(w);
    st.get(w).add(v);
  }

  public void addVertex(String v) {
    if (!hasVertex(v))
      st.put(v, new SET<String>());
  }

  public Iterable<String> vertices() {
    return st.keys();
  }

  public Iterable<String> adj(String v) {
    validateVertex(v);
    return st.get(v);
  }

  public boolean hasVertex(String v) {
    return st.contains(v);
  }

  public boolean hasEdge(String v, String w) {
    validateVertex(v);
    validateVertex(w);
    return st.get(v).contains(w);
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    for (String v : st) {
      s.append(v + ": ");
      for (String w : st.get(v)) {
        s.append(w + " ");
      }
      s.append('\n');
    }
    return s.toString();
  }

  public static void main(String[] args) {
    Graph graph = new Graph();
    while (!StdIn.isEmpty()) {
      String v = StdIn.readString();
      String w = StdIn.readString();
      graph.addEdge(v, w);
    }

    for (String v : graph.vertices()) {
      StdOut.print(v + ": ");
      for (String w : graph.adj(v)) {
        StdOut.print(w + " ");
      }
      StdOut.println();
    }
  }
}
