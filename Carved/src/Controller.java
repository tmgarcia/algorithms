import java.util.List;

import edu.neumont.ui.Picture;


public class Controller
{

	public static void main(String[]args)
	{
		testTopoSort();
		//testSeamCarving();
		
	}
	public static void testSeamCarving()
	{
		Picture pic = new Picture("Images/overlayimagewithhiddenmessage.png");
		SeamCarver carve = new SeamCarver(pic);
		
		for(int i = 0; i < 220; i++)
		{
			carve.removeVerticalSeam(carve.findVerticalSeam());
		}
		
		for(int i = 0; i < 190; i++)
		{
			carve.removeHorizontalSeam(carve.findHorizontalSeam());
		}
		
		pic = carve.getPicture();
		pic.show();
		pic.save("test.png");
	}
	public static void testTopoSort()
	{
		Graph g = new Graph(7);
		g.addEdge(0, 1, 1);
		g.addEdge(0, 2, 1);
		g.addEdge(1, 3, 1);
		g.addEdge(1, 4, 1);
		g.addEdge(1, 5, 1);
		g.addEdge(2, 3, 1);
		g.addEdge(3, 4, 1);
		g.addEdge(4, 6, 1);
		
		TopologicalSort topo = new TopologicalSort();
		List<Integer> sorted = topo.sort(g);
		for(int i = 0; i < sorted.size(); i++)
		{
			System.out.print(sorted.get(i)+",");
		}
		System.out.println();
	}
	
	public static void printArray(int[] ints)
	{
		for(int i = 0; i < ints.length; i++)
		{
			System.out.println("["+ i + "] = " + ints[i]);
		}
	}
}
