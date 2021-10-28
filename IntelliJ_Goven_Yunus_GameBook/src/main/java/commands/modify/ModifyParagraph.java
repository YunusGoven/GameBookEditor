package commands.modify;

import java.util.HashSet;
import java.util.Set;

import commands.Command;
import console.Console;
import model.IParagraph;
import model.impl.Book;
import model.impl.Action;
import model.impl.Paragraph;
import commands.ShowBookConsole;

/**
 * Classe qui permet de modifier un paragraphe
 * 
 * @author Yunus
 *
 */
public class ModifyParagraph extends Command {
	private final Book book;
	private final Console console;

	/**
	 * Constructeur de modification
	 * 
	 * @param console console
	 * @param book livre
	 */
	public ModifyParagraph(final Console console, final Book book) {
		super(3, "Modifier un paragraphe existant");
		this.book = book;
		this.console = console;
	}

	@Override
	public void execute() throws Exception {
		int numParaMod;
		do {
			numParaMod = console.readInteger("Numero du paragraphe a modifier (0 pour retour au menu)");
			if (numParaMod == 0)
				return;
		} while (!book.containsKey(numParaMod));

		final IParagraph p = book.getThisParagraph(numParaMod);
		changeName(p);
		final boolean modify = modifyAction(p);
		if (modify)
			addNewAction(p);

		final String book = ShowBookConsole.showBook(this.book);
		console.printLine(book);

	}

	/**
	 * Methode qui permet d'ajouter une action a la modification
	 *
	 * @param p paragraphe dans lequel il faut ajouter
	 */
	private void addNewAction(final IParagraph p) {
		final String change = console.readLine("Souhaitez-vous ajouter une action possible (o/n) ? ");
		if (!change.isBlank() && change.charAt(0) == 'o') {
			String textDeAction = ".";
			int num;
			while (!textDeAction.isBlank()) {
				textDeAction = console.readLine("Texte de laction possible (Enter si aucune) : ");
				if (!textDeAction.isBlank()) {
					num = console.readInteger("Numero du paragraphe de destination");
					final Action a = new Action(textDeAction,num);
					p.addAction(a);
					addParagraphe(num);
				}
			}
		}
	}

	/**
	 * Methode qui permet d'ajouter un paragraphe vide
	 * 
	 * @param num numero du paragraphe
	 */
	private void addParagraphe(final int num) {
		if (!book.containsKey(num))
			book.addParagraph(num);
	}

	/**
	 * Methode qui permet de modifer les actions d'un paragraphe
	 * 
	 * @param p paragraphe pour lequel il faut modifier les action
	 * @return true si les actions ont ete modifie
	 */
	private boolean modifyAction(final IParagraph p) {
		final String change = console.readLine("Souhaitez-vous modifier les actions possibles (o/n)? ");

		final var b = change.charAt(0) == 'o' || change.charAt(0) == 'O';
		if (!change.isBlank() && b) {
			final Set<Action> actionAssocie = p.getActions();
			final Set<Action> actionModifie = new HashSet<>();
			for (final Action a : actionAssocie) {
				final String text = a.getContent();
				final int num = a.getDestination();
				console.printLine("%s %d", text, num);
				final boolean sup = supprimer();
				if (!sup) {
					final Action act = modifyTextAndNum(a, num);
					actionModifie.add(act);
				}
			}
			p.setActions(actionModifie);
		}
		return b;
	}

	/**
	 * Methoed qui permet de modifier le texte et le numero de l'action
	 * 
	 * @param a      action a modifier
	 * @param number numero de laction
	 * @return une Action modifie
	 */
	private Action modifyTextAndNum(final Action a, int number) {
		String text = console.readLine("Nouveau texte (Tapez enter pour pas changer)");
		if (!text.isBlank())
			a.setContent(text);
		do {
			text = console.readLine("Nouveau numero (num initiale %d)(Tapez enter pour pas changer)", number);
		} while ("^[+-]?\\d*$".matches(text));
		if (!text.isBlank()) {
			int num = Integer.parseInt(text);
			if (book.containsKey(num) && num != 1)
				a.setDestination(num);
			else
				console.printLine("Le paragraphe n'existe pas");
		}
		return a;
	}

	/**
	 * Methode qui permet de supprimer une action
	 *
	 * @return true si laction a ete supprimer
	 */
	private boolean supprimer() {
		boolean sup = false;
		String change = console.readLine("Supprimer (o/n) ?");
		if (!change.isBlank() && change.charAt(0) == 'o') {
			change = console.readLine("Etes-vous sur ? ");
			if (!change.isBlank() && change.charAt(0) == 'o') {
				console.printLine("Action supprimee");
				sup = true;
			}
		}

		return sup;
	}

	/**
	 * Methode qui permet de changer le contenu du paragraphe
	 *
	 * @param p paragraphe qui faut modifier
	 */
	private void changeName(final IParagraph p) {

		final String nom = p.getContent();
		final String modifye = console.readLine(
				"Texte actuel du paragraphe %d : %s\nEncodez le nouveau texte ou Enter pour le conserver:",
				p.getNumber(), nom);
		if (!modifye.isBlank())
			p.setContent(modifye);
	}

}
