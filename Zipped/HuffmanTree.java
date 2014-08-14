import java.awt.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

import edu.neumont.io.Bits;


public class HuffmanTree
{
	private class Tree implements Comparable<Tree>
	{
		Node root;
		double weight;
		@Override
		public int compareTo(Tree o)
		{
			return new Double(this.weight).compareTo(o.weight);
		}
	}
	private ArrayList<Node> nodes;
	private Tree tree;
	public HuffmanTree(byte[] b)
	{
		nodes = new ArrayList<Node>();
		for(int i = 0; i < b.length; i++)
		{
			ArrayList<Byte> keys = new ArrayList<Byte>();
			keys.add(b[i]);
			Node n = new Node(keys);
			int index = nodes.indexOf(n);
			if(index == -1)
			{
				n.frequency = 1;///(double)b.length);
				nodes.add(n);
			}
			else
			{
				nodes.get(index).frequency+= 1;///(double)b.length);
			}
		}
		PriorityQueue<Tree> trees = new PriorityQueue<Tree>();
		//ArrayList<Tree> trees = new ArrayList<Tree>();
		for(int i = 0; i < nodes.size(); i++)
		{
			Tree t = new Tree();
			t.root = nodes.get(i);
			t.weight = t.root.frequency;
			trees.add(t);
		}
		tree = compressTrees(trees);
	}
	
	public Tree compressTrees(PriorityQueue<Tree> trees)
	{
		if(trees.size() == 1)
		{
			return trees.poll();
		}
		
		Tree t1 = trees.poll();
		Tree t2 = trees.poll();
		ArrayList<Byte> keys = new ArrayList<Byte>();
		keys.addAll(t1.root.keys);
		keys.addAll(t2.root.keys);
		Node n = new Node(keys);
		n.frequency = t1.weight + t2.weight;
		n.left = t1.root;
		n.right = t2.root;
		Tree t = new Tree();
		t.root = n;
		t.weight = n.frequency;
		
		trees.add(t);
		return compressTrees(trees);
	}
	
	public byte toByte(Bits bits)//true = right // false = left
	{
		return toByte(bits, tree.root);
	}
	
	private byte toByte(Bits bits, Node n)
	{
		if(n.keys.size()==1)
		{
			return n.keys.get(0);
		}
		if(bits.isEmpty())
		{
			throw new NoSuchElementException();
		}
		Node next = (bits.poll())? n.right: n.left;
		return toByte(bits, next);
	}
	
	public void fromByte(byte b, Bits bits)
	{
		findByte(b, bits, tree.root);
	}
	
	private void findByte(byte b, Bits bits, Node n)
	{
		if(n.keys.size()==1)
		{
			if(n.keys.get(0) == b)
			{
				return;
			}
			else
			{
				throw new NoSuchElementException();
			}
		}
		if(n.left.keys.contains(b))
		{
			bits.add(false);
			findByte(b, bits, n.left);
		}
		else
		{
			bits.add(true);
			findByte(b, bits, n.right);
		}
	}
	
	ArrayList<ArrayList<String>> treeLevels;
	public void printTree()
	{
		treeLevels = new ArrayList<ArrayList<String>>();
		addNodeToLevel(tree.root, 0);

		for(int i = 0; i < treeLevels.size(); i++)
		{
			ArrayList<String> level = treeLevels.get(i);
			for(int j = 0; j < level.size(); j++)
			{
				System.out.print(level.get(j));
			}
			System.out.println();
		}
	}
	
	private void addNodeToLevel(Node n, int level)
	{
		if(n == null)
		{
			if(treeLevels.size()==(level))
			{
				treeLevels.add(new ArrayList<String>());
			}
			treeLevels.get(level).add(new String("[--] "));
			return;
		}
		if(treeLevels.size()==(level))
		{
			treeLevels.add(new ArrayList<String>());
		}
		//String data = (level==0)? "All" : n.data;
		String data = "All";
		if(level!=0)
		{
			data = ""+n.keys.get(0);
			for(int i = 1; i < n.keys.size(); i++)
			{
				data += "/" + n.keys.get(i);
			}
		}
		treeLevels.get(level).add(new String("[" + data + "-" + n.frequency + "] "));
		addNodeToLevel(n.left, level+1);
		addNodeToLevel(n.right, level+1);
	}
	
	public void printNodeWithBits()
	{
		printLeaf(tree.root, new ArrayList<Integer>());
		System.out.println();
	}
	
	private void printLeaf(Node n, ArrayList<Integer> bits)// 1 is true 0 is false
	{
		if(n == null)
		{
			return;
		}
		if(n.left==null && n.right==null)
		{
			System.out.print("[" +n.keys.get(0) + "-" + n.frequency +"(");
			for(int i = 0; i<bits.size(); i++)
			{
				System.out.print(bits.get(i));
			}
			System.out.print(")]");
			return;
		}
		ArrayList<Integer> bitsRight = new ArrayList<Integer>();
		bitsRight.addAll(bits);
		bitsRight.add(1);
		printLeaf(n.right, bitsRight);
		ArrayList<Integer> bitsLeft= new ArrayList<Integer>();
		bitsLeft.addAll(bits);
		bitsLeft.add(0);
		printLeaf(n.left, bitsLeft);
	}
	
	public void printNodes()
	{
		double totalFrequency = 0;
		for(int i = 0; i < nodes.size(); i++)
		{
			Node n = nodes.get(i);
			totalFrequency += n.frequency;
			String data = ""+n.keys.get(0);
			System.out.print("["+data + "-" + n.frequency+"]");
		}
		System.out.println();
		System.out.println("Total Frequency: " + totalFrequency);
	}
	
}
