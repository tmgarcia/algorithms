import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


public class BfsGraphTraversal
{
	int VISITED = 1;
	int UNVISITED = -1;
	List<List<Integer>> traverse(Graph g)
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
				traversal.add(traverseComponent(g, v));
			}
		}
		return traversal;
	}

	
	private List<Integer> traverseComponent (Graph g, int firstVert)
	{
		List<Integer> visited = new ArrayList<Integer>();
		Queue<Integer> q = new ArrayBlockingQueue<Integer>(g.vcount());
		q.offer(firstVert);
		g.setMark(firstVert,  VISITED);
		while(q.size() > 0)
		{
			int v = q.poll();
			visited.add(v);
			for(int w = g.first(v); w < g.vcount(); w = g.next(v,  w))
			{
				if(g.getMark(w) == UNVISITED)
				{
					g.setMark(w, VISITED);
					q.offer(w);
				}
			}
		}
		return visited;
	}
}
