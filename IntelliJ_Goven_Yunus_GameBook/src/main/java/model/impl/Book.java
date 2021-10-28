package model.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import graph.Graph;
import model.IBook;
import model.IParagraph;

/**
 * Classe Book qui correspont au livre-jeu, implemente l'interface IBook et
 * Seriziable
 * 
 * @author Goven Y
 */
public class Book implements IBook, Serializable {
	private SortedMap<Integer, Paragraph> paragraphs;
	private String title;

	/**
	 * Constructeur du Livre
	 *
	 * @param title nom du livre
	 */
	public Book(final String title) {
		this.title = title.isBlank() ? "Veuillez entrez un titre svp!" : title;
		paragraphs = new TreeMap<>();
	}

	/**
	 * Constructeur du Livre
	 *
	 * @param title      nom du livre
	 * @param paragraphs paragraphes du livre
	 */
	public Book(final String title, final TreeMap<Integer, Paragraph> paragraphs) {
		this.title = title.isBlank() ? "Veuillez entrez un titre svp!" : title;
		this.paragraphs = paragraphs;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(final String title) {
		this.title = title;
	}

	@Override
	public void addParagraph(final Paragraph paragraph) {
		if (paragraph != null) {
			final var numP = paragraph.getNumber();
			if (containsKey(numP)) {
				organizeParagraph(numP, 1);
				paragraphs.put(numP, paragraph);
				organizeAction(numP, +1);
			} else {
				paragraphs.put(numP, paragraph);
			}
		}
	}

	@Override
	public void addParagraph(final int num) {
		final Paragraph paragraph = new Paragraph(num, "TO-DO", new HashSet<>());
		addParagraph(paragraph);
	}

	@Override
	public void deleteParagraph(final int id) throws Exception {
		try {
			paragraphs.remove(id);
			organizeParagraph(id, -1);
			removeActionForAllParagraph(id);
			organizeAction(id, -1);
		} catch (NoSuchElementException e) {
			throw new Exception("L'élément " + id + " n'existe pas ou n'a pas été trouvé");
		}catch (NullPointerException e){
			throw new Exception("L'élément " + id + " n'existe pas ou n'a pas été trouvé");
		}
	}

	@Override
	public int getNumberOfParagraph() {
		return paragraphs.size();
	}

	@Override
	public boolean containsKey(final int id) {
		return paragraphs.containsKey(id);
	}

	@Override
	public Set<Integer> getLastParagraphs() {
		final Set<Integer> lastParagraphes = new HashSet<>();
		for (final var paragraph : this) {
			final int pNum = paragraph.getNumber();
			if (!paragraph.hasAction()) {
				lastParagraphes.add(pNum);
			}
		}
		return lastParagraphes;
	}

	@Override
	public boolean isEmpty() {
		return paragraphs.isEmpty();
	}

	@Override
	public int getLastParagraph() {
		try {
			return paragraphs.lastKey();
		} catch (NoSuchElementException e) {
			return 0;
		}
	}

	@Override
	public IParagraph getThisParagraph(final int key) throws Exception {

		try {
			return paragraphs.get(key);
		} catch (NoSuchElementException e) {
			throw new Exception("L'élément " + key + " n'existe pas ou n'a pas été trouvé");
		}
	}

	@Override
	public List<Integer> getAllNumberOfAction() {

		final Iterator<Paragraph> it = iterator();
		final List<Integer> list = new LinkedList<>();
		while (it.hasNext()) {
			final var p = it.next();
			final var numAllActopn = p.getNumberOfAllActionPossible();
			list.addAll(numAllActopn);
		}
		return list;
	}

	@Override
	public Set<Integer> getAllNumberOfParagraphs() {
		return paragraphs.keySet();
	}

	@Override
	public Iterator<Paragraph> iterator() {
		final var col = paragraphs.values();
		return col.iterator();
	}

	/**
	 * Methode qui permet d'organiser les numero des paragraphe
	 *
	 * @param numero numero de reference
	 * @param val    valeur qui permet de savoir si le paragraphe est supprimer ou
	 *               ajoute
	 */
	private void organizeParagraph(final int numero, final int val) {
		final TreeMap<Integer, Paragraph> paragraphOrganized = new TreeMap<>();
		for (final var paragraph : this) {
			final int numP = paragraph.getNumber();
			if (numero > numP) {
				paragraphOrganized.put(numP, paragraph);
			} else {
				paragraph.setNumber(numP + val);
				paragraphOrganized.put(numP + val, paragraph);
			}
		}
		this.paragraphs = paragraphOrganized;
	}

	/**
	 * Methode qui permet de modifier le numero des actions
	 * 
	 * @param number numero du paragraphe
	 * @param valeur valeur qui permet de savoir si le action est supprimer ou
	 *               ajoute
	 */
	private void organizeAction(final int number, final int valeur) {
		for (final IParagraph paragraph : this) {
			final Set<Action> actions = paragraph.getActions();
			for (final var action : actions) {
				verifyForOneActionToOrganize(paragraph, action, number, valeur);
			}

		}
	}

	/**
	 * Methode qui permet de verifier ou laction doit etre place
	 * 
	 * @param paragraph paragraph
	 * @param action    action du paragraph
	 * @param number    numero du paragraph
	 * @param valeur    valeur qui permet de savoir si le action est supprimer ou
	 *                  ajoute
	 */
	private void verifyForOneActionToOrganize(final IParagraph paragraph, final Action action, final int number,
			final int valeur) {
		final int numAction = action.getDestination();
		if (numAction >= number) {// numAction plus grand que number
			action.setDestination(numAction + valeur);
		} // numAction plus petit que number

	}

	/**
	 * Methode qui permet de supprimer les actions de tout les paragraphe lorsque le
	 * paragraphe est pointe par laction
	 * 
	 * @param key la cle de laction
	 */
	private void removeActionForAllParagraph(final int key) {
		for (final IParagraph paragraph : this) {
			paragraph.deleteAction(key);
		}
	}

	@Override
	public Set<Integer> getNodeAbsent() {
		final Set<Integer> allNumberOfParagraph = getAllNumberOfParagraphs();
		final List<Integer> allNumberOfNumberOfActions = getAllNumberOfAction();
		final Set<Integer> nodeAbsent = new HashSet<>();
		if (allNumberOfNumberOfActions.isEmpty()) {
			nodeAbsent.addAll(allNumberOfParagraph);
		} else {
			for (final var paragraph : allNumberOfParagraph) {
				if (!allNumberOfNumberOfActions.contains(paragraph)) {
					nodeAbsent.add(paragraph);
				}
			}
		}
		nodeAbsent.remove(1);
		return nodeAbsent;
	}

	@Override
	public Set<Integer> getInaccesibleParagraph() throws Exception {
		final var lasts = getLastParagraphs();
		if (lasts.isEmpty()) {
			return new HashSet<>();
		} else {
			final Set<Integer> accesible = lastsParagraphAccessibleFromFirst(lasts);
			if (!accesible.containsAll(lasts)) {
				return determineNotAcces(lasts, accesible);
			} else {
				return new HashSet<>();
			}
		}
	}

	/**
	 * Methode qui determiner les derniers paragraphe accessible depuis le premier
	 * 
	 * @param lasts collection des derniers paragraphe
	 * @return une collection Set qui contient ses paragraphres
	 * @throws Exception Exception qui se declanche dans le cas ou la cle demande
	 *                   pour un paragraphe nexiste pas
	 */
	private Set<Integer> lastsParagraphAccessibleFromFirst(Set<Integer> lasts) throws Exception {
		final var first = getThisParagraph(1);
		final var actionsPos = first.getNumberOfAllActionPossible();
		if (actionsPos.isEmpty()) {
			return lasts;
		} else {
			return parcoursAndAdd(lasts, actionsPos);
		}
	}

	/**
	 * Methode qui retourne un set qui contient les paragraphe non accesible
	 * 
	 * @param latest          derniers paragraphe
	 * @param actionsPossible destination des actions du paragraphe 1
	 * @return collection SET qui contient les paragraphe non accessible
	 * @throws Exception Exception qui se declanche dans le cas ou la cle demande
	 *                   pour un paragraphe nexiste pas
	 */
	private Set<Integer> parcoursAndAdd(final Set<Integer> latest, final Set<Integer> actionsPossible)
			throws Exception {
		final Set<Integer> acessible = new HashSet<>();
		for (final int action : actionsPossible) {
			final int last = parcours(action);
			if (latest.contains(last)) {
				acessible.add(last);
			}
		}
		return acessible;
	}

	/**
	 * Methode qui retorune le numero du prochain ou dernier paragraphe
	 * 
	 * @param numero numero du paragraphe courant
	 * @return en entier
	 * @throws Exception Exception qui se declanche dans le cas ou la cle demande
	 *                   pour un paragraphe nexiste pas
	 */
	private int parcours(final int numero) throws Exception {
		int last = numero;
		final var p = getThisParagraph(numero);
		final Set<Integer> numbersActions = p.getNumberOfAllActionPossible();
		if (!numbersActions.isEmpty()) {
			for (final int action : numbersActions) {
				last = parcours(action);
			}
		}
		return last;
	}

	/**
	 * Methode qui retourne les noeud non accesible
	 * 
	 * @param lasts     collection des paragraphes terminaux
	 * @param accesible collection des paragraphe accessible
	 * @return Set<Integer> collection qui contient les paragraphe non accessible
	 *         depuis le 1er
	 */
	private Set<Integer> determineNotAcces(final Set<Integer> lasts, final Set<Integer> accesible) {
		final Set<Integer> notAcess = new HashSet<>();
		for (final int i : lasts) {
			if (!accesible.contains(i)) {
				notAcess.add(i);
			}
		}
		return notAcess;
	}

	@Override
	public Graph getGraph() throws Exception {
		final Graph graph = new Graph(getNumberOfParagraph());
		if (!isEmpty()) {
			for (final var p : this) {
				final var num = p.getNumber();
				final var numActions = p.getNumberOfAllActionPossible();
				searchNextParagraph(graph, numActions, num);
			}
		}
		return graph;
	}

	private void searchNextParagraph(final Graph graph, final Set<Integer> numActions, final int num) throws Exception {
		if (!numActions.isEmpty()) {
			for (final var action : numActions) {
				graph.addDest(num, action);
				parcoursGraph(graph, action);
			}
		}
	}

	private void parcoursGraph(final Graph graph, final int action) throws Exception {
		final var paragraph = getThisParagraph(action);
		final Set<Integer> numbersActions = paragraph.getNumberOfAllActionPossible();
		if (!numbersActions.isEmpty()) {
			iterateAction(graph, numbersActions, action);
		}
	}

	private void iterateAction(final Graph graph, final Set<Integer> numbersActions, final int actionNum)
			throws Exception {
		for (final int action : numbersActions) {
			graph.addDest(actionNum, action);
			parcoursGraph(graph, action);
		}
	}

}
