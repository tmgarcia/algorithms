import java.util.ArrayList;
import java.util.List;


public class DfsGraphTraversal
{
	int VISITED = 1;
	int UNVISITED = -1;
	public List<List<Integer>> traverse(Graph g)
	{
		List<List<Integer>> traversal = new ArrayList<List<Integer>>();
		int v;
		for(v = 0; v < g.vcount(); v++)
		{
			g.setMark(v, UNVISITED);
		}
		for(v = 0; v < g.vcount(); v++)
		{
			if(g.getMark(v)== UNVISITED)
			{
				traversal.add(traverseComponent(g, v, new ArrayList<Integer>()));
			}
		}
		return traversal;
	}
	
	private List<Integer> traverseComponent (Graph g, int vert, List<Integer> visited)
	{
		
		g.setMark(vert, VISITED);
		for(int i = g.first(vert); i<g.vcount(); i = g.next(vert,i))
		{
			if(g.getMark(i) == UNVISITED)
			{
				traverseComponent(g, i, visited);
			}
		}
		visited.add(vert);
		return visited;
	}
}
