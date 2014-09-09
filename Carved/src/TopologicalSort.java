import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TopologicalSort
{
	private int UNVISITED = 0;
	private int VISITED = 1;
	
	private List<Integer> verts;
	
	public List<Integer> sort(Graph g)
	{
		for(int i = 0; i < g.vcount(); i++)
		{
			g.setMark(i, UNVISITED);
		}
		verts = new ArrayList<Integer>();
		for(int i = 0; i < g.vcount(); i++)
		{
			if(g.getMark(i) == UNVISITED)
			{
				sortHelper(g, i);
			}
		}
		Collections.reverse(verts);
		return verts;
	}
	
	private void sortHelper(Graph g, int v)
	{
		g.setMark(v, VISITED);
		for(int w = g.first(v); w<g.vcount(); w = g.next(v,w))
		{
			if(g.getMark(w) == UNVISITED)
			{
				sortHelper(g, w);
			}
		}
		verts.add(v);
	}
}
