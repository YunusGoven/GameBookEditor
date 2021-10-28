package commands.modify;

import commands.Command;
import console.Console;
import model.impl.Book;

/**
 * Classe qui permet de supprimer un paragraphe
 * 
 * @author Yunus
 *
 */
public class DeleteParagraphe extends Command {
	private final Book book;
	private final Console console;

	/**
	 * Constructeur
	 * 
	 * @param console console
	 * @param book livre
	 */
	public DeleteParagraphe(final Console console, final Book book) {
		super(4, "Supprimer un paragraphe");
		this.book = book;
		this.console = console;
	}

	@Override
	public void execute() throws Exception {
		final int num = console.readInteger("Numero du paragraphe a supprimer ?");
		if (!book.containsKey(num))
			return;
		final String confirm = console
				.readLine("Etes-vous sur? Le paragraphe %d va etre definitivement supprime. (o/n)", num);
		final char conf = confirm.charAt(0);
		if (Character.toUpperCase(conf) == 'O') {
			book.deleteParagraph(num);
			console.printLine("Paragraphe %d et toute les branches menant a celui a ete supprimer", num);
		}
	}

}
