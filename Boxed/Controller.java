import java.util.List;


public class Controller
{
	public static void testTraversal()
	{
		Graph g = new Graph(6);
//		g.addEdge(0, 2, 1);
//		g.addEdge(0, 4, 1);
//		g.addEdge(1, 2, 1);
//		g.addEdge(1, 5, 1);
//		g.addEdge(2, 0, 1);
//		g.addEdge(2, 1, 1);
//		g.addEdge(2, 3, 1);
//		g.addEdge(2, 5, 1);
//		g.addEdge(3, 2, 1);
//		g.addEdge(3, 5, 1);
//		g.addEdge(4, 0, 1);
//		g.addEdge(4, 5, 1);
//		g.addEdge(5, 1, 1);
//		g.addEdge(5, 2, 1);
//		g.addEdge(5, 3, 1);
//		g.addEdge(5, 4, 1);
		
		g.addEdge(0,1,1);
		g.addEdge(0,2,1);
		g.addEdge(1, 0, 1);
		g.addEdge(1,5,1);
		g.addEdge(2, 0, 1);
		g.addEdge(3, 4, 1);
		g.addEdge(4,3,1);
		g.addEdge(5,1,1);
		
		g.printGraph();
		System.out.println();
		
		BfsGraphTraversal bfs = new BfsGraphTraversal();
		List<List<Integer>> bf = bfs.traverse(g);
		System.out.println("Breadth First");
		for(int i = 0; i < bf.size(); i++)
		{
			System.out.println("Component " + i);
			for(int j = 0; j < bf.get(i).size(); j++)
			{
				System.out.print(bf.get(i).get(j) + ",");
			}
			System.out.println();
		}
		
		System.out.println();
		
		DfsGraphTraversal dfs = new DfsGraphTraversal();
		List<List<Integer>> df = dfs.traverse(g);
		System.out.println("Depth First");
		for(int i = 0; i < df.size(); i++)
		{
			System.out.println("Component " + i);
			for(int j = 0; j < df.get(i).size(); j++)
			{
				System.out.print(df.get(i).get(j) + ",");
			}
			System.out.println();
		}
	}
	public static void testOwner()
	{
		DotsAndBoxes d = new DotsAndBoxes(5, 5);
		d.drawLine(1, 1, 0, 2, 0);
		d.drawLine(1, 2, 0, 3, 0);
		d.drawLine(1, 1, 1, 2, 1);
		d.drawLine(1, 2, 1, 3, 1);
		d.drawLine(1, 1, 0, 1, 1);
		d.drawLine(1, 3, 0, 3, 1);
		
		d.drawLine(1, 0, 2, 0, 3);
		d.drawLine(1, 0, 3, 0, 4);
		d.drawLine(1, 1, 2, 1, 3);
		d.drawLine(1, 1, 3, 1, 4);
		d.drawLine(1, 0, 2, 1, 2);
		d.drawLine(1, 0, 4, 1, 4);

		d.drawLine(1, 2, 0, 2, 1);
		//Assert.assertEquals(2, d.score(1));
	}
	public static void testDoubleCrosses() 
	{
		DotsAndBoxes d = new DotsAndBoxes(5, 5);
		d.drawLine(1, 1, 0, 2, 0);
		d.drawLine(1, 2, 0, 3, 0);
		d.drawLine(1, 1, 1, 2, 1);
		d.drawLine(1, 2, 1, 3, 1);
		d.drawLine(1, 1, 0, 1, 1);
		d.drawLine(1, 3, 0, 3, 1);
		
		d.drawLine(1, 0, 2, 0, 3);
		d.drawLine(1, 0, 3, 0, 4);
		d.drawLine(1, 1, 2, 1, 3);
		d.drawLine(1, 1, 3, 1, 4);
		d.drawLine(1, 0, 2, 1, 2);
		d.drawLine(1, 0, 4, 1, 4);
		
		//Assert.assertEquals(2, d.countDoubleCrosses());
	}
	public static void testCycle() 
	{
		DotsAndBoxes d = new DotsAndBoxes(5, 5);
		
//		.-.-.-.-.
//		|       |
//		. .-.-. .
//		| |   | |
//		. . . . .
//		| |   | |
//		. .-.-. .
//		|       |
//		.-.-.-.-.
		
		d.drawLine(1, 0, 0, 0, 1);
		d.drawLine(1, 0, 1, 0, 2);
		d.drawLine(1, 0, 2, 0, 3);
		d.drawLine(1, 0, 3, 0, 4);
		
		d.drawLine(1, 0, 4, 1, 4);
		d.drawLine(1, 1, 4, 2, 4);
		d.drawLine(1, 2, 4, 3, 4);
		d.drawLine(1, 3, 4, 4, 4);
		
		d.drawLine(1, 4, 3, 4, 4);
		d.drawLine(1, 4, 2, 4, 3);
		d.drawLine(1, 4, 1, 4, 2);
		d.drawLine(1, 4, 0, 4, 1);
		
		d.drawLine(1, 3, 0, 4, 0);
		d.drawLine(1, 2, 0, 3, 0);
		d.drawLine(1, 1, 0, 2, 0);
		d.drawLine(1, 0, 0, 1, 0);
		
		d.drawLine(1, 1, 1, 1, 2);
		d.drawLine(1, 1, 2, 1, 3);
		
		d.drawLine(1, 1, 3, 2, 3);
		d.drawLine(1, 2, 3, 3, 3);
		
		d.drawLine(1, 3, 2, 3, 3);
		d.drawLine(1, 3, 1, 3, 2);
		
		d.drawLine(1, 2, 1, 3, 1);
		d.drawLine(1, 1, 1, 2, 1);
		
		//Assert.assertEquals(2, d.countCycles());
	}
	public static void testChains() {
		
//		. . .-.-.
//		| |     |
//		. .-.-. .
//		| |   | |
//		. . . . .
//		| |   | |
//		. .-.-. .
//		|       |
//		.-.-.-.-.
		
		DotsAndBoxes d = new DotsAndBoxes(5, 5);
		
		d.drawLine(1, 0, 0, 0, 1);
		d.drawLine(1, 0, 1, 0, 2);
		d.drawLine(1, 0, 2, 0, 3);
		d.drawLine(1, 0, 3, 0, 4);
		
		d.drawLine(1, 0, 4, 1, 4);
		d.drawLine(1, 1, 4, 2, 4);
		d.drawLine(1, 2, 4, 3, 4);
		d.drawLine(1, 3, 4, 4, 4);
		
		d.drawLine(1, 4, 3, 4, 4);
		d.drawLine(1, 4, 2, 4, 3);
		d.drawLine(1, 4, 1, 4, 2);
		d.drawLine(1, 4, 0, 4, 1);
		
		d.drawLine(1, 3, 0, 4, 0);
		d.drawLine(1, 2, 0, 3, 0);
		d.drawLine(1, 1, 0, 1, 1);
		
		d.drawLine(1, 1, 1, 1, 2);
		d.drawLine(1, 1, 2, 1, 3);
		
		d.drawLine(1, 1, 3, 2, 3);
		d.drawLine(1, 2, 3, 3, 3);
		
		d.drawLine(1, 3, 2, 3, 3);
		d.drawLine(1, 3, 1, 3, 2);
		
		d.drawLine(1, 2, 1, 3, 1);
		d.drawLine(1, 1, 1, 2, 1);
		
		//Assert.assertEquals(1, d.countOpenChains());		
	}
	public static void testTwoChains() {
		
//		. . .-.-.
//		| |     |
//		. .-.-. .
//		| |   | |
//		. . . . .
//		| |   | |
//		. .-.-. .
//		|   |   |
//		.-. . .-.
		
		DotsAndBoxes d = new DotsAndBoxes(5, 5);
		
		d.drawLine(1, 0, 0, 0, 1);
		d.drawLine(1, 0, 1, 0, 2);
		d.drawLine(1, 0, 2, 0, 3);
		d.drawLine(1, 0, 3, 0, 4);
		
		d.drawLine(1, 0, 4, 1, 4);
		d.drawLine(1, 2, 3, 2, 4);
		d.drawLine(1, 3, 4, 4, 4);
		
		d.drawLine(1, 4, 3, 4, 4);
		d.drawLine(1, 4, 2, 4, 3);
		d.drawLine(1, 4, 1, 4, 2);
		d.drawLine(1, 4, 0, 4, 1);
		
		d.drawLine(1, 3, 0, 4, 0);
		d.drawLine(1, 2, 0, 3, 0);
		d.drawLine(1, 1, 0, 1, 1);
		
		d.drawLine(1, 1, 1, 1, 2);
		d.drawLine(1, 1, 2, 1, 3);
		
		d.drawLine(1, 1, 3, 2, 3);
		d.drawLine(1, 2, 3, 3, 3);
		
		d.drawLine(1, 3, 2, 3, 3);
		d.drawLine(1, 3, 1, 3, 2);
		
		d.drawLine(1, 2, 1, 3, 1);
		d.drawLine(1, 1, 1, 2, 1);
		
		//Assert.assertEquals(2, d.countOpenChains());		
	}
	public static void testInternalChains() {
		
//		.-.-.-.-.
//		|       |
//		. .-.-. .
//		| |     |
//		. . .-. .
//		| |     |
//		. .-.-. .
//		|       |
//		.-.-.-.-.
		
		DotsAndBoxes d = new DotsAndBoxes(5, 5);
		
		d.drawLine(1, 0, 0, 0, 1);
		d.drawLine(1, 0, 1, 0, 2);
		d.drawLine(1, 0, 2, 0, 3);
		d.drawLine(1, 0, 3, 0, 4);
		
		d.drawLine(1, 0, 4, 1, 4);
		d.drawLine(1, 1, 4, 2, 4);
		d.drawLine(1, 2, 4, 3, 4);
		d.drawLine(1, 3, 4, 4, 4);
		
		d.drawLine(1, 4, 3, 4, 4);
		d.drawLine(1, 4, 2, 4, 3);
		d.drawLine(1, 4, 1, 4, 2);
		d.drawLine(1, 4, 0, 4, 1);
		
		d.drawLine(1, 3, 0, 4, 0);
		d.drawLine(1, 2, 0, 3, 0);
		d.drawLine(1, 1, 0, 2, 0);
		d.drawLine(1, 0, 0, 1, 0);
		
		d.drawLine(1, 1, 1, 1, 2);
		d.drawLine(1, 1, 2, 1, 3);
		
		d.drawLine(1, 1, 3, 2, 3);
		d.drawLine(1, 2, 3, 3, 3);
		
		d.drawLine(1, 2, 2, 3, 2);
		
		d.drawLine(1, 2, 1, 3, 1);
		d.drawLine(1, 1, 1, 2, 1);
		
		//Assert.assertEquals(2, d.countOpenChains());		
	}
	public static void testInternalChain() {
		
//		. . . . .
//		
//		. .-.-. .
//		  |     
//		. . ._. .
//		  |     
//		. .-.-. .
//		       
//		. . . . .
		
		DotsAndBoxes d = new DotsAndBoxes(5, 5);
		
		d.drawLine(1, 1, 1, 1, 2);
		d.drawLine(1, 1, 2, 1, 3);
		
		d.drawLine(1, 1, 3, 2, 3);
		d.drawLine(1, 2, 3, 3, 3);
		
		d.drawLine(1, 2, 2, 3, 2);
		//d.drawLine(1, 3, 2, 3, 3);
		
		d.drawLine(1, 2, 1, 3, 1);
		d.drawLine(1, 1, 1, 2, 1);
		
		//Assert.assertEquals(1, d.countOpenChains());		
	}
	public static void testNotAChain() {
		
//		. . . . .
//		
//		. .-.-. .
//		       
//		. ._._. .
//		      
//		. . . . .
//		       
//		. . . . .
		
		DotsAndBoxes d = new DotsAndBoxes(5, 5);

		d.drawLine(1, 1, 2, 2, 2);
		d.drawLine(1, 2, 2, 3, 2);
		
		d.drawLine(1, 2, 1, 3, 1);
		d.drawLine(1, 1, 1, 2, 1);
		
		//Assert.assertEquals(0, d.countOpenChains());		
		//Assert.assertEquals(0, d.countCycles());
		//Assert.assertEquals(0, d.countDoubleCrosses());
	}
	public static void main(String[] args)
	{
		//testTraversal();
		//DotsAndBoxes db = new DotsAndBoxes(5,5);
		
		//testOwner(); //score player 1 = 2
		//testDoubleCrosses(); //count double crosses = 2
		//testCycle(); //count cycles = 2
		
		//testChains();//count chains = 1
		testTwoChains(); //count chains = 2
		//testInternalChains(); //count chains = 2
		//testInternalChain(); //count chains = 1
		//testNotAChain();
	}
}
