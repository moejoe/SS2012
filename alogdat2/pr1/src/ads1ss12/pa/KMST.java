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
//		HashSet<Edge> sol = getHSolution(numNodes,numEdges,this.graph,k);
//		setSolution(calculateWeight(sol),sol);
		
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
		//upperBound = getSolution().getUpperBound();
		curWeight = 0;
		//_recAlgorithm( this.graph );
		Edge clonedArrayList[] = new Edge[this.graph.size()];
		this.graph.toArray(clonedArrayList);
		int bestCase = 0;
		for(int i=0; i < k -1; i++){
			bestCase += clonedArrayList[i].weight;
		}
		upperBound = 0; 
		for(int i = 1 ; i < k ; i++){
			upperBound += clonedArrayList[graph.size()-i].weight;
		}
//		Main.printDebug("UpperBound starts at " + upperBound );
		_recAlgorithm(clonedArrayList,this.graph.size(),bestCase);	
//		Main.printDebug("Done after " + recursion + " iterations");
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
	
	private void _recAlgorithm( Edge[] canditates, int candidateCount, int bestCase ){
		recursion++;
		int spaceLeft = k - ( 1 + tree.size() ); 
		
		int level = tree.size();
		if( level == this.k-1 ){
			HashSet<Edge> solution = new HashSet<Edge>(tree);
			setSolution(calculateWeight(solution),solution);
			upperBound = getSolution().getUpperBound();
			return;
		}
		
		for( int i = 0; i < candidateCount; i++ ){
			Edge curCanditate = canditates[i];
			
 			if( level == 0 || ( connectedNodes[curCanditate.node1] > 0 && connectedNodes[curCanditate.node2] == 0 )
					|| ( connectedNodes[curCanditate.node1] == 0 && connectedNodes[curCanditate.node2] > 0 ) ) {
				
				connectedNodes[curCanditate.node1]++;
				connectedNodes[curCanditate.node2]++;
				
				tree.add(curCanditate);
				curWeight += curCanditate.weight;
				
				System.arraycopy(canditates, i+1, canditates, i, candidateCount-i-1); 
				candidateCount--;
				i--;
				
				Edge clonedArrayList[] = new Edge[candidateCount];
				System.arraycopy(canditates, 0, clonedArrayList, 0, candidateCount ); //clone Array
				_recAlgorithm(clonedArrayList,candidateCount,bestCase);
				
				tree.remove(curCanditate);
				curWeight -= curCanditate.weight;
				connectedNodes[curCanditate.node1]--;
				connectedNodes[curCanditate.node2]--;
				
				bestCase -= curCanditate.weight;
				if( i + spaceLeft > candidateCount ) return;
				if( i < spaceLeft -1 ){
					bestCase += canditates[spaceLeft - 1].weight;
				}
				else{
					bestCase += canditates[i+1].weight;
				}
				if( bestCase >= upperBound ) return;
			}
			else{
				if( connectedNodes[curCanditate.node1] > 0 && connectedNodes[curCanditate.node2] > 0 ) {
					System.arraycopy(canditates, i+1, canditates, i, candidateCount-i-1); 
					candidateCount--;
					i--;
					bestCase -= curCanditate.weight;
					if( i + spaceLeft > candidateCount ) return;
					if( i < spaceLeft -1 ){
						bestCase += canditates[spaceLeft - 1].weight;
					}
					else{
						bestCase += canditates[i+1].weight;
					}
					if( bestCase >= upperBound ) return;
				}
				else{
					if( i >= spaceLeft - 1 && i+1 < candidateCount ){
						bestCase -= curCanditate.weight;
						bestCase += canditates[i+1].weight;
					}
					if( bestCase >= upperBound ) return;
				}
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
				return false;
			}
		}
		
		return true;
	}


		

}
