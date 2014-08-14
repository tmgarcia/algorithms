import java.util.ArrayList;

public class Node implements Comparable<Node>
{
	//public String data;
	public ArrayList<Byte> keys;
	public double frequency;
	public Node left;
	public Node right;
	
	public Node(ArrayList<Byte> keys)
	{
		//data = d;
		this.keys = keys;
		frequency = 0;
		left = null;
		right = null;
	}
	public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        Node other = (Node)obj;
        return keys.equals(other.keys);
    }

    public int hashCode()
    {
        int hash = 7;
        hash = (int) (31 * hash);
        return hash;
    }
	@Override
	public int compareTo(Node o)
	{
		return new Double(this.frequency).compareTo(o.frequency);
	}

}
