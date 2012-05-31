package ads1ss12.pa;
import java.util.HashSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Random;

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
		this.graph = new TreeSet(edges);
		this.numEdges = numEdges;
		this.numNodes = numNodes;
		this.k = k;
		
		HashSet<Edge> sol = getHSolution(numNodes,numEdges,this.graph,k);
		setSolution(calculateWeight(sol),sol);
		Main.printDebug("Initial Solution: " + getSolution().getUpperBound());
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
			
		simpleBaB(this.graph, null, this.k);
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
	
		TreeSet<Edge> edgeClone = (TreeSet)edges.clone();
		
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

}
