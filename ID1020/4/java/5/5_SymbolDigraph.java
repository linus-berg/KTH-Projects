import edu.princeton.cs.algs4.*;

public class SymbolDigraph {
  private ST<String, Integer> st;
  private String[] keys;
  private Digraph graph;

  public SymbolDigraph(String filename, String delimiter) {
    st = new ST<String, Integer>();

    In in = new In(filename);

    while (!in.isEmpty()) {
      String[] a = in.readLine().split(delimiter);
      String v = a[0];
      String w = a[1];
      if (!st.contains(v))
        st.put(v, st.size());
      if (!st.contains(w))
        st.put(w, st.size());
    }

    keys = new String[st.size()];
    for (String name : st.keys()) {
      keys[st.get(name)] = name;
    }

    graph = new Digraph(st.size());
    in = new In(filename);
    while (in.hasNextLine()) {
      String[] a = in.readLine().split(delimiter);
      int v = st.get(a[0]);
      for (int i = 1; i < a.length; i++) {
        int w = st.get(a[i]);
        graph.addEdge(v, w);
      }
    }
  }

  public boolean contains(String s) {
    return st.contains(s);
  }

  public int indexOf(String s) {
    return st.get(s);
  }

  public String nameOf(int v) {
    validateVertex(v);
    return keys[v];
  }

  public Digraph digraph() {
    return graph;
  }

  private void validateVertex(int v) {
    int V = graph.V();
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
  }

  public static void main(String[] args) {
    String filename = args[0];
    String delimiter = args[1];

    SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
    Digraph graph = sg.digraph();

    int start = sg.indexOf("AL");
    int pToFind = sg.indexOf("VA");
    DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(graph, start);
    
    for (int i = 0; i < graph.V(); i++) {
      if (dfs.hasPathTo(i)) {
        for (int x : dfs.pathTo(i)) {
          if (x == start) {
            StdOut.print(sg.nameOf(x));
          } else {
            StdOut.print("-" + sg.nameOf(x));
          }
        }
        StdOut.println();
      } else {
       // StdOut.printf("%s to %s:  not connected\n", sg.nameOf(start), sg.nameOf(i));
      }
    }
  }
}
