
public class Node<T extends Comparable> implements Comparable<Node>
{
	T key;
	Node left;
	Node right;
	public Node(T data)
	{
		key = data;
		left = null;
		right = null;
	}
	public int compareTo(Node o)
	{
		return this.key.compareTo(o.key);
	}
	public String toString()
    {
		return ""+key;
    }
}
