package commands;

import console.Console;
import model.impl.Book;
import storage.IManager;

/**
 * Classe qui permet de creer un commande de creation de livre
 * 
 * @author Karde
 *
 */
public class CreationLivreCommand extends Command {

	private final Console console;
	private Book book;
	private final IManager storage;

	/**
	 * Constructeur pour initaliser la commande
	 * 
	 * @param console console
	 * @param book   livre
	 * @param storage classe qui permet de faire des sauvegarde
	 */
	public CreationLivreCommand(final Console console, final Book book, final IManager storage) {
		super(1, "Creer un livre");
		this.console = console;
		this.book = book;
		this.storage = storage;
	}

	@Override
	public void execute() {
		String bookContent = "(Aucun paragraphe)";
		if ("Veuillez entrez un titre svp!".equals(book.getTitle())) {
			setName();
			bookContent = ShowBookConsole.showBook(this.book);
		} else {
			modifyName();
		}
		String bookName = book.getTitle();
		console.printLine("%s\n %s", bookName,bookContent);
		storage.save(book,"console");

	}

	/**
	 * Permet d'initialiser le nom d'un livre
	 */
	public void setName() {
		var nomLivre = "";
		while (nomLivre.isBlank()) {
			nomLivre = console.readLine("Quel serait le nom du livre ?  ");
		}
		this.book.setTitle(nomLivre);
	}

	/**
	 * Methode qui permet de modifier le nom du livre
	 */
	public void modifyName() {
		String nouvea = console.readLine("Voulez-vous creer un nouveau livre et supprimer lancien ? (o/n)");
		if (!nouvea.isBlank() && nouvea.charAt(0) == 'o') {
			nouvea = console.readLine("Quel serait le nom de votre livre ?");
			book = new Book("");
			book.setTitle(nouvea);
		}
	}
}
