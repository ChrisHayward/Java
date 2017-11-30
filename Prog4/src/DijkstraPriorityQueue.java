//Author:Chris Hayward
//email: christopher.hayward@wsu.edu or stuffedturkey319@gmail.com
//Original code supplied during CS223 Fall 2017
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DijkstraPriorityQueue<P extends Comparable<P>> {

    /* Private types and instance vars go here */
	ArrayList<Vertex<P>> heap;
	ArrayList<Integer> heapIndices;

    public DijkstraPriorityQueue(Comparator<P> priorityComparator,
                                 int numVerts) {
    /* your code goes here */	
    	heap = new ArrayList<Vertex<P>>(numVerts);
    	heapIndices = new ArrayList<Integer>(Collections.nCopies(numVerts, -1));
    }

    public boolean isEmpty() {
    /* your code goes here */
    	return heap.isEmpty();
    }

    public boolean contains(int v) {
	/* your code goes here */
    	return !(heapIndices.get(v).equals(new Integer(-1)) );
    }

    public void insert(int v, P priority) {
	/* your code goes here */
    	heap.add(new Vertex<P>(v, priority));
    	heapIndices.set(v, new Integer(heap.size()-1));
    	while( (heap.get(0).vert != v) && heap.get(heapIndices.get(v)).compareTo(heap.get( (heapIndices.get(v)-1)/2 )) < 0 ) //while inserted vertex is NOT first in heap AND is not correct priority order, sift up
    	{
    		siftUp(v);
    	}
    }
    
    public void siftUp(int v)
    {
    	//swap v with parent, update heap and heapindex
    	Vertex<P> tempV = heap.get(heapIndices.get(v));
    	int parentIndex = (heapIndices.get(v)-1)/2;
    	int vIndex = heapIndices.get(v);
    	Vertex<P> vParent = heap.get( parentIndex );
    	System.out.println("Swapping locations "+parentIndex+ " "+ vIndex+ " in siftUp");
    	heap.set(parentIndex, tempV);
    	heap.set(vIndex, vParent);
    	heapIndices.set(tempV.vert, new Integer(parentIndex));
    	heapIndices.set(vParent.vert, new Integer(vIndex));
    }

    public int deleteMin() {
	//pop root node , replace with last element, update heapindices for moved nodes, and sift down if needed
    	Vertex<P> root = heap.get(0);
    	heap.set(0, heap.get(heap.size()-1));
    	heap.remove(heap.size()-1);
    	//System.out.println("trying to swap during delete, root.vert that is popping"+ root.vert + " heap size "+ heap.size()+ " Vert+Prio+index"+root.vert+" "+root.priority+" "+heapIndices.get(root.vert) );
    	heapIndices.set(root.vert, -1);
    	if (heap.size() > 0)
    	{
    		heapIndices.set(heap.get(0).vert, 0);
    		siftDown(0);
    	}
    	
    	
    	return root.vert;
    }
    
    public void siftDown(int v)//v in this case is the location in heap
    {
    	//System.out.println("In shiftDown, attempting to shift " + heap.get(v).vert +" down");
    	Vertex<P> node = heap.get(v);
    	while( heap.size()-1 >= (heapIndices.get(node.vert)*2+1) && ( node.compareTo(heap.get(heapIndices.get(node.vert)*2+1)) > 0  || //while node has at least 1 child and is larger than that child
    			heap.size()-1 >= ((heapIndices.get(node.vert)+1)*2) && node.compareTo(heap.get((heapIndices.get(node.vert)+1)*2)) > 0 ) )//OR while node has at least 2 children and is larger than the second
    	{
    		int comp = 0; //compare value for deciding to swap first or second child, this value allows less rewritten code later
    		//check if it has a second child
    		if (heap.size()-1 >= ((heapIndices.get(node.vert)+1)*2))
    		{
    			//if it has a second child, compare children, compare child with lower priority to node, then change position if needed
    			comp = heap.get((heapIndices.get(node.vert)+1)*2).compareTo(heap.get((heapIndices.get(node.vert)*2+1)));
    			if (comp < 0)
    			{
    				int x = node.compareTo(heap.get((heapIndices.get(node.vert)+1)*2));
    				if (x > 0) //if second child priority is less than node then swap positions and update as needed
    				{
    					//System.out.println("Success sifting " + heap.get(v).vert);
    					int nodeIndex = heapIndices.get(node.vert);
    					int secondChildIndex = (nodeIndex+1)*2;
    					Vertex<P> child = heap.get(secondChildIndex);
    					//System.out.println("Swapping locations " + nodeIndex + " "+ secondChildIndex);
    					heap.set(nodeIndex, child);
    					heap.set(secondChildIndex, node);
    					heapIndices.set(node.vert, secondChildIndex);
    					heapIndices.set(child.vert, nodeIndex);
    					//System.out.println("Success sifting, new item in spot " + heap.get(v).vert + " Now vert "+ node.vert+ " with priority "+node.priority+ " should be in spot "+ secondChildIndex);
    					v = secondChildIndex;
    				}
    			}
    		}
    		if (comp >= 0 ) //test vs first child
    		{
    			int x = node.compareTo(heap.get((heapIndices.get(node.vert)*2+1)));
    			if (x > 0 )//if first child priority is less than node then swap positions and update as needed
    			{
    				//System.out.println("Success sifting " + heap.get(v).vert);
					int nodeIndex = heapIndices.get(node.vert);
					int firstChildIndex = (nodeIndex*2)+1;
					Vertex<P> child = heap.get(firstChildIndex);
					//System.out.println("Swapping locations " + nodeIndex + " "+ firstChildIndex);
					heap.set(nodeIndex, child);
					heap.set(firstChildIndex, node);
					heapIndices.set(node.vert, firstChildIndex);
					heapIndices.set(child.vert, nodeIndex);
					//System.out.println("Success sifting, new item in spot " + heap.get(v).vert + " Now vert "+ node.vert+ " with priority "+node.priority+ " should be in spot "+ firstChildIndex);
					v = firstChildIndex;
    			}
    		}
    		node = heap.get(v);
    	}
    }
    
    public void printHeap()
    {
    	if (heap.size() > 0)
    	{
    		System.out.println("Printing current heap");
    		System.out.println("Index(heap)     Vertex     Priority     index according to heapindices");
    		for (int i = 0; i < heap.size();i++)
    		{
    			System.out.println(i+"               "+heap.get(i).vert+"           "+heap.get(i).priority+"           "+heapIndices.get(heap.get(i).vert));
    		}
    	}
    }

    public void decreaseKey(int v, P priority) {
	/* your code goes here */
    	heap.set(heapIndices.get(v), new Vertex<P>(v, priority));
    	while( (heap.get(0).vert != v) && heap.get(heapIndices.get(v)).compareTo(heap.get( (heapIndices.get(v)-1)/2 )) < 0 ) //while inserted vertex is NOT first in heap AND is not correct priority order, sift up
    	{
    		siftUp(v);
    	}
    	//System.out.println("In decKey, attempting to shift " + v +" down, it is currently in spot "+ heapIndices.get(v));
    	siftDown(heapIndices.get(v));//to cover edge cases where the provided code was actually raising the priority instead of decreasing
    }

    /*
     * Unit test.
     */
    private static class IntComparator implements Comparator<Integer> {
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }

    public static void main(String[] args) {
        final int n = 20;
        DijkstraPriorityQueue<Integer> Q = 
            new DijkstraPriorityQueue<Integer>(new IntComparator(), n);
        for (int i = 0; i < 20; i++) 
            Q.insert(i, (i-10)*(i-10) + i);
        	//Q.printHeap();
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        Q.printHeap();
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        for (int i = 10; i < 20; i++)
            Q.decreaseKey(i,(i-5)*(i-5));
        Q.printHeap();
        while (!Q.isEmpty()) {
            int v = Q.deleteMin();
            System.out.println(v);
        }
    }
}
