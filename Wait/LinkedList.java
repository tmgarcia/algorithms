import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.neumont.util.Queue;


public class LinkedList<T> implements Queue<T>, Iterator<T>
{
	private class Node<T>
	{
		private T data;
		private Node next;
		Node(T nodeData, Node nextNode)
		{
			data = nodeData;
			next = nextNode;
		}
		public T getData()
		{
			return data;
		}
		public void setData(T newData)
		{
			data = newData;
		}
		public Node getNext()
		{
			return next;
		}
		public void setNext(Node newNext)
		{
			next = newNext;
		}
	}
	private Node head;
	private Node tail;
	private Node currentNode;
	
	int numNodes;
	
	public LinkedList()
	{
		numNodes = 0;
		head = null;
		tail = null;
		currentNode = null;
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return this;
	}
	
	public T next()
	{
		T ret = (T) currentNode.getNext().getData();
		currentNode = currentNode.getNext();
		return ret;
	}
	
	public boolean hasNext()
	{
		return (currentNode != tail);
	}

	@Override
	public T poll()
	{
		T ret = null;
		if(numNodes>0)
		{
			Node node = head;
			head = head.getNext();
			ret = (T) node.getData();
			numNodes--;
		}
		//add test poll
		//add some times, remove some items
		return ret;
	}

	@Override
	public boolean offer(T t)
	{
		if(t == null)
		{
			throw new IllegalArgumentException("Data cannot be null.");
		}
		boolean changeSuccessful = true;
		if(head == null)
		{
			head = new Node<T>(t,null);
			tail = head;
			currentNode = head;
		}
		else
		{
			tail.setNext(new Node<T>(t,null));
			tail = tail.getNext();
		}
		
		numNodes++;
		return changeSuccessful;
	}

	@Override
	public T peek()
	{
		T ret = null;
		if(head != null)
		{
			ret = (T)head.getData();
		}
		return ret;
	}

	@Override
	public void remove()
	{
		if(numNodes>0)
		{
			head = head.getNext();
		}
		else
		{
			throw new NoSuchElementException("Cannot remove an element from an empty linked list.");
		}
	}
	
}
