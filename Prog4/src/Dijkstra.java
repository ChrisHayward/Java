import java.util.Comparator;
import java.util.Iterator;

public class Dijkstra {
        
    private static class FloatComparator implements Comparator<Float> {
        public int compare(Float a, Float b) {
            return (int) Math.signum(a - b);
        }
    }

    public static boolean shortestPath(WeightedGraph G, int s, int t, 
                                       int prev[]) {
        int numVerts = G.numVerts();
        float dist[] = new float[numVerts];
        for (int v = 0; v < numVerts; v++) {
            dist[v] = Float.MAX_VALUE;
            prev[v] = -1;
        }
        dist[s] = 0.0F;
        DijkstraPriorityQueue<Float> Q = 
            new DijkstraPriorityQueue<Float>(new FloatComparator(), numVerts);
        for (int v = 0; v < numVerts; v++)
            Q.insert(v, dist[v]);
        while (!Q.isEmpty()) {
            int u = Q.deleteMin();
            if (dist[u] >= Float.MAX_VALUE)
                return false;
            if (u == t)
                return true;
            Iterator<WeightedGraph.VertexWeight> iter = G.adjacents(u);
            while (iter.hasNext()) {
                WeightedGraph.VertexWeight v = iter.next();
                float alt = dist[u] + v.weight;
                if (alt < dist[v.i]) {
                    dist[v.i] = alt;
                    prev[v.i] = u;
                    Q.decreaseKey(v.i, alt);
                }
            }
        }
        return false;
    }
        
}
