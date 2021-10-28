package commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import console.Console;
import model.IParagraph;
import model.impl.Book;

/**
 * Classe qui permet de creeer une commande qui permet de verifier un livre
 * 
 * @author Yunus
 *
 */
public class VerificationLivre extends Command {

	private final Console console;
	private final Book book;

	/**
	 * Constructeur d'une commande pour verifier un livre
	 * 
	 * @param console console
	 * @param book livre
	 */
	public VerificationLivre(final Console console, final Book book) {
		super(3, "Verifier le livre");
		this.book = book;
		this.console = console;
	}

	@Override
	public void execute() throws Exception {
		final Set<Integer> allNumParagraph = book.getAllNumberOfParagraphs();
		final List<Integer> allNumOfActions = book.getAllNumberOfAction();
		final Set<Integer> nodeAbsent = searchAbsentNode(allNumParagraph, allNumOfActions);
		final Set<Integer> latest = book.getLastParagraphs();

		console.print("Noeud absents de toute action : ");
		showAbsentNode(nodeAbsent);
		console.print("Noeuds terminaux inaccessibles a partir du debut :");
		showInaccesibleParagraphe(latest);

	}

	private void showInaccesibleParagraphe(final Set<Integer>lastParagraphs) throws Exception {
		if(lastParagraphs.isEmpty()){
			console.printLine("Aucun noued");
		}else {
			Set<Integer> accesible = lastsParagraphAccessibleFromFirst(lastParagraphs);
			if(!accesible.containsAll(lastParagraphs)){
				for (int i : lastParagraphs) {
					if (!accesible.contains(i)) {
						console.print(i + " ");
					}
				}
				console.printLine();
			}else{
				console.printLine("aucun noued");
			}
		}
	}

	/**
	 * Methode qui permet d'afficher les noeud absents
	 * 
	 * @param nodeAbsent collection de noed absent
	 */
	private void showAbsentNode(final Set<Integer> nodeAbsent) {
		if (nodeAbsent.isEmpty()) {
			console.printLine("Aucun noued");
		} else {
			final Object[] tabNoed = nodeAbsent.toArray();
			for (int i = 1; i < tabNoed.length - 1; i++) {
				console.print("%d, ", tabNoed[i]);
			}
			console.printLine("%d", tabNoed[tabNoed.length - 1]);
		}
	}

	/**
	 * Methode qui permet de chercher les noed absent
	 * 
	 * @param allNumberOfParagraph       collection des numero de tous les
	 *                                   paragraphe
	 * @param allNumberOfNumberOfActions collection des nuemro de toute les actions
	 * @return collection de noed absent
	 */
	private Set<Integer> searchAbsentNode(final Set<Integer> allNumberOfParagraph,
			final List<Integer> allNumberOfNumberOfActions) {

		final Set<Integer> nodeAbsent = new HashSet<>();

		for (final var paragraph : allNumberOfParagraph) {
			if (!allNumberOfNumberOfActions.contains(paragraph)) {
				nodeAbsent.add(paragraph);
			}
		}
		nodeAbsent.remove(1);
		return nodeAbsent;
	}

	private Set<Integer> lastsParagraphAccessibleFromFirst(Set<Integer> latest) throws Exception {
		IParagraph first = book.getThisParagraph(1);
		Set<Integer> accesible = new HashSet<>();
		Set<Integer>actionsPossible = first.getNumberOfAllActionPossible();
		if(actionsPossible.isEmpty()){
			return  latest;
		}else{
			for(int action : actionsPossible){
				int last = parcours(action);
				if(latest.contains(last)){
					accesible.add(last);
				}
			}
		}


		return accesible;
	}

	private int parcours(int numero) throws Exception {
		int last=numero;
		IParagraph p = book.getThisParagraph(numero);
		Set<Integer> numbersActions = p.getNumberOfAllActionPossible();
		if(!numbersActions.isEmpty()){
			for(int action : numbersActions){
				last = parcours(action);
			}
		}
		return last;
	}
}
