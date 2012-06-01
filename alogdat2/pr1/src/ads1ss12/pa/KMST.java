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
	
	int recursion = 0;
	
	private HashSet<Edge> tree;
	private int[] connectedNodes;
	private int k;
	ArrayList<Edge> graph;
	
	int curWeight;
	int upperBound;	
	
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
		
		this.graph = new ArrayList<Edge>(edges);
		java.util.Collections.sort(this.graph);
		
		this.tree = new HashSet<Edge>((int)(numNodes*0.75));
		this.connectedNodes = new int[numNodes];
		this.k = k;
		// initialize connectedNode list
		for( int i=0; i< numNodes; i++ ) { connectedNodes[i] = 0; };
		// Heuristik for an upper Bound
		HashSet<Edge> sol = getHSolution(numNodes,numEdges,this.graph,k);
		setSolution(calculateWeight(sol),sol);	
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
		recursion = 0;	
		upperBound = getSolution().getUpperBound();
		curWeight = 0;
		_recAlgorithm( this.graph );
		
		Main.printDebug("Done after " + recursion + " iterations");
	}
	
	/**
	 * Erstellt eine g&uuml;ltige L&ouml;sung unter Verwendung einer Heuristik.
	 * 
	 * Folgende Heuristiken sind implementiert:
	 * - random ST
	 * 
	 */
	public static HashSet<Edge> getHSolution(Integer numNodes, Integer numEdges, ArrayList<Edge> edges, int k){
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
	

	
	private void _recAlgorithm( ArrayList<Edge> canditates ){
		recursion++;
		int spaceLeft = k - ( 1 + tree.size() ); // Wieviel Kanten haben noch Platz
		
		/* Neue Lösung gefunden */
		if( tree.size() == this.k-1 ){
			HashSet<Edge> solution = new HashSet<Edge>(tree);
			if( setSolution(calculateWeight(solution),solution) ) {
//				Main.printDebug("New Solution is better :)" + getSolution().getUpperBound());
//				Main.printDebug(tree);
			}
			upperBound = getSolution().getUpperBound();
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
				
				bestCase = curWeight;
				for( int j = 0; j < spaceLeft; j++ ) { bestCase += canditates.get(j).weight; };
				
				if( bestCase >= upperBound ) return;
				
				_recAlgorithm(clone);
			}
		}
		return;
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
