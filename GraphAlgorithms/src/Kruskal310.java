//TODO: Implement the required methods and add JavaDoc as needed.
//Remember: Do NOT add any additional instance or class variables (local variables are ok)
//and do NOT alter any provided methods or change any method signatures!

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

import java.awt.Color;

import javax.swing.JPanel;


/**
 *  Simulation of Kruskal algorithm.
 *  
 */
class Kruskal310 implements ThreeTenAlg {
	/**
	 *  The graph the algorithm will run on.
	 */
	Graph<GraphNode, GraphEdge> graph;
	
	/**
	 *  The priority queue of edges for the algorithm.
	 */
	WeissBST<GraphEdge> pqueue;
	
	/**
	 *  The subgraph of the MST in construction.
	 */
	private Graph310 markedGraph;
	
	/**
	 *  Whether or not the algorithm has been started.
	 */
	private boolean started = false;
	
	/**
	 *  The color when a node has "no color".
	 */
	public static final Color COLOR_NONE_NODE = Color.WHITE;
	
	/**
	 *  The color when an edge has "no color".
	 */
	public static final Color COLOR_NONE_EDGE = Color.BLACK;
		
	/**
	 *  The color when a node is inactive.
	 */
	public static final Color COLOR_INACTIVE_NODE = Color.LIGHT_GRAY;

	/**
	 *  The color when an edge is inactive.
	 */
	public static final Color COLOR_INACTIVE_EDGE = Color.LIGHT_GRAY;
	
	/**
	 *  The color when a node is highlighted.
	 */
	public static final Color COLOR_HIGHLIGHT = new Color(255,204,51);
	
	/**
	 *  The color when a node is in warning.
	 */
	public static final Color COLOR_WARNING = new Color(255,51,51);

	/**
	 *  The color when a node/edge is selected and added to MST.
	 */
	public static final Color COLOR_SELECTED = Color.BLUE;
			
	/**
	 *  {@inheritDoc}
	 */
	public EdgeType graphEdgeType() {
		return EdgeType.UNDIRECTED;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void reset(Graph<GraphNode, GraphEdge> graph) {
		this.graph = graph;
		started = false;
		pqueue = null;	
		markedGraph = new Graph310();	
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public boolean isStarted() {
		return started;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void cleanUpLastStep() {
		// Unused. Required by the interface.		
	}
	

	//----------------------------------------------------
	// TODO: Complete the methods below to implement kruskal's algorithm.
	// - DO NOT change the signature of any required public methods;
	// - Feel free to define additional method but they must be private.
	//
	//----------------------------------------------------
	
	/**
	 *  {@inheritDoc}
	 */
	public void start() {
		started = true;
				
		//----------------------------------------------------
		// Complete the missing part:
		// - add all edges into the priority queue
		//----------------------------------------------------

		pqueue = new WeissBST<>();
		for(GraphEdge edge : graph.getEdges())
			pqueue.insert(edge);
		
		//----------------------------------------------------
		// End of missing part
		//----------------------------------------------------

		//highlight the edge with min weight 
		highlightNext();
			
	}
	/**
	 * Finds the current min edge in the priority queue and changes the color of the edge to be COLOR_HIGHLIGHT.
	 */
	public void highlightNext(){	
		// Note: do not dequeue the node.
		pqueue.findMin().setColor(COLOR_HIGHLIGHT);
	}

	/**
	 *  {@inheritDoc}
	 */
	public void finish() {
	
		//wrapping up the algorithm
		// - mark all edges not selected to be inactive		
		// - mark all nodes not in the constructed MST with COLOR_WARINING
		for(GraphEdge edge : pqueue.values())
			edge.setColor(COLOR_INACTIVE_EDGE);
		for(GraphNode node : graph.getVertices())
			if(!markedGraph.containsVertex(node))
				node.setColor(COLOR_WARNING);
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public boolean setupNextStep() {
	
		//decide whether we are done with the MST algorithm
		// return true if more steps to continue; return false if done
		// Hint: you may not always need to check all edges.
		if(markedGraph.getEdgeCount() == graph.getVertexCount() - 1 || pqueue.isEmpty())
			return false;
		
		return true;
		
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void doNextStep() {
	
		//remove the next min edge from priority queue and check:
		// - if edge should be included in MST, add it into MST and change the color of 
		//     the edge and nodes (COLOR_SELECTED)
		// - if edge should not be included, change its color to COLOR_INACTIVE_EDGE
		// - if MST is not completed, highlight next min edge
		GraphEdge min = pqueue.findMin();
		pqueue.removeMin();

		GraphNode v1 = graph.getEndpoints(min).getFirst();
		GraphNode v2 = graph.getEndpoints(min).getSecond();
		
		if(markedGraph.reachableSet(v1) == null || !markedGraph.reachableSet(v1).contains(v2))
		{
			markedGraph.addEdge(min, v1, v2);
			v1.setColor(COLOR_SELECTED);
			v2.setColor(COLOR_SELECTED);
			min.setColor(COLOR_SELECTED);
		}
		else
		{
			min.setColor(COLOR_INACTIVE_EDGE);
		}

		if(setupNextStep())
			highlightNext();
	
	}
	
	//----------------------------------------------------
	// Testing code: edit as much as you need. 
	//----------------------------------------------------
	
	/**
	 * Main method for testing.
	 * @param args Arguments to provide before running
	 */
	public static void main(String[] args){

		Graph310 graph = new Graph310();
		Kruskal310 kruskal = new Kruskal310();
		
		GraphNode[] nodes = {
			new GraphNode(0), 
			new GraphNode(1)
		};

		GraphEdge[] edges = {
			new GraphEdge(0)
		};
		
		
		// a graph of two nodes, one edge
		graph.addVertex(nodes[0]);
		graph.addVertex(nodes[1]);
		graph.addEdge(edges[0], nodes[0], nodes[1]); 
		
		kruskal.reset(graph);
		while (kruskal.step()) {} //execution of all steps

		
		if (nodes[0].getColor()==COLOR_SELECTED && nodes[1].getColor()==COLOR_SELECTED &&
			edges[0].getColor()==COLOR_SELECTED && kruskal.pqueue.size()==0){
			System.out.println("Yay1!");
		}
		
		//start over with a new graph
		graph = new Graph310();
		GraphNode[] nodes2 = {
			new GraphNode(0), 
			new GraphNode(1), 
			new GraphNode(2), 
			new GraphNode(3), 
			new GraphNode(4), 
			new GraphNode(5)
		};

		GraphEdge[] edges2 = {
			new GraphEdge(0,7), //id, weight
			new GraphEdge(1,1), 
			new GraphEdge(2,19), 
			new GraphEdge(3,3), 
			new GraphEdge(4,16), 
			new GraphEdge(5,2), 
			new GraphEdge(6,9)
		};
		
		
		graph.addVertex(nodes2[0]);
		graph.addVertex(nodes2[1]);
		graph.addVertex(nodes2[2]);
		graph.addVertex(nodes2[3]);
		graph.addVertex(nodes2[4]);
		graph.addVertex(nodes2[5]);

		graph.addEdge(edges2[0], nodes2[2], nodes2[0]); 
		graph.addEdge(edges2[1], nodes2[3], nodes2[1]); 
		graph.addEdge(edges2[2], nodes2[1], nodes2[5]); 
		graph.addEdge(edges2[3], nodes2[3], nodes2[2]); 
		graph.addEdge(edges2[4], nodes2[2], nodes2[5]); 
		graph.addEdge(edges2[5], nodes2[3], nodes2[0]); 
		graph.addEdge(edges2[6], nodes2[0], nodes2[5]); 

		kruskal.reset(graph);
		while (kruskal.step()) {} //execution of all steps

		//edges 1,3,5,6 selected, nodes 0,1,2,3,5 selected
		if (nodes2[4].getColor()==COLOR_WARNING && nodes2[0].getColor()==COLOR_SELECTED &&
			edges2[1].getColor()==COLOR_SELECTED && edges2[0].getColor()==COLOR_INACTIVE_EDGE){
			System.out.println("Yay2!");
		}
		
		
		
		//write your own testing code ...		
	}

}