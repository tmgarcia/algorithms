import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.neumont.util.List;


public class ArrayList<T> implements List<T>, Iterator<T>
{	
	private int size;
	private int currentNumElements;
	private T[] array;
	private int currentElementIndex;
	
	public ArrayList()
	{
		size = 10;
		currentNumElements = 0;
		array = (T[])new Object[size];
	}
	public ArrayList(int initialCapacity)
	{
		size = initialCapacity;
		currentNumElements = 0;
		array = (T[])new Object[size];
	}
	
	@Override
	public Iterator<T> iterator()
	{
		currentElementIndex = -1;
		return this;
	}

	@Override
	public boolean add(T t)
	{
		boolean addSuccessful = true;
		if(currentNumElements == size)
		{
			T[] arrayDoubled = (T[])new Object[size*2];
			for(int i = 0; i < currentNumElements; i++)
			{
				arrayDoubled[i] = array[i];
			}
			array = arrayDoubled;
			array[currentNumElements] = t;
			size *= 2;
			currentNumElements++;
		}
		else
		{
			array[currentNumElements] = t;
			currentNumElements++;
		}
		return addSuccessful;
	}

	@Override
	public T get(int index)
	{
		if(index>= currentNumElements)
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		return array[index];
	}

	@Override
	public boolean remove(T t)
	{
		boolean removedItem = true;
		int index = getIndexOfElement(t);
		if(index == -1)
		{
			throw new NoSuchElementException("The element being removed does not exist.");
		}
		else
		{
			//System.out.println("REMOVED " + index);
			removeFromIndex(index);
		}
		return removedItem;
	}
	
	private int getIndexOfElement(T t)
	{
		int index = -1;
		boolean foundElement = false;
		for(int i = 0; i < currentNumElements && !foundElement; i++)
		{
			if(array[i] == t)
			{
				foundElement = true;
				index = i;
			}
		}
		return index;
	}

	@Override
	public int size()
	{
		return currentNumElements;
	}

	@Override
	public boolean hasNext()
	{
		return (currentElementIndex<currentNumElements-1);
	}

	@Override
	public T next()
	{
		currentElementIndex++;
		T ret = array[currentElementIndex];
		return ret;
	}

	@Override
	public void remove()
	{
		//IllegalStateException
		if(currentElementIndex == -1)
		{
			throw new IllegalStateException("next() has not yet been called on this iterator.");
		}
		removeFromIndex(currentElementIndex);
		//currentElementIndex--;
	}
	private void removeFromIndex(int index)
	{
		for(int i = index; i<currentNumElements; i++)
		{
			if(i == currentNumElements-1)
			{
				array[i] = null;
			}
			else
			{
				array[i] = array[i+1];
			}
		}
		currentNumElements--;
	}
	
}
