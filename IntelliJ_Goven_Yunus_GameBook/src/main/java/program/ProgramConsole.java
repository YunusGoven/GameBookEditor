package program;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import commands.Command;
import commands.CommandMap;
import commands.CreationLivreCommand;
import commands.ExitCommand;
import commands.GrapheLivre;
import commands.ModifyCommand;
import commands.VerificationLivre;
import presenter.FrontController;
import console.Console;
import console.UserConsole;
import model.impl.Book;
import storage.BookManager;
import storage.IManager;

/**
 * Classe qui permet de lancer l'application
 * 
 * @author Yunus
 *
 */
public class ProgramConsole {

	/**
	 * Methode main
	 * 
	 * @param args argument
	 */
	public static void main(final String[] args) throws Exception {

		final Console console = new UserConsole();
		final IManager manager = new BookManager();
		Book book = manager.load("console");
		if (book == null)
			book = new Book("");

		final CommandMap commandMap = doMap(console, book, manager);
		final FrontController fontcontroller = new FrontController(console, commandMap);
		fontcontroller.loop();
	}

	/**
	 * Methode qui permet de creer une map de commandes
	 * 
	 * @param console console
	 * @param book   livre
	 * @param manager manager
	 * @return commandMap qui contient toute les commandes
	 */
	private static CommandMap doMap(final Console console, final Book book, final IManager manager) {
		final Command[] commands = { new ExitCommand(console), new CreationLivreCommand(console, book, manager),
				new ModifyCommand(console, book, manager), new VerificationLivre(console, book),
				new GrapheLivre(console, book) };
		final List<Command> list = new LinkedList<>(Arrays.asList(commands));
		return new CommandMap(list);
	}
}
