package commands.modify;

import java.util.HashSet;

import commands.Command;
import console.Console;
import model.impl.Action;
import model.impl.Book;
import model.impl.Paragraph;

/**
 * Classe qui permet d'ajouter un paragraphe
 * 
 * @author Yunus
 *
 */
public class AddParagraph extends Command {

	private final Book book;
	private final int lastParagraph;
	private final Console console;

	/**
	 * Constructeur
	 * 
	 * @param book         livre
	 * @param lastParagraph dernier paragraphe
	 * @param console console
	 */
	public AddParagraph(final Book book, final int lastParagraph, final Console console) {
		super(2, "Ajouter un paragraphe");
		this.lastParagraph = lastParagraph;
		this.book = book;
		this.console = console;
	}

	/**
	 * Methode qui permet de recuper une chaine de caractere qui contient un entier
	 * 
	 * @return un entier ou un texte vide
	 */
	private String getNumString() {
		String numString;
		do {
			numString = console.readLine("Numero du nouveau paragraphe");
		} while ("^[+-]?\\d*$".matches(numString));
		return numString;
	}

	@Override
	public void execute() {
		final String numString = getNumString();
		int numParagraph;
		if (numString.isBlank()) {
			numParagraph = lastParagraph + 1;
		} else {
			try {
				numParagraph = Integer.parseInt(numString);
				if(numParagraph>0)
					numParagraph = numParagraph > lastParagraph ? lastParagraph + 1 : numParagraph;
			} catch (NumberFormatException e) {
				console.printLine("Erreur lors de votre saisie");
				numParagraph = -1;
			}
		}
		add(numParagraph);
	}

	/**
	 * Methode qui permet de creer un paragraphe
	 * @param numParagraph numero du paragraph quil faut crer
	 */
	private void add(final int numParagraph){
		String content;
		if (numParagraph <= 0) {
			console.printLine("Le numero du paragraphe ne peut pas etre plus petit ou egal a 0");
		} else {
			do {
				content = console.readLine("Texte du nouveau paragraphe");
			} while (content.isBlank());
			final HashSet<Action> actions = actionForParagraph();
			final Paragraph p = new Paragraph(numParagraph, content, actions);
			book.addParagraph(p);
		}
	}
	/**
	 * Methode qui permet d encoder les actions d'un paragraphe
	 * 
	 * @return une collection d'actions
	 */
	private HashSet<Action> actionForParagraph() {
		final HashSet<Action> action = new HashSet<>();
		String reponse = "o";
		while (!reponse.isBlank()) {
			reponse = console.readLine("Texte de l'action possible (ENTER si aucune");
			if (!reponse.isBlank()) {
				final int numero = console.readInteger("Numero du paragraphe de destination: ");
				final Action a = new Action( reponse, numero);
				action.add(a);
				if (!book.containsKey(numero)) {
					book.addParagraph(numero);
				}
			}
		}
		return action;
	}
}
