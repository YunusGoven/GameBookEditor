package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe Graph
 * 
 * @author GOVEN Y.
 */
public class Graph {

	private final Map<Integer, HashSet<Integer>> list;

	/**
	 * Constructeur qui instancie une graphe
	 * 
	 * @param val nombre de noued
	 */
	public Graph(final int val) {
		list = new HashMap<>();
		for (int i = 1; i <= val; ++i) {
			list.put(i, new HashSet<>());
		}
	}

	/**
	 * Methode qui permet d'ajouter un noeud a un noued
	 * 
	 * @param src  noeud source
	 * @param dest noued destination
	 */
	public void addDest(final int src, final int dest) {
		final var listChoisi = list.get(src);
		listChoisi.add(dest);
	}

	/**
	 * Methode qui retourne les noued d'un noued
	 * 
	 * @param src noeud source
	 * @return Collection d'entier qui contient les noued voisin
	 */
	public Set<Integer> getNoeud(final int src) {
		return list.get(src);
	}

	/**
	 * Methode qui permet de savoir si le graphe est vide
	 * 
	 * @return true si le graphe est vide sinon false
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
}
