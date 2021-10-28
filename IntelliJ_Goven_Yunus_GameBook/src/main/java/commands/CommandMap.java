package commands;

import java.util.*;

/**
 * Classe qui contient toute les commandes
 * 
 * @author Yunus
 *
 */
public class CommandMap implements Iterable<Command> {
	private final Map<Integer, Command> commandMap;
	/**
	 * Constructeur qui permet de creer un collection de commandes precondition :
	 * collection ne dois pas etre null
	 *
	 * @param collection collection qui contient les differentes commande Ã 
	 *                   ajouter dans la map
	 */
	public CommandMap(final Collection<Command> collection) {
		commandMap = new HashMap<>();
		if (collection != null) {
			for (final Command command : collection) {
				commandMap.put(command.getNumero(), command);
			}
		}



	}

	/**
	 * 
	 * Methode permettant de retrouver une commande depuis son nom precondition :
	 * key ne doit pas etre null post condition : un objet de la classe Command doit
	 * etre retourne si key est equivalent Ã  l'une des commandes de la map ou
	 * elle doit renvoyÃ© null si elle n'existe pas
	 *
	 * @param key nom de la commande a chercher
	 * @return toReturn la commande si elle existe dans la collection de commande
	 *         sinon null dans le cas oÃ¹ elle n'existe pas
	 */
	public Command get(final int key) {
		final Command toReturn;
		toReturn = commandMap.get(key);
		return toReturn;
	}

	/**
	 * Methode qui permet de creer un iterateur qui contients les Commands de classe
	 */
	@Override
	public Iterator<Command> iterator() {
		Collection<Command> collection = commandMap.values();
		return collection.iterator();
	}

}
