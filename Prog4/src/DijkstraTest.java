import java.util.Iterator;
import java.util.LinkedList;

public class DijkstraTest {

    private static class ArrayIterator<T> implements Iterator<T> {
        private T a[];
        private int i;
        public ArrayIterator(T a[]) {
            this.a = a;
            i = 0;
        }
        public boolean hasNext() {return i < a.length;}
        public T next() {return a[i++];}
        public void remove() {
            throw new UnsupportedOperationException("remove verboten!");
        }
    }

    private static class WGraph implements WeightedGraph {
        static WeightedGraph.VertexWeight[][] adjacencyLists = {
            // 0
            {new WeightedGraph.VertexWeight(1,10),
             new WeightedGraph.VertexWeight(5,7)},

            // 1
            {new WeightedGraph.VertexWeight(0,10),
             new WeightedGraph.VertexWeight(6,3),
             new WeightedGraph.VertexWeight(2,1)},

            // 2
            {new WeightedGraph.VertexWeight(1,1),
             new WeightedGraph.VertexWeight(6,3),
             new WeightedGraph.VertexWeight(7,0)},

            // 3
            {new WeightedGraph.VertexWeight(2,7),
             new WeightedGraph.VertexWeight(8,4),
             new WeightedGraph.VertexWeight(4,5)},

            // 4
            {new WeightedGraph.VertexWeight(3,5),
             new WeightedGraph.VertexWeight(9,1),
             new WeightedGraph.VertexWeight(21,1)},

            // 5
            {new WeightedGraph.VertexWeight(0,7),
             new WeightedGraph.VertexWeight(6,5),
             new WeightedGraph.VertexWeight(10,7)},

            // 6
            {new WeightedGraph.VertexWeight(1,3),
             new WeightedGraph.VertexWeight(7,10),
             new WeightedGraph.VertexWeight(11,0),
             new WeightedGraph.VertexWeight(5,5)},

            // 7
            {new WeightedGraph.VertexWeight(2,0),
             new WeightedGraph.VertexWeight(8,1),
             new WeightedGraph.VertexWeight(12,6),
             new WeightedGraph.VertexWeight(6,10)},

            // 8
            {new WeightedGraph.VertexWeight(3,4),
             new WeightedGraph.VertexWeight(9,8),
             new WeightedGraph.VertexWeight(13,3),
             new WeightedGraph.VertexWeight(7,1)},

            // 9
            {new WeightedGraph.VertexWeight(4,1),
             new WeightedGraph.VertexWeight(21,4),
             new WeightedGraph.VertexWeight(14,1),
             new WeightedGraph.VertexWeight(8,8)},

            // 10
            {new WeightedGraph.VertexWeight(5,7),
             new WeightedGraph.VertexWeight(11,1),
             new WeightedGraph.VertexWeight(15,1)},

            // 11
            {new WeightedGraph.VertexWeight(6,0),
             new WeightedGraph.VertexWeight(12,6),
             new WeightedGraph.VertexWeight(16,8),
             new WeightedGraph.VertexWeight(10,1)},

            // 12
            {new WeightedGraph.VertexWeight(7,6),
             new WeightedGraph.VertexWeight(13,10),
             new WeightedGraph.VertexWeight(17,0),
             new WeightedGraph.VertexWeight(11,6)},

            // 13
            {new WeightedGraph.VertexWeight(8,3),
             new WeightedGraph.VertexWeight(14,10),
             new WeightedGraph.VertexWeight(18,3),
             new WeightedGraph.VertexWeight(12,10)},

            // 14
            {new WeightedGraph.VertexWeight(9,1),
             new WeightedGraph.VertexWeight(21,4),
             new WeightedGraph.VertexWeight(19,4),
             new WeightedGraph.VertexWeight(13,10)},

            // 15
            {new WeightedGraph.VertexWeight(10,1),
             new WeightedGraph.VertexWeight(16,4)},

            // 16
            {new WeightedGraph.VertexWeight(11,8),
             new WeightedGraph.VertexWeight(17,10),
             new WeightedGraph.VertexWeight(15,4)},

            // 17
            {new WeightedGraph.VertexWeight(12,0),
             new WeightedGraph.VertexWeight(18,0),
             new WeightedGraph.VertexWeight(16,10)},

            // 18
            {new WeightedGraph.VertexWeight(13,3),
             new WeightedGraph.VertexWeight(19,10),
             new WeightedGraph.VertexWeight(17,0)},

            // 19
            {new WeightedGraph.VertexWeight(14,4),
             new WeightedGraph.VertexWeight(21,3),
             new WeightedGraph.VertexWeight(18,10)},

            // 20
            {new WeightedGraph.VertexWeight(0,0),
             new WeightedGraph.VertexWeight(5,7),
             new WeightedGraph.VertexWeight(10,3),
             new WeightedGraph.VertexWeight(15,1)},

            // 21
            {}
        };

        public int numVerts() {return adjacencyLists.length;}
        public Iterator<WeightedGraph.VertexWeight> adjacents(int v) {
            return new 
                ArrayIterator<WeightedGraph.VertexWeight>(adjacencyLists[v]);
        }
    }

    public static void main(String[] args) {
        WGraph G = new WGraph();
        int prev[] = new int[G.numVerts()];
        final int s = 20;
        final int t = 21;
        boolean found = Dijkstra.shortestPath(G, s, t, prev);
        if (!found) {
            System.err.println("t not found!");
        }
        LinkedList<Integer> path = new LinkedList<Integer>();
        int v = t;
        while (v != -1) {
            path.addFirst(v);
            v = prev[v];
        }
        for (Integer I : path)
            System.out.print(I + ", ");
        System.out.println();
    }

}
