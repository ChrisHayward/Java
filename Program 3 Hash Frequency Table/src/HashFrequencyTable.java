//Author: Chris Hayward
//Email: chris.hayward@wsu.edu 		stuffedturkey319@gmail.com
import java.io.PrintStream;
import java.util.*;

public class HashFrequencyTable<K> implements FrequencyTable<K>, Iterable<K>{

	
	int sz2 = 0;
	ArrayList<Pair> table;
	float filledSlots = 0;
	float maxLFactor = 0;
	
	public HashFrequencyTable(int initialCapacity, float maxLoadFactor)
	{
		int sz = nextPowerOfTwo(initialCapacity);
		table = new ArrayList<Pair>(sz);
		for (int i = 0; i < sz; i++)
		{
			table.add(null);
		}
		maxLFactor = maxLoadFactor;
		sz2 = sz;

	}
	
	public Pair probe(K key, boolean x)
	//Method for the heavy lifting of count and click 
	//x is used to determine if the table is updated when accessed
	{
		int i = 0;
		int h = key.hashCode() & (sz2- 1);
		Pair y = table.get(h);
		int k = h;
		if (x)
		{
			while(y!=null && !(key.equals(y.key)))					//if a Pair exists and is not equal to the desired Pair, start probing
			{
				k = ((h + i*(i + 1)/2) & (sz2 - 1));
				y = table.get(k);
				i++;
			}
			if (y!=null)											//Probe success! we have found our desired Pair location
			{
				y.count = y.count+1;
				table.set(k, y);
			}
			else													//Key not found and we got to an empty slot, so fill this one
			{
				Pair temp = new Pair(key);
				table.set(k, temp);
				filledSlots++;
				loadFactor();
			}
		}
		else
		{
			if (y!=null)
			{
				while(!(key.equals(y.key)))
				{
					h = key.hashCode() & (sz2 - 1);
					k = ((h + i*(i + 1)/2) & (sz2 - 1));
					y = table.get(k);
					i++;
				}
				return y;
			}
			else
			{
				return y;
			}
		}
		return y;	
	}
	
	private void loadFactor() //Tests load factor, if over max, builds a bigger table and rehashes
	{
		float lfactor = filledSlots / (float)sz2;
		if ( lfactor > maxLFactor )
		{
			ArrayList<Pair> tempTable = new ArrayList<>(table);
			sz2 = nextPowerOfTwo(sz2+1);
			table = new ArrayList<Pair>(sz2);
			for (int i = 0; i < sz2; i++)
				table.add(null);
			
			for(Pair p : tempTable)
			{
				if (p!=null)
				{
					int i = 0;
					int h = (p.key.hashCode() & (sz2 - 1));
					int k = h;
					Pair p2 = table.get(k);
					while(p2!=null && !(p2.key.equals(p.key)))
					{
						k = ((h + i*(i + 1)/2) & (sz2 - 1));
						i++;
						p2 = table.get(k);
					}
					table.set(k, p);
				}
				
				
			}
		}
	}

	public void click(K key) {
		probe(key, true);
	}

	public int count(K key) {
		Pair x = probe(key, false);
		if (x!=null)
			return x.count;
		return 0;
	}
	
	public void dump(PrintStream str)
	{
		for (int i = 0; i < table.size(); i++)
		{
			Pair p = table.get(i);
			if (p==null)
			{
				System.out.println(i + ": " + p);
			}
			else
			{
				System.out.println(i + ": key='" + p.key + "', count=" + p.count);
			}
		}
	}
	
	private static int nextPowerOfTwo(int n) {
		int e = 1;
		while ((1 << e) < n)
		e++;
		return 1 << e;
		}
	
	public static void main(String[] args) {
		String hamlet =
		"To be or not to be that is the question " +
		"Whether 'tis nobler in the mind to suffer " +
		"The slings and arrows of outrageous fortune ";
		String words[] = hamlet.split("\\s+");
		HashFrequencyTable<String> table = new HashFrequencyTable<String>(10, 0.95F);
		for (int i = 0; i < words.length; i++)
		if (words[i].length() > 0)
		table.click(words[i]);
		table.dump(System.out);
		}

	
	
	public Iterator<K> iterator() {
			return new TableIterator();
		}

	
	private class TableIterator implements Iterator<K> {
		private int i;
		public TableIterator() {i = 0;}
		public boolean hasNext() {
		// look for the next non-null entry in the ArrayList
		// if there is one, return true, otherwise return false
			while(i < sz2 - 1 && table.get(i)==null)
			{
				i++;
			}
			if (table.get(i)==null)
			{
				return false;
			}
			return true;
		}
		public K next() {
		// return the value at location i, being sure to move on to the next element
			Pair t = table.get(i);
			i++;
			return (K) t;
		}
		public void remove() {
		throw new UnsupportedOperationException("Remove not supported");
		}
	}
}
