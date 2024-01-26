import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;

import org.apache.commons.collections15.Factory;

import java.util.Collection;

/**
 * Creates a graph.
 */
class Graph310 implements Graph<GraphNode,GraphEdge>, UndirectedGraph<GraphNode,GraphEdge>
{

    /**
     * A constant defining the maximum number of nodes.
     */
    private static final int MAX_NUMBER_OF_NODES = 200;
	
    /**
     * The map that stores the graph.
     */
    private Map310<GraphNode,Map310<GraphNode,GraphEdge>> storage;
	
    /**
     * Constructs the graph.
     */
    public Graph310()
    {
        storage = new Map310<>();
    }
	

    /**
     * Returns a view of all edges in this graph.
     * In general, this obeys the Collection contract, and therefore makes no guarantees about the ordering of the edges within the set.
     * @return A Collection view of all edges in this graph.
     */
    public Collection<GraphEdge> getEdges()
    {
		Set310<GraphEdge> set = new Set310<>();		
        for(Map310<GraphNode,GraphEdge> map : storage.values())
            for(GraphEdge edge : map.values())
                set.add(edge);
        return set;
		
	}
    
    /**
     * Returns a view of all vertices in this graph. 
     * In general, this obeys the Collection contract, and therefore makes no guarantees about the ordering of the vertices within the set.
     * @return A Collection view of all vertices in this graph.
     */
    public Collection<GraphNode> getVertices() {
    
		return storage.keySet();		
		
	}
    
    /**
     * Returns the number of edges in this graph.
     * @return The number of edges in this graph.
     */
    public int getEdgeCount() {

		return getEdges().size();
		
	}

    /**
     * Returns the number of vertices in this graph.
     * @return The number of vertices in this graph.
     */
    public int getVertexCount() {
		
		return storage.size();
	}


    /**
     * Returns true if this graph's vertex collection contains vertex.
     * Equivalent to getVertices().contains(vertex).
     * @param vertex The vertex whose presence is being queried.
     * @return True if this graph contains a vertex.
     */
    public boolean containsVertex(GraphNode vertex) {
    	
		return getVertices().contains(vertex);
	}
     
	
    /**
     * Returns the collection of vertices which are connected to vertex via any edges in this graph.
     * If vertex is connected to itself with a self-loop, then it will be included in the collection returned.
     * @param vertex The vertex whose neighbors are to be returned.
     * @return The collection of vertices which are connected to vertex, or null if vertex is not present.
     */
    public Collection<GraphNode> getNeighbors(GraphNode vertex) {

        if(storage.get(vertex) == null)
            return null;
        return storage.get(vertex).keySet(); //default return, remove or change as needed		
		
	}


    /**
     * Returns the number of vertices that are adjacent to vertex
     * (that is, the number of vertices that are incident to edges in vertex's
     * incident edge set).
     * 
     * <p>Equivalent to getNeighbors(vertex).size().
     * @param vertex the vertex whose neighbor count is to be returned
     * @return the number of neighboring vertices
     */
    public int getNeighborCount(GraphNode vertex) {
        if(getNeighbors(vertex) == null)
            return 0;
        return getNeighbors(vertex).size(); //default return, remove or change as needed
	}


    
    /**
     * Returns an edge that connects v1 to v2.
     * If this edge is not uniquely defined (that is, if the graph contains more than one edge connecting v1 to v2), any of these edges may be returned.
     * The method findEdgeSet(v1, v2) may be used to return all such edges.
     * Returns null if either of the following is true:
     * <ul>
     * <li>The verticies v1 is not connected to v2.
     * <li>Either v1 or v2 are not present in this graph.
     * </ul> 
     * <b>Note</b>: for purposes of this method, v1 is only considered to be connected to v2 via a given <i>directed</i> edge e 
     * if v1 == e.getSource() && v2 == e.getDest() evaluates to true.
     * The verticies v1 and v2 are connected by an undirected edge u if u is incident to both v1 and v2.
     * @param v1 Vertetx one.
     * @param v2 Vertetx two.
     * @return An edge that connects v1 to v2, or null if no such edge exists (or either vertex is not present).
     * @see Hypergraph#findEdgeSet(Object, Object) 
     */
    public GraphEdge findEdge(GraphNode v1, GraphNode v2) {
        if(storage.get(v1) == null || storage.get(v2) == null)
            return null;

        GraphEdge edge = storage.get(v1).get(v2);

        return edge; //default return, remove or change as needed   	

	}

    /**
     * Returns true if vertex and edge are incident to each other.
     * Equivalent to getIncidentEdges(vertex).contains(edge) and to getIncidentVertices(edge).contains(vertex).
     * @param vertex The vertex.
     * @param edge The edge to evaluate.
     * @return True if vertex and edge are incident to each other.
     */
    public boolean isIncident(GraphNode vertex, GraphEdge edge) {
		return getIncidentEdges(vertex).contains(edge); //default return, remove or change as needed
		
	}

    /**
     * Returns the endpoints of edge as a Pair.
     * @param edge The edge whose endpoints are to be returned.
     * @return The endpoints (incident vertices) of edge or null if edge is not present.
     */
    public Pair<GraphNode> getEndpoints(GraphEdge edge) {
        int i = 0;
        GraphNode[] vertices = new GraphNode[2];
        for(GraphNode node : storage.keySet())
        {
            if(isIncident(node, edge))
            {
                vertices[i] = node;
                i++;
            }
            if(i == 2)
                break;
        }

        if(i != 2)
            return null;

        GraphNode v1;
        GraphNode v2;
        if(vertices[0].compareTo(vertices[1]) > 0)
        {
            v1 = vertices[1];
            v2 = vertices[0];
        }
        else
        {
            v1 = vertices[0];
            v2 = vertices[1];
        }
        
        return new Pair<GraphNode>(v1, v2);
        //NOTE: the two end points must be sorted in ascending order of their IDs.		
	}


    /**
     * Returns the collection of edges in this graph which are connected to vertex.
     * @param vertex The vertex whose incident edges are to be returned.
     * @return The collection of edges which are connected to vertex, or null if vertex is not present.
     */
    public Collection<GraphEdge> getIncidentEdges(GraphNode vertex) {
        if(storage.get(vertex) == null)
            return null;
        
        Set310<GraphEdge> set = new Set310<>();
        for(Map310<GraphNode, GraphEdge> map : storage.values())
        {
            for(GraphNode node : map.keySet())
            {
                if(vertex==node)
                    set.add(map.get(node));
            }
        }
        return set;
	}

    /**
     * Adds edge e to this graph such that it connects vertex v1 to v2.
     * Equivalent to addEdge(e, new Pair(v1, v2)).
     * If this graph does not contain v1, v2, or both, the method silently adds the vertices to the graph.
     * If this graph assigns edge types to its edges, the edge type of e will be the default for this graph.
     * @param e The edge to be added.
     * @param v1 The first vertex to be connected.
     * @param v2 The second vertex to be connected.
     * @return True if the add is successful, false otherwise.
     * @see #addEdge(Object, Object, Object, EdgeType)
     */
    public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2) {
        if(v1 == null || v2 == null || e == null || findEdge(v1, v2) != null || getEdges().contains(e) || v1==v2)
            return false;

        addVertex(v1);
        addVertex(v2);

        storage.get(v1).put(v2, e);
        storage.get(v2).put(v1, e);

	    return true;		
	}
    
    /**
     * Adds vertex to this graph.
     * Fails if vertex is null or already in the graph.
     * @param vertex The vertex to add.
     * @return True if the add is successful, and false otherwise.
     * @throws IllegalArgumentException if vertex is null.
     */
    public boolean addVertex(GraphNode vertex) {
        if(vertex == null)
            throw new IllegalArgumentException();
        
		if(storage.containsKey(vertex))
            return false;

        storage.put(vertex, new Map310<>());

		return true;
	}

    /**
     * Removes edge from this graph.
     * Fails if edge is null, or is otherwise not an element of this graph.
     * @param edge The edge to remove.
     * @return True if the removal is successful, false otherwise.
     */
    public boolean removeEdge(GraphEdge edge) {
        if(edge == null || !getEdges().contains(edge))
            return false;
        
        Pair<GraphNode> vertices = getEndpoints(edge);

        storage.get(vertices.getFirst()).remove(vertices.getSecond());
        storage.get(vertices.getSecond()).remove(vertices.getFirst());

	    return true;
	}
    
    /**
     * Removes vertex from this graph.
     * As a side effect, removes any edges e incident to vertex if the removal of vertex would cause e to be incident to an illegal number of vertices.
     * Fails under the following circumstances:
     * <ul>
     * <li>Vertex is not an element of this graph.
     * <li>Vertex is null.
     * </ul>
     * @param vertex The vertex to remove.
     * @return True if the removal is successful, false otherwise.
     */
    public boolean removeVertex(GraphNode vertex) {
        if(vertex == null || !storage.containsKey(vertex))
            return false;
        
        for(GraphEdge edge : getIncidentEdges(vertex))
            removeEdge(edge);
        
        storage.remove(vertex);

	    return true;
	}
		
	
	/**
	 *  Report a set of graph nodes that are reachable from the given vertex. 
	 *  Do not include vertex itself in the set.
	 *  No particular order is required (hence a set).
     * @param vertex The vertex to get the reachables from.
	 *  @return A set of graph nodes that are reachable from vertex, or an empty set if none return null if vertex is not present.
	 */
	public Set310<GraphNode> reachableSet(GraphNode vertex) {
        if(vertex == null || !storage.containsKey(vertex))
            return null;
        Set310<GraphNode> set = new Set310<>();
        getReachables(vertex, set);
        set.remove(vertex);
        return set;
	}

    /**
     * A recursive helper method that gets all the noes that is reachable form node.
     * @param node The node that we get all the reachables from.
     * @param set The set that we add the reachables from.
     */
    private void getReachables(GraphNode node, Set310<GraphNode> set)
    {
        if(set.contains(node))
            return;
        
        set.add(node);
        for(GraphNode neighbor: getNeighbors(node))
        {
            getReachables(neighbor, set);
        }
    }
	
	/**
     * Main method for testing.
     * @param args Arguments.
     */
	public static void main(String[] args) {
		//create a set of nodes and edges to test with
		GraphNode[] nodes = {
			new GraphNode(0), 
			new GraphNode(1), 
			new GraphNode(2), 
			new GraphNode(3), 
			new GraphNode(4), 
			new GraphNode(5), 
			new GraphNode(6), 
			new GraphNode(7), 
			new GraphNode(8), 
			new GraphNode(9)
		};
		
		GraphEdge[] edges = {
			new GraphEdge(0), 
			new GraphEdge(1), 
			new GraphEdge(2),
			new GraphEdge(3), 
			new GraphEdge(4), 
			new GraphEdge(5),
			new GraphEdge(6),
			new GraphEdge(7)
		};
		
		//constructs a graph
		Graph310 graph = new Graph310();
		for(GraphNode n : nodes) {
			graph.addVertex(n);
		}
		
		graph.addEdge(edges[0],nodes[0],nodes[1]);
		graph.addEdge(edges[1],nodes[1],nodes[2]);
		graph.addEdge(edges[2],nodes[3],nodes[6]);
		graph.addEdge(edges[3],nodes[6],nodes[7]);
		graph.addEdge(edges[4],nodes[8],nodes[9]);
		graph.addEdge(edges[5],nodes[9],nodes[0]);
		graph.addEdge(edges[6],nodes[2],nodes[7]);
		graph.addEdge(edges[7],nodes[1],nodes[8]);
		
		
		if(graph.getVertexCount() == 10 && graph.getEdgeCount() == 8) {
			System.out.println("Yay 1");
		}
		
		if(graph.getNeighborCount(nodes[0]) == 2 && graph.getNeighborCount(nodes[1]) == 3) {
			System.out.println("Yay 2");
		}
		
		Pair<GraphNode> ends = graph.getEndpoints(edges[5]);
		if (ends.getFirst().getId()==0 && ends.getSecond().getId()==9) {
			System.out.println("Yay 3");		
		}
		
		//edges incident to nodes[1]
		//expected Ids: 0 1 7 (any order as it is a set)
		
		System.out.print("edges to nodes[1]: ");
		for(GraphEdge e: graph.getIncidentEdges(nodes[1])){
			System.out.print(e.getId()+" ");
		}
		System.out.println();
		

		//print out all vertices
		
		for (GraphNode n: graph.getVertices()){
			System.out.print(+n.getId()+" ");
		}
		System.out.println();
		

		//print out all edges and their endpoints
		
		for (GraphEdge e: graph.getEdges()){
			for (GraphNode n: graph.getIncidentVertices(e)){
				System.out.print(e.getId()+":"+n.getId()+" ");
				
			}
			System.out.println();
		}
		System.out.println();
		
		
		
		Set310<GraphNode> reachable = graph.reachableSet(nodes[1]);
		if (reachable.size()==7){
			System.out.println("Yay 4");				
		}
		
		//detailed reachable set: expect all ids except for 5 and 1
		
		for (GraphNode n: reachable){
			System.out.print(n.getId()+" ");
		}
		System.out.println();
		
		
		//lots more testing here...
		
		
		//If your graph "looks funny" you probably want to check:
		//getVertexCount(), getVertices(), getInEdges(vertex),
		//and getIncidentVertices(incomingEdge) first. These are
		//used by the layout class.
	}
	
    /**
     * Returns a Collection view of the incoming edges incident to vertex in this graph.
     * @param vertex The vertex whose incoming edges are to be returned.
     * @return A Collection view of the incoming edges incident to vertex in this graph.
     */
    public Collection<GraphEdge> getInEdges(GraphNode vertex) {

		//actually, for undirected graph, this is the same as getOutEdges
		//get all edge records
		return getIncidentEdges(vertex);
		    	
	}
    
    /**
     * Returns a Collection view of the outgoing edges incident to vertex in this graph.
     * @param vertex The vertex whose outgoing edges are to be returned.
     * @return A Collection view of the outgoing edges incident to vertex in this graph.
     */
    public Collection<GraphEdge> getOutEdges(GraphNode vertex) {
		return getIncidentEdges(vertex);
	}

    /**
     * Returns the number of incoming edges incident to vertex.
     * Equivalent to getInEdges(vertex).size().
     * @param vertex The vertex whose indegree is to be calculated.
     * @return The number of incoming edges incident to vertex.
     */
    public int inDegree(GraphNode vertex) {
		return degree(vertex);
	}
    
    /**
     * Returns the number of outgoing edges incident to vertex.
     * Equivalent to getOutEdges(vertex).size().
     * @param vertex The vertex whose outdegree is to be calculated.
     * @return The number of outgoing edges incident to vertex.
     */
    public int outDegree(GraphNode vertex) {
		return degree(vertex);
	}


    /**
     * Returns a Collection view of the predecessors of vertex in this graph.
     * A predecessor of vertex is defined as a vertex v which is connected to vertex by an edge e, where e is an outgoing edge of v and an incoming edge of vertex.
     * @param vertex The vertex whose predecessors are to be returned.
     * @return A Collection view of the predecessors of vertex in this graph.
     */
    public Collection<GraphNode> getPredecessors(GraphNode vertex) {
		//undirected graph
		return getNeighbors(vertex);
	}
    
    /**
     * Returns a Collection view of the successors of vertex in this graph.
     * A successor of vertex is defined as a vertex v which is connected to vertex by an edge e, where e is an incoming edge of v and an outgoing edge of vertex.
     * @param vertex The vertex whose predecessors are to be returned.
     * @return A Collection view of the successors of vertex in this graph.
     */
    public Collection<GraphNode> getSuccessors(GraphNode vertex) {
		return getNeighbors(vertex);
	}
    
    /**
     * Returns true if v1 is a predecessor of v2 in this graph.
     * Equivalent to v1.getPredecessors().contains(v2).
     * @param v1 The first vertex to be queried.
     * @param v2 The second vertex to be queried.
     * @return True if v1 is a predecessor of v2, and false otherwise.
     */
    public boolean isPredecessor(GraphNode v1, GraphNode v2) {
		return isNeighbor(v1, v2);
	}
    
    /**
     * Returns true if v1 is a successor of v2 in this graph.
     * Equivalent to v1.getSuccessors().contains(v2).
     * @param v1 The first vertex to be queried.
     * @param v2 The second vertex to be queried.
     * @return True if v1 is a successor of v2, and false otherwise.
     */
    public boolean isSuccessor(GraphNode v1, GraphNode v2) {
		return isNeighbor(v1, v2);
	}
    

    /**
     * If directed_edge is a directed edge in this graph, returns the source; otherwise returns null. 
     * The source of a directed edge d is defined to be the vertex for which d is an outgoing edge.
     * The param directedEdge is guaranteed to be a directed edge if its EdgeType is DIRECTED. 
     * @param directedEdge The edge.
     * @return The source of directed_edge if it is a directed edge in this graph, or null otherwise.
     */
    public GraphNode getSource(GraphEdge directedEdge) {
		return null; //undirected  	
	}

    /**
     * If directed_edge is a directed edge in this graph, returns the destination; otherwise returns null. 
     * The destination of a directed edge d is defined to be the vertex incident to d for which d is an incoming edge.
     * The param directedEdge is guaranteed to be a directed edge if its EdgeType is DIRECTED. 
     * @param directedEdge The edge.
     * @return The destination of directed_edge if it is a directed edge in this graph, or null otherwise.
     */
    public GraphNode getDest(GraphEdge directedEdge) {
		return null; //undirected  	
	}
  


    /**
     * Returns the number of edges incident to vertex.  
     * Special cases of interest:
     * <ul>
     * <li> Incident self-loops are counted once.
     * <li> If there is only one edge that connects this vertex to each of its neighbors (and vice versa), 
     * then the value returned will also be equal to the number of neighbors that this vertex has
     * (that is, the output of getNeighborCount).
     * <li> If the graph is directed, then the value returned will be 
     * the sum of this vertex's indegree (the number of edges whose 
     * destination is this vertex) and its outdegree (the number
     * of edges whose source is this vertex), minus the number of
     * incident self-loops (to avoid double-counting).
     * </ul>
     * Equivalent to getIncidentEdges(vertex).size().
     * @param vertex The vertex whose degree is to be returned.
     * @return The degree of this node.
     * @see Hypergraph#getNeighborCount(Object)
     */
    public int degree(GraphNode vertex) {
		return getNeighborCount(vertex);
	}

    /**
     * Returns true if v1 and v2 share an incident edge.
     * Equivalent to getNeighbors(v1).contains(v2).
     * @param v1 The first vertex to test.
     * @param v2 The second vertex to test.
     * @return True if v1 and v2 share an incident edge.
     */
    public boolean isNeighbor(GraphNode v1, GraphNode v2)
    {
		return (findEdge(v1, v2) != null || findEdge(v2, v1)!=null);
	}
    
    
    /**
     * Returns the collection of vertices in this graph which are connected to edge.
     * Note that for some graph types there are guarantees about the size of this collection
     * (i.e., some graphs contain edges that have exactly two endpoints, which may or may 
     * not be distinct).  Implementations for those graph types may provide alternate methods 
     * that provide more convenient access to the vertices.
     * @param edge The edge whose incident vertices are to be returned.
     * @return  The collection of vertices which are connected to edge, or null if edge is not present.
     */
    public Collection<GraphNode> getIncidentVertices(GraphEdge edge) {
		Pair<GraphNode> p = getEndpoints(edge);
		Set310<GraphNode> ret = new Set310<>();
		ret.add(p.getFirst());
		ret.add(p.getSecond());
		return ret;
	}


    /**
     * Returns true if this graph's edge collection contains edge.
     * Equivalent to getEdges().contains(edge).
     * @param edge The edge whose presence is being queried.
     * @return True iff this graph contains an edge edge.
     */
    public boolean containsEdge(GraphEdge edge) {
		return (getEndpoints(edge) != null);
	}
    
    /**
     * Returns the collection of edges in this graph which are of type edge_type.
     * @param edgeType The type of edges to be returned.
     * @return The collection of edges which are of type edge_type, or null if the graph does not accept edges of this type.
     * @see EdgeType
     */
    public Collection<GraphEdge> getEdges(EdgeType edgeType) {
		if(edgeType == EdgeType.UNDIRECTED) {
			return getEdges();
		}
		return null;
	}

    /**
     * Returns the number of edges of type edge_type in this graph.
     * @param edgeType The type of edge for which the count is to be returned.
     * @return The number of edges of type edge_type in this graph.
     */
    public int getEdgeCount(EdgeType edgeType) {
		if(edgeType == EdgeType.UNDIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}
    
    /**
     * Returns the number of predecessors that vertex has in this graph.
     * Equivalent to vertex.getPredecessors().size().
     * @param vertex The vertex whose predecessor count is to be returned.
     * @return The number of predecessors that vertex has in this graph.
     */
    public int getPredecessorCount(GraphNode vertex) {
		return inDegree(vertex);
	}
    
    /**
     * Returns the number of successors that vertex has in this graph.
     * Equivalent to vertex.getSuccessors().size().
     * @param vertex The vertex whose successor count is to be returned.
     * @return  The number of successors that vertex has in this graph.
     */
    public int getSuccessorCount(GraphNode vertex) {
		return outDegree(vertex);
	}
    
    /**
     * Returns the vertex at the other end of edge from vertex.
     * That is, returns the vertex incident to edge which is not vertex.
     * @param vertex The vertex to be queried.
     * @param edge The edge to be queried.
     * @return The vertex at the other end of edge from vertex.
     */
    public GraphNode getOpposite(GraphNode vertex, GraphEdge edge) {
		Pair<GraphNode> p = getEndpoints(edge);
		if(p.getFirst().equals(vertex)) {
			return p.getSecond();
		}
		else {
			return p.getFirst();
		}
	}
    
    /**
     * Returns all edges that connects v1 to v2.
     * If this edge is not uniquely defined (that is, if the graph contains more than one edge connecting 
     * v1 to v2), any of these edges may be returned. findEdgeSet(v1, v2) may be used to return all such edges.
     * Returns null if v1 is not connected to v2.
     * Returns an empty collection if either v1 or v2 are not present in this graph.
     * <b>Note</b>: for purposes of this method, v1 is only considered to be connected to v2 via a given <i>directed</i> edge d if
     * v1 == d.getSource() && v2 == d.getDest() evaluates to true.
     * Param v1 and v2 are connected by an undirected edge u if u is incident to both v1 and v2.
     * @param v1 Vertex one.
     * @param v2 Vertex two.
     * @return A collection containing all edges that connect v1 to v2, or null if either vertex is not present.
     * @see Hypergraph#findEdge(Object, Object) 
     */
    public Collection<GraphEdge> findEdgeSet(GraphNode v1, GraphNode v2) {
		GraphEdge edge = findEdge(v1, v2);
		if(edge == null) {
			return null;
		}
		
		Set310<GraphEdge> ret = new Set310<>();
		ret.add(edge);
		return ret;
		
	}
	
    /**
     * Returns true if vertex is the source of edge.
     * Equivalent to getSource(edge).equals(vertex).
     * @param vertex The vertex to be queried.
     * @param edge The edge to be queried.
     * @return True iff vertex is the source of edge.
     */
    public boolean isSource(GraphNode vertex, GraphEdge edge) {
    	if (getSource(edge)==null) return false;
		return getSource(edge).equals(vertex);
	}
    
    /**
     * Returns true if vertex is the destination of edge.
     * Equivalent to getDest(edge).equals(vertex).
     * @param vertex The vertex to be queried.
     * @param edge The edge to be queried.
     * @return True iff vertex is the destination of edge.
     */
    public boolean isDest(GraphNode vertex, GraphEdge edge) {
    	if (getSource(edge)==null) return false;
		return getDest(edge).equals(vertex);
	}
    
    /**
     * Adds edge e to this graph such that it connects vertex v1 to v2.
     * Equivalent to addEdge(e, new Pair(v1, v2)).
     * If this graph does not contain v1, v2, or both, implementations may choose to either silently add the vertices to the graph or throw an IllegalArgumentException.
     * If edgeType is not legal for this graph, this method will throw IllegalArgumentException.
     * See Hypergraph.addEdge() for a listing of possible reasons for failure.
     * @param e The edge to be added.
     * @param v1 The first vertex to be connected.
     * @param v2 The second vertex to be connected.
     * @param edgeType The type to be assigned to the edge.
     * @return True if the add is successful, false otherwise.
     * @see Hypergraph#addEdge(Object, Collection)
     * @see #addEdge(Object, Object, Object)
     */
    public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2, EdgeType edgeType) {
		//NOTE: Only directed edges allowed
		
		if(edgeType == EdgeType.DIRECTED) {
			throw new IllegalArgumentException();
		}
		
		return addEdge(e, v1, v2);
	}
    
    /**
     * Adds edge to this graph.
     * Fails under the following circumstances:
     * <ul>
     * <li>Edge is already an element of the graph 
     * <li>Either edge or vertices is null
     * <li>Vertices has the wrong number of vertices for the graph type
     * <li>Vertices are already connected by another edge in this graph, and this graph does not accept parallel edges
     * </ul>
     * @param edge The edge.
     * @param vertices The vertex.
     * @return True if the add is successful, and false otherwise.
     * @throws IllegalArgumentException If edge or vertices is null, or if a different vertex set in this graph is already connected by edge, or if vertices are not a legal vertex set for edge 
     */
    @SuppressWarnings("unchecked")
    public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		GraphNode[] vs = (GraphNode[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

    /**
     * Adds edge to this graph with type edge_type.
     * Fails under the following circumstances:
     * <ul>
     * <li>Edge is already an element of the graph 
     * <li>Either edge or vertices is null
     * <li>Vertices has the wrong number of vertices for the graph type
     * <li>Vertices are already connected by another edge in this graph, and this graph does not accept parallel edges
     * <li>Edge_type is not legal for this graph
     * </ul>
     * @param edge The edge.
     * @param vertices The vertex.
     * @param edgeType What type the edge is.
     * @return True if the add is successful, and false otherwise.
     * @throws IllegalArgumentException If edge or vertices is null, or if a different vertex set in this graph is already connected by edge, or if vertices are not a legal vertex set for edge 
     */
    @SuppressWarnings("unchecked")
    public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices, EdgeType edgeType) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		GraphNode[] vs = (GraphNode[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE EXCEPT FOR FIXING JAVADOC
	//********************************************************************************
	
	/**
     * Returns a {@code Factory} that creates an instance of this graph type.
     * @param <V> the vertex type for the graph factory
     * @param <E> the edge type for the graph factory
     * @return A {@code Factory} that creates an instance of this graph type.
     */
     
	public static <V,E> Factory<UndirectedGraph<GraphNode,GraphEdge>> getFactory() { 
		return new Factory<UndirectedGraph<GraphNode,GraphEdge>> () {
			@SuppressWarnings("unchecked")
			public UndirectedGraph<GraphNode,GraphEdge> create() {
				return (UndirectedGraph<GraphNode,GraphEdge>) new Graph310();
			}
		};
	}
    

    /**
     * Returns the edge type of edge in this graph.
     * @param edge The edge.
     * @return The EdgeType of edge, or null if edge has no defined type.
     */
    public EdgeType getEdgeType(GraphEdge edge) {
		return EdgeType.UNDIRECTED;
	}
    
    /**
     * Returns the default edge type for this graph.
     * @return The default edge type for this graph.
     */
    public EdgeType getDefaultEdgeType() {
		return EdgeType.UNDIRECTED;
	}
    
    /**
     * Returns the number of vertices that are incident to edge.
     * For hyperedges, this can be any nonnegative integer; for edges this must be 2 (or 1 if self-loops are permitted). 
     * Equivalent to getIncidentVertices(edge).size().
     * @param edge The edge whose incident vertex count is to be returned.
     * @return The number of vertices that are incident to edge.
     */
    public int getIncidentCount(GraphEdge edge) {
		return 2;
	}
	

}
