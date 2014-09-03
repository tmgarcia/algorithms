import java.security.InvalidParameterException;


public class Graph
{
	private int[][] matrix;
	private int[] marks;
	private int numEdges;
	public Graph(int n)
	{
		matrix = new int[n][n];
		marks = new int[n];
		numEdges = 0;
	}
	
	//first neighbor, next neighbor after the one just got
	//first(3) - get vert 3's first neighbor
	//next(3, 4) - get vert 3's first neghbor after 4
	// keep in mind 0 vs 1 based arrays & indexing
	public int vcount()
	{
		return marks.length;
	}
	public int ecount()
	{
		return numEdges;
	}
	public int first(int v)// alternative -> call next, pass -1
	{
		int firstNeighbor = vcount();//don't find something, return number of vertices
		for(int i = 0; i < vcount() && firstNeighbor == vcount(); i++)
		{
			if(matrix[v][i] != 0)//0 means there is no connection
			{
				firstNeighbor = i;
			}
		}
		return firstNeighbor;
	}
	public int next(int vertex, int previous)
	{
		int nextNeighbor = vcount();
		for(int i = previous+1; i < vcount() && nextNeighbor == vcount(); i++)
		{
			if(matrix[vertex][i] != 0)
			{
				nextNeighbor = i;
			}
		}
		return nextNeighbor;
	}
	
	public void addEdge(int vertex, int neighbor, int weight)
	{
		//if adding weight of 0, complain, 
		//that means they are not neighbors, 
		//what are you doing, go remove the edge, stop that
		if(weight==0)
		{
			throw new InvalidParameterException();
		}
		matrix[vertex][neighbor] = weight;
		numEdges++;
	}
	
	public void removeEdge(int vertex, int neighbor)
	{
		matrix[vertex][neighbor] = 0;
		numEdges--;
	}
	
	public boolean isEdge(int vertex, int neighbor)//is there an edge between these two 
	{
		return (matrix[vertex][neighbor] != 0);//don't want > 0 because might have negative weights in the future
	}
	public int getMark(int vertex)
	{
		return marks[vertex];
	}
	public void setMark(int vertex, int mark)
	{
		marks[vertex] = mark;
	}
	public int degree(int vertex)
	{
		int edges = 0;
		for(int w = first(vertex); w != vcount(); w=next(vertex, w))
		{
			edges++;
		}
		return edges;
	}
	public void printGraph()
	{
		System.out.print("#");
		for(int i = 0; i < marks.length; i++)
		{
			System.out.print("  " + i);
		}
		System.out.println();
		for(int i = 0; i < matrix.length; i++)
		{
			System.out.print(i);
			for(int j = 0; j < matrix[i].length; j++)
			{
				System.out.print("  " + matrix[i][j]);
			}
			System.out.println();
		}
	}
}
