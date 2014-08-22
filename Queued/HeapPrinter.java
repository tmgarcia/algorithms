import java.util.ArrayList;
import java.util.Collections;


public class HeapPrinter
{
	public static <T extends Comparable<?>> void printHeapAsArray(HeapBasedPriorityQueue<T> heap) 
	{
		ArrayList<T> heapArray = heap.getHeap();
		int numElements = heap.getNumElements();
		if(numElements>0)
		{
			System.out.print("[" + heapArray.get(0));
			for(int i = 1; i < numElements; i++)
			{
				System.out.print("," + heapArray.get(i));
			}
			System.out.println("]");
		}
		else
		{
			System.out.println("Heap Empty");
		}
	}
	public static <T extends Comparable<?>> void printHeapAsTree(HeapBasedPriorityQueue<T> heap) 
	{
		ArrayList<T> heapArray = heap.getHeap();
		int numElements = heap.getNumElements();
		if(numElements>0)
		{
			Node root = new Node(heapArray.get(0));
			buildTree(heapArray, numElements, 0, root);
			BinaryTreePrinter.printNode(root);
		}
		else
		{
			System.out.println("Heap Empty");
		}
	}
	private static <T extends Comparable<?>> void buildTree(ArrayList<T> heapArray, int numElements, int currentIndex, Node currentNode)
	{
		if((currentIndex >= numElements/2) )//is leaf
		{
			return;
		}
		if(currentIndex*2+1 < numElements)//valid left child
		{
			Node lc = new Node(heapArray.get(currentIndex*2+1));
			currentNode.left = lc;
			buildTree(heapArray, numElements, currentIndex*2+1, lc);

			if(currentIndex*2+2 < numElements)//valid right child
			{
				Node rc = new Node(heapArray.get(currentIndex*2+2));
				currentNode.right = rc;
				buildTree(heapArray, numElements, currentIndex*2+2, rc);
			}
		}

	}
}
