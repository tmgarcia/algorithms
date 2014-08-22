import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Controller
{
	public static void testAVL(ArrayList<Integer> ints)
	{
		System.out.println("/*---------------------------- AVL Test ----------------------------*/\n");
		QuickSorter sorter = new QuickSorter();
		AVLBasedPriorityQueue avl = new AVLBasedPriorityQueue<Integer>();
		String str = "";
		System.out.println("/*------------- Offer Tests -------------*/\n");
		for(int i = 0; i < ints.size(); i++)
		{
			System.out.println("offer " + ints.get(i));
			avl.offer(ints.get(i));
			BinaryTreePrinter.printNode(avl.getRoot());
		}
		sorter.sort(ints);
		Collections.reverse(ints);
		System.out.println("/*------------- Peek and Poll Tests -------------*/\n");
		for(int i = 0; i < ints.size(); i++)
		{
			System.out.println("expected Max " + ints.get(i) + " ");
			System.out.println("peek result: " + avl.peek());
			System.out.println("poll result: " + avl.poll());
			BinaryTreePrinter.printNode(avl.getRoot());
		}
	}
	public static void testHeap(ArrayList<Integer>ints)
	{
		System.out.println("/*---------------------------- Heap Test ----------------------------*/\n");
		QuickSorter sorter = new QuickSorter();
		HeapBasedPriorityQueue heap = new HeapBasedPriorityQueue<Integer>();
		String str = "";
		System.out.println("/*------------- Offer Tests -------------*/\n");
		for(int i = 0; i < ints.size(); i++)
		{
			System.out.println("offer " + ints.get(i));
			heap.offer(ints.get(i));
			HeapPrinter.printHeapAsArray(heap);
			HeapPrinter.printHeapAsTree(heap);
			//BinaryTreePrinter.printNode(avl.getRoot());
		}
		sorter.sort(ints);
		Collections.reverse(ints);
		System.out.println("/*------------- Peek and Poll Tests -------------*/\n");
		for(int i = 0; i < ints.size(); i++)
		{
			System.out.println("expected max " + ints.get(i) + " ");
			System.out.println("peek result: " + heap.peek());
			System.out.println("poll result: " + heap.poll());
			HeapPrinter.printHeapAsArray(heap);
			HeapPrinter.printHeapAsTree(heap);
		}
	}
	public static void main(String[] args)
	{
		Integer[] ints = {5,6,8,3,2,4,7};
		
		ArrayList<Integer> intsList1 = new ArrayList<Integer>(Arrays.asList(ints));
		testAVL(intsList1);
		
		ArrayList<Integer> intsList2 = new ArrayList<Integer>(Arrays.asList(ints));
		//testHeap(intsList2);
	}
}