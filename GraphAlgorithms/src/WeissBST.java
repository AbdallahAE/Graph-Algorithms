// Binary Search Tree class From your textbook (Weiss)

import java.util.LinkedList; //only for the return of values(), do not use it anywhere else

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @param <T> Generic type for the nodes in the tree.
 * @author Mark Allen Weiss.
 */
public class WeissBST<T extends Comparable<? super T>>
{

	//--------------------------------------------------------
	// CODE PROVIDED from WEISS
	//--------------------------------------------------------
	// Do NOT change the implementation - you should only edit 
	// in order to pass JavaDoc and style checking.
	//--------------------------------------------------------

	/**
	 * Basic node stored in unbalanced binary search trees.
	 * @param <T> Generic type for the node.
	 */
	private class BinaryNode<T>
	{
		/**
		 * Constructs the Node.
		 * @param theElement Takes in an element of Generic type T to se the value of the node.
		 */
		BinaryNode(T theElement)
		{
	   		element = theElement;
			left = right = null;
		}

		/**
		 * The data in the node.
		 */
		T element;
		/**
		 * Left child.
		 */
		BinaryNode<T> left;
		/**
		 * Right child.
		 */
		BinaryNode<T> right;
	}

	//NOTE: you may not have any other instance variables, only this one below.
	//if you make more instance variables your binary search tree class 
	//will receive a 0, no matter how well it works
	
	/**
	 * The tree root.
	 */
	private BinaryNode<T> root;



	/**
	 * Construct the tree.
	 */
	public WeissBST( )
	{
		root = null;
	}

	/**
	 * Insert into the tree.
	 * @param x the item to insert.
	 * @throws Exception if x is already present.
	 */
	public void insert( T x )
	{
		root = insert( x, root );
	}

	/**
	 * Remove minimum item from the tree.
	 * @throws Exception if tree is empty.
	 */
	public void removeMin( )
	{
		root = removeMin( root );
	}

	/**
	 * Find the smallest item in the tree.
	 * @return smallest item or null if empty.
	 */
	public T findMin( )
	{
		return elementAt( findMin( root ) );
	}


	/**
	 * Find an item in the tree.
	 * @param x the item to search for.
	 * @return the matching item or null if not found.
	 */
	public T find( T x )
	{
		return elementAt( find( x, root ) );
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty( )
	{
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty( )
	{
		return root == null;
	}

	/**
	 * Internal method to get element field.
	 * @param t the node.
	 * @return the element field or null if t is null.
	 */
	private T elementAt( BinaryNode<T> t )
	{
		return t == null ? null : t.element;
	}

	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the tree.
	 * @return the new root.
	 * @throws Exception if x is already present.
	 */
	private BinaryNode<T> insert( T x, BinaryNode<T> t )
	{
		if( t == null )
			t = new BinaryNode<T>( x );
		else if( x.compareTo( t.element ) < 0 )
			t.left = insert( x, t.left );
		else if( x.compareTo( t.element ) > 0 )
			t.right = insert( x, t.right );
		else
			throw new IllegalArgumentException( "Duplicate Item: " + x.toString( ) );  // Duplicate
		return t;
	}


	/**
	 * Internal method to remove minimum item from a subtree.
	 * @param t the node that roots the tree.
	 * @return the new root.
	 * @throws Exception if t is empty.
	 */
	private BinaryNode<T> removeMin( BinaryNode<T> t )
	{
		if( t == null )
			throw new IllegalArgumentException( "Min Item Not Found");
		else if( t.left != null )
		{
			t.left = removeMin( t.left );
			return t;
		}
		else
			return t.right;
	}	

	/**
	 * Internal method to find the smallest item in a subtree.
	 * @param t the node that roots the tree.
	 * @return node containing the smallest item.
	 */
	private BinaryNode<T> findMin( BinaryNode<T> t )
	{
		if( t != null )
			while( t.left != null )
				t = t.left;

		return t;
	}


	/**
	 * Internal method to find an item in a subtree.
	 * @param x is item to search for.
	 * @param t the node that roots the tree.
	 * @return node containing the matched item.
	 */
	private BinaryNode<T> find( T x, BinaryNode<T> t )
	{
		while( t != null )
		{
			if( x.compareTo( t.element ) < 0 )
				t = t.left;
			else if( x.compareTo( t.element ) > 0 )
				t = t.right;
			else
				return t;	// Match
		}
		
		return null;		 // Not found
	}

	//--------------------------------------------------------
	// CODE THAT YOU MAY NEED TO CHANGE
	//--------------------------------------------------------
	// We need the BST removal to be "predecessor replacement".
	// Make necessary changes to match the expected behavior.
	// Feel free to add private helper methods as needed.
	//--------------------------------------------------------

	/**
	 * Remove from the tree..
	 * @param x the item to remove.
	 * @throws Exception if x is not found.
	 */
	public void remove( T x )
	{
		root = remove( x, root );
	}

	/**
	 * Internal method to remove from a subtree.
	 * @param x the item to remove.
	 * @param t the node that roots the tree.
	 * @return the new root.
	 * @throws Exception if x is not found.
	 */
	private BinaryNode<T> remove( T x, BinaryNode<T> t )
	{
		if( t == null )
			throw new IllegalArgumentException( "Item Not Found: " + x.toString( ) );
		if( x.compareTo( t.element ) < 0 )
			t.left = remove( x, t.left );
		else if( x.compareTo( t.element ) > 0 )
			t.right = remove( x, t.right );
		else if( t.left != null && t.right != null ) // Two children
		{
			t.element = findMax( t.left ).element;
			t.left = removeMax( t.left );
		}
		else
			t = ( t.left != null ) ? t.left : t.right;
		return t;
	}

	/**
	 * Internal method to remove maximum item from a subtree.
	 * @param t the node that roots the tree.
	 * @return the new root.
	 * @throws Exception if t is empty.
	 */
	private BinaryNode<T> removeMax( BinaryNode<T> t )
	{
		if( t == null )
			throw new IllegalArgumentException( "Max Item Not Found");
		else if( t.right != null )
		{
			t.right = removeMax( t.right );
			return t;
		}
		else
			return t.left;
	}	
	/**
	 * Internal method to find the largest item in a subtree.
	 * @param t the node that roots the tree.
	 * @return node containing the largest item.
	 */
	private BinaryNode<T> findMax( BinaryNode<T> t )
	{
		if( t != null )
			while( t.right != null )
				t = t.right;

		return t;
	}


	//--------------------------------------------------------
	// CODE YOU MUST IMPLEMENT
	//--------------------------------------------------------
	// Feel free to define private helper methods.
	// Remember to add JavaDoc for your methods.
	//--------------------------------------------------------


	/**
	 * Reports the number of nodes in tree.
	 * @return Size of the tree, 0 for null trees
	 */
	public int size(){
		LinkedList<T> list = new LinkedList<>();
		depthFirstTraversal(root, (byte)0, list);
		return list.size();
	}
	
	/**
	 * Follows and IN-ORDER traversal to include all nodes and adds a space between each element to print.
	 * @return Returns a string representation of the tree.
	 */
	public String toString(){
		LinkedList<T> list = new LinkedList<>();
		depthFirstTraversal(root, (byte)1, list);

		String result = "";
		for(T a: list)
			result += a + " ";
		return result;
  	}
  	
	/**
	 * Follows a PRE-ORDER traversal of the tree and includes in a list.
	 * @return Returns an array representation of all values.
	 */
	public LinkedList<T> values(){
		LinkedList<T> list = new LinkedList<>();
		depthFirstTraversal(root, (byte)0, list);
		return list;
	}
	
	/**
	* Edits the list given to travers the tree in either post, pre, or in order.
	* @param node The node to start traversing from.
	* @param i Is represents the three possible depth-first traversals, 0 for pre, 1 for in, and 2 for post order.
	* @param list The list that contains the traversal.
	*/
	private void depthFirstTraversal(BinaryNode<T> node, byte i, LinkedList<T> list)
	{
		// Depending on the parameter which order to use.
		switch(i)
		{
			case 0:
				if(node == null)
            		return;
        		list.add(node.element);
        		depthFirstTraversal(node.left, (byte) 0, list);
        		depthFirstTraversal(node.right, (byte) 0, list);
				break;
			case 1:
				if(node == null)
            		return;
        		depthFirstTraversal(node.left, (byte) 1, list);
				list.add(node.element);
        		depthFirstTraversal(node.right, (byte) 1, list);
				break;
			case 2:
				if(node == null)
            		return;
        		depthFirstTraversal(node.left, (byte) 2, list);
        		depthFirstTraversal(node.right, (byte) 2, list);
				list.add(node.element);
				break;
		}
	}
 


	//--------------------------------------------------------
	// TESTING CODE
	//--------------------------------------------------------
	// Edit as much as you want ... do not forget JavaDoc
	//--------------------------------------------------------


	/**
	 * Main method for testing.
	 * @param args Takes in arguments.
	 */
	public static void main( String [ ] args )
	{
		WeissBST<Integer> t = new WeissBST<Integer>( );

		if (t.size()==0 && t.isEmpty() && t.find(310)==null){
			System.out.println("Yay 1");
		}
		
		t.insert(310);
		t.insert(112);
		t.insert(440);
		t.insert(330);
		t.insert(471);
		LinkedList<Integer> values = t.values();

		// Current tree:
		//			  310
		//           /   \
		//        112     440
		//                /  \
		//              330  471
  		
		
		if (t.size()==5 && t.toString().equals("112 310 330 440 471 ") && !t.isEmpty()){
			System.out.println("Yay 2");
		}

		if (values.size()==5 && values.get(0)==310 && values.get(1)==112 &&
			values.get(2) == 440 && values.get(3)== 330 && values.get(4)== 471){
			System.out.println("Yay 3");
		}

		//remove
		t.remove(440);
		
		//check removal with predecessor replacement
		//tree expected:
		//
		//			  310
		//           /   \
		//        112     330
		//                  \
		//              	 471
		values = t.values();
		if (values.size() == 4 && values.get(2)==330 && values.get(3)==471){
			System.out.println("Yay 4");		
		}
		//System.out.print(t);
	
	}
	
}
