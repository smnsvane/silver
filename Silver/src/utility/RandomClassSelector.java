package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomClassSelector<T>
{
	private Random rnd = new Random();
	private int occurenceMax = 0;
	private List<Element<T>> elements = new ArrayList<Element<T>>();
	
	public void addElement(int occurance, T o)
	{
		Element<T> e = new Element<T>(occurance, o);
		elements.add(e);
		occurenceMax += occurance;
	}
	
	public T getRandomElement()
	{
		int occ = rnd.nextInt(occurenceMax) + 1;
		int index = 0;
		while (occ > elements.get(index).getOccurance())
		{
			occ -= elements.get(index).getOccurance();
			index++;
		}
		return (T) elements.get(index).getO();
	}
	
	public void clearElements()
	{
		elements.clear(); occurenceMax = 0;
	}
	
	class Element<E>
	{
		private int occurance;
		private E o;
		
		public Element(int occurance, E o)
		{
			this.occurance = occurance; this.o = o;
		}
		
		public int getOccurance() { return occurance; }
		public E getO() { return o; }
	}
}
