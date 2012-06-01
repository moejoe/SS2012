package ads1ss12.pa;
import java.util.HashSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
//import java.util.TreeMap;
import java.util.TreeSet;
//import java.util.Random;

/**
 * Klasse zum Berechnen eines k-MST mittels Branch-and-Bound. Hier sollen Sie
 * Ihre L&ouml;sung implementieren.
 */
public class KMST extends AbstractKMST {
	
	public static final Integer INFINITE_WEIGHT = Integer.MAX_VALUE;
	TreeSet<Edge> graph; 
	Integer numNodes;
	Integer numEdges;
	int k;
	int counter;
	int cuts=0;
	//KruskalKMST krustal;
	PrimKMST prim;
	
	/**
	 * Der Konstruktor. Hier ist die richtige Stelle f&uuml;r die
	 * Initialisierung Ihrer Datenstrukturen.
	 * 
	 * @param numNodes
	 *          Die Anzahl der Knoten
	 * @param numEdges
	 *          Die Anzahl der Kanten
	 * @param edges
	 *          Die Menge der Kanten
	 * @param k
	 * 			Die Anzahl der Knoten, die Ihr MST haben soll
	 */
	public KMST(Integer numNodes, Integer numEdges, HashSet<Edge> edges, int k) {
		this.graph = new TreeSet<Edge>(edges);
		this.numEdges = numEdges;
		this.numNodes = numNodes;
		this.k = k;
		
		HashSet<Edge> sol = getHSolution(numNodes,numEdges,this.graph,k);
		setSolution(calculateWeight(sol),sol);
		Main.printDebug("Initial Solution: " + getSolution().getUpperBound());
		prim = new PrimKMST(numNodes, k, this);
		//krustal = new KruskalKMST(numNodes,graph,k,this);
	}

	/**
	 * Diese Methode bekommt vom Framework maximal 30 Sekunden Zeit zur
	 * Verf&uuml;gung gestellt um einen g&uuml;ltigen k-MST zu finden.
	 * 
	 * <p>
	 * F&uuml;gen Sie hier Ihre Implementierung des Branch-and-Bound Algorithmus
	 * ein.
	 * </p>
	 */
	@Override
	public void run() {
		counter = 0;
		Main.printDebug("Starting with " + this.numEdges + " Edges - searching for " + k + "-MST");
			
		//simpleBaB(this.graph, null, this.k);
		//krustal.run(this.graph);
		prim.run(this.graph);
		Main.printDebug("Done after " + counter + " iterations");
	}
	
	public void simpleBaB(TreeSet<Edge> canditates, Edge lastMustEdge,int k){
		this.counter++;
		/* calculate best case scenario for this list */
		if( lowerBound(canditates,k) > getSolution().getUpperBound()) {
			this.cuts++;
			return;
		}
		
		/* There is still hope */
		
		
		/* we have a possible solution */
		if( canditates.size() == k - 1 || ( lastMustEdge != null && canditates.tailSet(lastMustEdge).size() == k - 1 )){
			HashSet<Edge> solution = canditates.size() == k - 1 ? new HashSet<Edge>(canditates) : new HashSet<Edge>(canditates.tailSet(lastMustEdge));			
			if( isATree(solution) ){
				if( setSolution(calculateWeight(solution),solution) ) {
					Main.printDebug("New Solution is better :)" + getSolution().getUpperBound());
					Main.printDebug(solution);
				}
			}
			return;
		}
	
		// remove most expensive edge and branch
	
		Edge removedEdge = lastMustEdge == null ? canditates.last() : canditates.lower(lastMustEdge);
		canditates.remove(removedEdge);
		simpleBaB(canditates,lastMustEdge,k);
			
		// force inclusion of the removed edge and start the other branch
		canditates.add(removedEdge);
		simpleBaB(canditates,removedEdge,k);
	}
	
	/**
	 * Erstellt eine g&uuml;ltige L&ouml;sung unter Verwendung einer Heuristik.
	 * 
	 * Folgende Heuristiken sind implementiert:
	 * - random ST
	 * 
	 */
	public static HashSet<Edge> getHSolution(Integer numNodes, Integer numEdges, TreeSet<Edge> edges, int k){
		HashSet<Edge> sol = new HashSet<Edge>( (int) (k * 1.25) );
	
		TreeSet<Edge> edgeClone = new TreeSet<Edge>(edges);
		
		while( sol.size() + 1 < k ){			
			for(Iterator<Edge> it = edgeClone.iterator(); it.hasNext(); ){
				Edge anEdge = it.next();
				if( edgeConnectsToTree( anEdge, sol ) == 1 ) {
					sol.add( anEdge );
					it.remove();
				}
			}
		}
		return sol;
	}

	/**
	 *  Gibt f&uuml;r eine Kante, und einen Graphen die Anzahl der 
	 *  Verbindungsstellen zur&uuml;ck.
	 *  
	 *  Wenn der Graph leer ist, wird 1 zur&uuml;ck gegeben.
	 *  
	 * @param theEdge
	 * @param aTree
	 * @return Anzahl der Nodes, die sowohl in der Kante, als auch dem
	 * 	Graphen existieren.
	 * 
	 */
	
	public static int edgeConnectsToTree(Edge theEdge, HashSet<Edge> aTree){
		if( aTree.isEmpty() ) return 1;
		int connections = 0;
		 for( Edge anEdge : aTree ){
			if(edgesConnect(anEdge,theEdge)) connections++;
		 }
		return connections;
	}
	
	public static boolean edgesConnect(Edge anEdge, Edge anotherEdge ){
		return(anEdge.node1 == anotherEdge.node1 || 
			anEdge.node1 == anotherEdge.node2 ||
			anEdge.node2 == anotherEdge.node1 ||
			anEdge.node2 == anotherEdge.node2);
	}
	
	public static int lowerBound(TreeSet<Edge> edges, int k){
		int sum = 0;
		int i = 1;
		for(Edge anEdge : edges){
			sum += anEdge.weight;
			if(i++ == k) break;
		}
		return sum;
	}
	
	public static int calculateWeight(Set<Edge> edges){
		int weight = 0;
		for( Edge anEdge : edges ){
			weight += anEdge.weight;
		}
		return weight;
	}
	
	public boolean isATree( Set<Edge> edges ){
		
		HashSet<Edge> clonedEdges = new HashSet<Edge>(edges);
		
		HashSet<Edge> tree = new HashSet<Edge> ( (int)( 1.25 * edges.size() ) );
		while( ! clonedEdges.isEmpty() ){
			int extendedTree = 0;
			
			for(Iterator<Edge> it = clonedEdges.iterator(); it.hasNext() ;){
				Edge anEdge = it.next();
				int connCount = edgeConnectsToTree( anEdge, tree );
				if( connCount == 1 ){
					tree.add(anEdge);
					extendedTree = 1;
					it.remove();
				}
				if( connCount == 2 ){
					return false;
				}
			}	
			
			if (extendedTree == 0) {
//				Main.printDebug("Could not extend Tree");
//				Main.printDebug(tree);
//				Main.printDebug(clonedEdges);
				return false;
			}
		}
		
		return true;
	}

	private static class PrimKMST {
		private HashSet<Edge> tree;
		private int[] connectedNodes;
		private KMST container; 
		private int k;
		//int bestCaseWeight;
		int curWeight;
		int upperBound;
		
		public PrimKMST(int numNodes, int k, KMST container ){
			this.tree = new HashSet<Edge>((int)(numNodes*0.75));
			this.connectedNodes = new int[numNodes];
			this.k = k;
			this.container = container;
			for( int i=0; i< numNodes; i++ ) { connectedNodes[i] = 0; };
		}
		
		public void run( TreeSet<Edge> edges ){
			ArrayList<Edge> list = new ArrayList<Edge>(edges);

			curWeight = 0;
			upperBound = container.getSolution().getUpperBound();
			_recAlgorithm( list );
		}
		
		private void _recAlgorithm( ArrayList<Edge> canditates ){
			int spaceLeft = k - ( 1 + tree.size() ); // Wieviel Kanten haben noch Platz
			/* Neue Lösung gefunden */
			if( tree.size() == this.k-1 ){
				HashSet<Edge> solution = new HashSet<Edge>(tree);
				if( container.setSolution(calculateWeight(solution),solution) ) {
					Main.printDebug("New Solution is better :)" + container.getSolution().getUpperBound());
					Main.printDebug(tree);
				}
				upperBound = container.getSolution().getUpperBound();
				return;
			}
			
			for( int i = 0; i < canditates.size(); i++ ){
				Edge curCanditate = canditates.get(i);
				
				int bestCase = curWeight;
				if( i >= spaceLeft ){
					bestCase += curCanditate.weight;
					for( int j = 0; j < spaceLeft - 1; j++ ) { bestCase += canditates.get(j).weight; };				
				}
				else {
					for( int j = 0; j < spaceLeft; j++ ) { bestCase += canditates.get(j).weight; };
				}
				if( bestCase >= upperBound ) return;
				
				if( tree.isEmpty() || ( connectedNodes[curCanditate.node1] > 0 && connectedNodes[curCanditate.node2] == 0 )
						|| ( connectedNodes[curCanditate.node1] == 0 && connectedNodes[curCanditate.node2] > 0 ) ) {
				
					/* Wir haben einen passenden mit min. Knoten gefunden */
					connectedNodes[curCanditate.node1]++;
					connectedNodes[curCanditate.node2]++;
					
					tree.add(curCanditate);
					curWeight += curCanditate.weight;
					
					/* Der neue Kanditat wird danach nicht mehr in der Liste sein
					 * und daher muss der Index angepasst werden.
					 */
					canditates.remove(i--);
					
					ArrayList<Edge> clone = new ArrayList<Edge>(canditates);
					_recAlgorithm(clone);
					
					tree.remove(curCanditate);
					
					curWeight -= curCanditate.weight;
					connectedNodes[curCanditate.node1]--;
					connectedNodes[curCanditate.node2]--;

					if( canditates.size() < spaceLeft ) return;
					
					_recAlgorithm(clone);
				}
				
								
				
			}
			return;
		}
		
	}
	
	/* does not work as expected -.- should have thought it through */
	/*
	private static class KruskalKMST {
		
		private ArrayList<DynamicDisjunctSet> dds;
		private HashSet<Edge> tree;
		private int currentWeight = 0;
		private int k;
		private TreeSet<Edge> edges;
		private KMST container = null;
		
		
		private void addSet(DynamicDisjunctSet aSet){
			dds.add(aSet.node, aSet);
		}
		public DynamicDisjunctSet getSet(int aNode){
			//if(dds == null ) return null;
			return dds.get(aNode);
		}
		public void divide(Edge anEdge){		
			DynamicDisjunctSet set1 = getSet(anEdge.node1);
			DynamicDisjunctSet set2 = getSet(anEdge.node2);

			if(set1.parent == set2) {
				set1.parent = set1;
			}
			if(set2.parent == set1){
				set2.parent = set2;
			}
		}
		
		public KruskalKMST(Integer numNodes, TreeSet<Edge> edges, int k, KMST container) {
			dds = new ArrayList<DynamicDisjunctSet>(numNodes);
			for(int i = 0; i < numNodes; i++ ){
				addSet(new DynamicDisjunctSet(i)); // TODO: Inline function
			}
			tree = new HashSet<Edge>((int)(numNodes/0.75));
			this.edges = edges;
			this.k = k;
			this.container = container;
			
			 
		}
		
		public void run(TreeSet<Edge> edges) {
			// Wir sind fertig 
			if( tree.size() == k-1 ){
				if( container.setSolution(calculateWeight(tree),tree) ) {
					Main.printDebug("New Solution is better :)" + container.getSolution().getUpperBound());
					Main.printDebug(tree);
				}
			}
			// Find next viable edge with lowest cost 			
			for( Iterator<Edge> it = edges.iterator(); it.hasNext();  ){
				Edge e = it.next();
				DynamicDisjunctSet set1 = DynamicDisjunctSet.findRoot(getSet(e.node1));
				DynamicDisjunctSet set2 = DynamicDisjunctSet.findRoot(getSet(e.node2));
				
				if(  set1 != set2 ){
					set1.union(set2);
					tree.add(e);
					currentWeight += e.weight;
					it.remove();
					// Besser werden wir hier nicht mehr
					if( currentWeight + lowerBound(edges,k - tree.size()) > container.getSolution().getUpperBound()) {
						return;
					}				
					this.run((TreeSet)edges.clone());
					
					
					tree.remove(e);
					currentWeight -= e.weight;
					divide(e);
					// Besser werden wir hier nicht mehr
					if( currentWeight + lowerBound(edges,k - tree.size()) > container.getSolution().getUpperBound()) {
						return;
					}
					this.run((TreeSet)edges.clone());
				}
			}
			// Es gibt keine gültigen Lösungen mehr 
			return;
		}
		
		private static class DynamicDisjunctSet{			
			private int node;
			public DynamicDisjunctSet parent;
			
			public DynamicDisjunctSet(int node){
				this.node = node;
				this.parent = this;
			}
			
			public void union( DynamicDisjunctSet other ){
				this.parent = other;
			}
			
			public static DynamicDisjunctSet findRoot(DynamicDisjunctSet leaf){
				DynamicDisjunctSet lastParent = leaf;
				
				while( lastParent != leaf ){
					leaf = leaf.parent;
				}
				return leaf;
			}
		}
		
	}
	*/
}
