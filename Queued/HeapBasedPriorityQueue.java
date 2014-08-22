import java.util.ArrayList;
import java.util.Collections;

//MIN HEAP
public class HeapBasedPriorityQueue<T extends Comparable> 
{
	private ArrayList<T> heap;
	private int numElements;
	
	public ArrayList<T> getHeap()
	{
		return heap;
	}
	public int getNumElements()
	{
		return numElements;
	}
	
	public HeapBasedPriorityQueue()
	{
		heap = new ArrayList<T>();
		numElements = 0;
	}
	
	public boolean offer(T data)
	{
		boolean success = true;
		placeElement(data);
		return success;
	}

	public T poll()
	{
		if(heap.isEmpty())
		{
			return null;
		}
		T ret = heap.get(0);
		removeMax();
		return ret;
	}
	
	public T peek()
	{
		if(heap.isEmpty())
		{
			return null;
		}
		return heap.get(0);
	}
	
	private void removeMax()
	{
		heap.set(0, heap.get(numElements-1));
		heap.remove(numElements-1);
		numElements--;
		if(numElements>0)
		{
			siftDown(0);
		}
	}
	private void siftDown(int index)
	{
		while(!elementIsLeaf(index))
		{
			int child = getLeftChildIndex(index);//child is left child
			if(child < numElements-1 && heap.get(child).compareTo(heap.get(child+1)) < 0)//left child is valid index and left child is less than right child
			{
				child++; //child is now right child
			}
			if(heap.get(index).compareTo(heap.get(child)) >= 0)//if current is greater than child
			{
				return; // element is greater than its child, is in the right place
			}
			Collections.swap(heap, index, child);//element was less than child, swap it with child
			index = child;//repeat with new index
		}
	}
	private void siftUp(int index)
	{
		//sift the new value up until its parent is a greater value (is in the correct place)
				//while it's value is greater than its parent, keep sifting it up
		while(index>0 && heap.get(index).compareTo(heap.get(getParentIndex(index))) > 0)
		{
			Collections.swap(heap, index, getParentIndex(index));
			index = getParentIndex(index);
		}
	}
	private void placeElement(T t)
	{
		int currentIndex = numElements;
		heap.add(t);
		siftUp(currentIndex);
		numElements++;
	}
	private boolean elementIsLeaf(int index)
	{
		return (index >= numElements/2) && (index<numElements);
	}
	private int getParentIndex(int childIndex)
	{
		return (int)(childIndex/2);
	}
	private int getLeftChildIndex(int parentIndex)
	{
		return parentIndex*2+1;
	}
	private int getRightChildIndex(int parentIndex)
	{
		return parentIndex*2+2;
	}
}
