import java.util.Iterator;

public interface WeightedGraph {
    public class VertexWeight {
        public final int i;
        public final float weight;
        public VertexWeight(int v, float w) {i=v; weight=w;}
    }
    int numVerts();  // verts are numbered 0,1,..,|V|-1
    Iterator<VertexWeight> adjacents(int v);
}