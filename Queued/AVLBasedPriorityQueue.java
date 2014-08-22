import java.util.NoSuchElementException;


public class AVLBasedPriorityQueue<T extends Comparable> 
{
	Node treeRoot;
	public AVLBasedPriorityQueue()
	{
		treeRoot = null;
	}
	public Node getRoot()
	{
		return treeRoot;
	}
	
	public void print()
	{
		print(treeRoot);
	}
	
	private void print(Node root)
	{
		if(root == null)
		{
			return;
		}
		System.out.println(root + "- L:" + root.left + " R:" + root.right);
		print(root.left);
		print(root.right);
	}
	public boolean offer(T data)
	{
		boolean success = true;
		if(data == null)
		{
			success = false;
		}
		else
		{
			Node n = new Node(data);
			if(treeRoot == null)
			{
				treeRoot = n;
			}
			else
			{
				placeChildNode(treeRoot, n, null);
			}
		}
		return success;
	}
	
	public T poll()
	{
		Node n = findAndRemoveHighestNode();
		if(n == null)
		{
			throw new NoSuchElementException();
		}
		return (T) n.key;
	}

	private Node findAndRemoveHighestNode()
	{
		highestParent = null;
		Node n = getHighestValueNode(treeRoot, null);
		if(n != null)
		{
			removeNode(n, highestParent);
		}
		return n;
	}
	private Node parent;
	private Node findNode(Node root, Node parent, T key)
	{
		if(root == null)
		{
			return null;
		}
		else if(root.key == key)
		{
			parent = parent;
			return root;
		}
		Node result = findNode(root.left, root, key);
		if(result== null)
		{
			result = findNode(root.right, root, key);
		}
		return result;
	}
	private void removeNode(Node n, Node parentNode)
	{
		//n is root
		if(treeRoot == n)
		{
			treeRoot = null;
			if(n.left != null)
			{
				treeRoot = n.left;
				if(n.right != null)
				{
					placeChildNode(treeRoot, n.right, null);
				}
			}
			else if(n.right != null)
			{
				treeRoot = n.right;
			}
		}
		//n has 1 child
		else if((n.right == null && n.left != null)||(n.right != null && n.left == null))
		{
			if(n.right != null)//has right child
			{
				if(n.key.compareTo(parentNode.key) < 0)//n < parentNode, on left
				{
					parentNode.left = n.right;
				}
				else
				{
					parentNode.right = n.right;
				}
			}
			else //has left child
			{
				if(n.key.compareTo(parentNode.key) < 0)//n < parentNode, on left
				{
					parentNode.left = n.left;
				}
				else
				{
					parentNode.right = n.left;
				}
			}
			balanceTree(treeRoot, null);
		}
		//n has 2 children
		else if(n.right != null && n.left != null)
		{
			if(n.key.compareTo(parentNode.key) < 0)//n < parentNode, on left
			{
				parentNode.left = n.left;
				placeChildNode(treeRoot, n.right, null);
			}
			else//n > parentNode, on right
			{
				parentNode.right = n.left;
				placeChildNode(treeRoot, n.right, null);
			}
		}
		//n is leaf
		else if(n.right == null && n.left == null)
		{
			if(n.key.compareTo(parentNode.key) < 0)//n < parentNode, on left
			{
				parentNode.left = null;
			}
			else//n > parentNode, on right
			{
				parentNode.right = null;
			}
			balanceTree(treeRoot, null);
		}
	}

	private void balanceTree(Node root, Node parent)
	{
		if(root == null)
		{
			return;
		}
		balanceTree(root.left, root);
		balanceTree(root.right, root);
		balanceAtRoot(root, parent);
	}
	
	private void balanceAtRoot(Node root, Node rootParent)
	{
		int balance = getBalance(root);
		if(Math.abs(balance) > 1)
		{
			if(balance > 0)//e.g. +2
			{
				if(getBalance(root.left)>0)//same sign, single rotate (+1)
				{
					rotateRight(root,rootParent);
				}
				else//different sign, double rotate (-1)
				{
					rotateLeftRight(root,rootParent);
				}
			}
			else//e.g. -2
			{
				if(getBalance(root.right)<0)//same sign, single rotate (+1)
				{
					rotateLeft(root,rootParent);
				}
				else//different sign, double rotate (-1)
				{
					rotateRightLeft(root,rootParent);
				}
			}
		}
	}
	
	public T peek()
	{
		if(treeRoot == null)
		{
			return null;
		}
		return (T) getHighestValueNode(treeRoot, null).key;
	}

	private void rotateLeft(Node root, Node rootParent)
	{
		Node newRoot = root.right;
		root.right = null;
		if(rootParent != null)
		{
			if(root.key.compareTo(rootParent.key) < 0)//root < rootParent
			{
				rootParent.left = newRoot;
			}
			else 
			{
				rootParent.right = newRoot;
			}
		}
		if(newRoot.left != null)
		{
			root.right = newRoot.left;
		}
		newRoot.left = root;
		if(root == treeRoot)
		{
			treeRoot = newRoot;
		}
	}
	private void rotateRight(Node root, Node rootParent)
	{
		Node newRoot = root.left;
		root.left = null;
		if(rootParent != null)
		{
			if(root.key.compareTo(rootParent.key) < 0)//root < rootParent
			{
				rootParent.left = newRoot;
			}
			else 
			{
				rootParent.right = newRoot;
			}
		}
		if(newRoot.right != null)
		{
			root.left = newRoot.right;
		}
		newRoot.right = root;
		if(root == treeRoot)
		{
			treeRoot = newRoot;
		}
	}
	private void rotateLeftRight(Node root, Node rootParent)
	{
		Node rootLeft = root.left;
		Node newRoot = rootLeft.right;
		root.left = null;
		rootLeft.right = null;
		if(rootParent != null)
		{
			if(root.key.compareTo(rootParent.key) < 0)//root < rootParent
			{
				rootParent.left = newRoot;
			}
			else 
			{
				rootParent.right = newRoot;
			}
		}
		if(newRoot.left != null)
		{
			rootLeft.right = newRoot.left;
		}
		if(newRoot.right != null)
		{
			root.left = newRoot.right;
		}
		newRoot.left = rootLeft;
		newRoot.right = root;
		if(root == treeRoot)
		{
			treeRoot = newRoot;
		}
	}
	private void rotateRightLeft(Node root,Node rootParent)
	{
		Node rootRight= root.right;
		Node newRoot = rootRight.left;
		root.right = null;
		rootRight.left = null;
		if(rootParent != null)
		{
			if(root.key.compareTo(rootParent.key) < 0)//root < rootParent
			{
				rootParent.left = newRoot;
			}
			else 
			{
				rootParent.right = newRoot;
			}
		}
		if(newRoot.left != null)
		{
			root.right = newRoot.left;
		}
		if(newRoot.right != null)
		{
			rootRight.left = newRoot.right;
		}
		newRoot.left = root;
		newRoot.right = rootRight;
		if(root == treeRoot)
		{
			treeRoot = newRoot;
		}
	}
	private Node highestParent;
	private Node getHighestValueNode(Node root, Node parent)
	{
		if(root.right == null)
		{
			highestParent = parent;
			return root;
		}
		return getHighestValueNode(root.right, root);
	}
	
	//empty tree = -1
	//balance = height of left - height of right
	//height of leaf = 0
	//no right = 1 (0 - -1)
	//no left = -1 (-1 - 0)
	private void placeChildNode(Node root, Node child, Node rootParent)
	{
		//if(child.key.compareTo(root.key) >= 0)//they are equal or child > root
		//else if(child.key.compareTo(root.key) < 0)//child < root
		if(child.key.compareTo(root.key) >= 0 && root.right == null)//put as right
		{
			root.right = child;
		}
		else if(child.key.compareTo(root.key) < 0 && root.left == null)
		{
			root.left = child;
		}
		else
		{
			Node nextNode = (child.key.compareTo(root.key) < 0)? root.left: root.right;
			placeChildNode(nextNode, child, root);
		}
		
		balanceAtRoot(root, rootParent);
	}
	private int getBalance(Node root)
	{
		if(root == null)
		{
			return -1;
		}
		int balance = getHeight(root.left) - getHeight(root.right);
		return balance;
	}
	private int getHeight(Node root)
	{
		//int height = 0;
		if(root == null)
		{
			return  -1;
		}
		else
		{
			return Math.max(getHeight(root.right)+1, getHeight(root.left)+1);
		}
		//return 0;
	}
}
