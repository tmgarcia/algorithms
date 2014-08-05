import java.util.Iterator;
import java.util.Random;

import edu.neumont.util.Client;


public class Controller
{
	public static void main(String[]args)
	{
		Random gen = new Random();
//		GroceryStore b = new GroceryStore(3);
//		Client[] clients = new Client[10];
//		int s = 5;
//		for(int i = 0; i < 10; i++)
//		{
//			s = gen.nextInt(10);
//			Client c = new Client(s);
//			b.addClient(c);
//			clients[i] = c;
//		}
//		b.printAllClients();
//		b.advanceMinute();
//		System.out.println("___________");
//		b.printAllClients();
//		System.out.println("Average " + b.getAverageClientWaitTime());
//		b.advanceMinute();
//		System.out.println("___________");
//		b.printAllClients();
//		System.out.println("Average " + b.getAverageClientWaitTime());
//		b.advanceMinute();
//		System.out.println("___________");
//		b.printAllClients();
//		System.out.println("Average " + b.getAverageClientWaitTime());
//		b.advanceMinute();
//		System.out.println("___________");
//		b.printAllClients();
//		System.out.println("Average " + b.getAverageClientWaitTime());
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.offer(5);
		ll.offer(4);
		ll.offer(6);
		ll.offer(9);
		ll.offer(12);
		ll.offer(36);
		System.out.println("size " + ll.numNodes);
		Iterator<Integer> it = ll.iterator();
		while(ll.hasNext())
		{
			System.out.println(ll.next());
		}
		
		ll.poll();
		System.out.println("poll()");
		System.out.println("size " + ll.numNodes);
		ll.poll();
		System.out.println("poll()");
		System.out.println("size " + ll.numNodes);
		ll.poll();
		System.out.println("poll()");
		System.out.println("size " + ll.numNodes);
		ll.poll();
		System.out.println("poll()");
		System.out.println("size " + ll.numNodes);
		ll.poll();
		System.out.println("poll()");
		System.out.println("size " + ll.numNodes);
	}
}
