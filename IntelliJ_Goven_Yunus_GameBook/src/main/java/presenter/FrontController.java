package presenter;

import java.util.Iterator;

import commands.Command;
import commands.CommandMap;
import console.Console;

/**
 * Classe qui permet de loop sur les differente commande principale
 * 
 * @author Yunus
 *
 */
public class FrontController {
	private final Console console;
	private final CommandMap commandMap;

	/**
	 * Constructeur de la classe
	 *
	 * @param console    object de type Console qui proposera toutes les methodes
	 *                   d'affichage et d'acquisition
	 * @param commandMap object de type CommandMap qui contient les diffÃ©rentes
	 *                   commandes existantes
	 */
	public FrontController(final Console console, final CommandMap commandMap) {
		this.console = console;
		this.commandMap = commandMap;

	}

	/**
	 * Methode permettant d'effectuer une boucle infini pour selectionner une
	 * commande
	 */
	public void loop() throws Exception {
		list();
		Command commande;
		int i = 0;
		while (i==0) {
			final String num = console.readLine("Encodez une commande : ");
			commande = find(num);
			if ("list".equals(num)) {
				list();
			} else if (commande != null) {
				if(commande.getNumero()==5){
					i=1;
				}
				commande.execute();
			} else {
				console.printLine("Cette commande n'existe pas ! ");
			}
		}
	}

	/**
	 * Methode permettant de rechercher une commande prÃ©condition : key ne doit
	 * pas etre null postcondition : la fonction doit renvoyer une commande
	 * existante ou null
	 *
	 * @param key nom de la commande a rechercher
	 * @return la Commande contenant le nom key si elle existe sinon null
	 */
	private Command find(final String key) {
		try {
			final int cmde = Integer.parseInt(key);
			return commandMap.get(cmde);
		} catch (NumberFormatException ignored) {
		}
		return null;
	}

	/**
	 * methode permettant de lister toute les commandes disponible
	 */
	private void list() {
		console.printLine("%s%3s%s\n", "Num", " ", "Nom");
		final Iterator<Command> iterator = commandMap.iterator();
		String name;
		int numero;
		Command commande;
		while (iterator.hasNext()) {
			commande = iterator.next();
			name = commande.getName();
			numero = commande.getNumero();
			console.printLine("%d%5s%s", numero, " ", name);
		}

	}
}
