package commands;

import commands.modify.AddParagraph;
import commands.modify.DeleteParagraphe;
import commands.modify.ModifyParagraph;
import console.Console;
import model.impl.Book;
import storage.IManager;

/**
 * Classe qui permet de creer une commande de modification d'un livre
 * 
 * @author Yunus
 *
 */
public class ModifyCommand extends Command {
	private final Console console;
	private final Book book;
	private final IManager storage;

	/**
	 * Constructeur de la commande
	 * 
	 * @param console console
	 * @param book livre
	 * @param storage classe de sauvegarde
	 */
	public ModifyCommand(final Console console, final Book book, final IManager storage) {
		super(2, "Modifier Livre");
		this.console = console;
		this.book = book;
		this.storage = storage;
	}

	@Override
	public void execute() throws Exception {
		if (!"Veuillez entrez un titre svp!".equals(book.getTitle())) {
			int choix = 0;
			while (choix != 5) {
				final String book = ShowBookConsole.showBook(this.book);
				console.printLine(book);
				choix = showSousMenu();
				switchMenu(choix);
			}
		} else {
			console.printLine("Veuillez d'abord creer un livre merci");
		}

	}

	private int showSousMenu() {
		int choix = 0;
		while (choix < 1 || choix > 5) {
			choix = console.readInteger("1. Modifier le titre du livre\n" + "2. Ajouter un nouveau paragraphe\n"
					+ "3. Modifier un paragraphe existant\n" + "4. Supprimer un paragraphe\n"
					+ "5. Arreter la modification\n" + "Votre choix : ");
		}
		return choix;
	}

	/**
	 * Methode qui permet d'effectuer la sous commande choisie
	 * 
	 * @param choix numero de la sous commande
	 */
	private void switchMenu(final int choix) throws Exception {
		final int lastParagraph = book.getLastParagraph();
		switch (choix) {
		case 1:
			final String bookname = book.getTitle();
			setBookName(bookname);
			break;
		case 2:
			final Command add = new AddParagraph(book, lastParagraph, console);
			add.execute();

			break;
		case 3:
			final Command mod = new ModifyParagraph(console, book);
			if (lastParagraph != 0)
				mod.execute();
			else
				console.printLine("Erreur");
			break;
		case 4:
			final Command del = new DeleteParagraphe(console, book);
			del.execute();
			break;
		default:
			break;
		}
		storage.save(book,"console");
	}

	/**
	 * Methode qui permet de modifier le nom du livre
	 * 
	 * @param name nom du livre
	 */
	private void setBookName(final String name) {
		final String newName = console.readLine(name);
		if (!newName.isBlank()) {
			book.setTitle(newName);
		}
	}

}
