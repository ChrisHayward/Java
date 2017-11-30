
public class Vertex<P extends Comparable<P>> {
	public P priority;
	public int vert;
	public Vertex(int i, P p)
	{
		vert = i;
		priority = p;
	}
	public int compareTo(Vertex toTest) {
		//System.out.println("Attempting to compare "+priority+" to "+ toTest.priority + " Result "+ priority.compareTo((P) toTest.priority));
		return priority.compareTo((P) toTest.priority);
	}	
}
