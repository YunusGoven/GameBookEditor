package commands;

import console.Console;

/**
 * Classe qui permet de creer une commande pour la quitter
 * 
 * @author Yunus
 *
 */
public class ExitCommand extends Command {

	private final Console console;

	public ExitCommand(final Console console) {
		super(5, "Quitter l'application");
		this.console = console;
	}

	@Override
	public void execute() {
		console.print("Aurevoir");
		console.exit();
	}
}
