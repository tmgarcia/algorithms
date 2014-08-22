import java.util.ArrayList;

public class Node implements Comparable<Node>, BinaryNode
{
	//public String data;
	public ArrayList<Byte> keys;
	public int frequency;
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
    public String toString()
    {
    	String ret = "";
    	if(keys.size() > 1)
    	{
    		ret += "*";
    	}
    	else
    	{
    		ret+= keys.get(0);
    	}
    	
//    	for(int i = 0; i < keys.size(); i++)
//    	{
//    		ret += keys.get(i) + ",";
//    	}
    	ret += ("(" + (int)frequency + ")");
    	return ret;
    }
    
	@Override
	public int compareTo(Node o)
	{
		return new Integer(this.frequency).compareTo(o.frequency);
	}
	@Override
	public BinaryNode getLeft()
	{
		return left;
	}
	@Override
	public BinaryNode getRight()
	{
		return right;
	}

}
