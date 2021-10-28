package commands;


/**
 * Classe abstraite qui cree une commande
 * 
 * @author Yuus
 *
 */
public abstract class Command {

	private final int numero;
	private final String nom;

	/**
	 * Constructeur permettant d'initialiser une commande precondition : name et
	 * description ne doit pas etre vide et null
	 *
	 *
	 * @param numero numero de la commande
	 * @param nom    nom de la commande
	 */
	public Command(final int numero, final String nom) {
		this.numero = numero;
		this.nom = nom.isEmpty() ? " - " : nom;
	}

	/**
	 * Methode permettant de retourner le nom de la commande postcondition : le nom
	 * de la commande doit etre renvoyé
	 *
	 * @return nom: nom de la commande
	 */
	public String getName() {
		return nom;
	}

	/**
	 * Methode permettant de retourner le numero de la commande postcondition : un
	 * entier doit etre renvoye
	 *
	 * @return numero : numero de la commande
	 */
	public int getNumero() {
		return numero;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Command))
			return false;
		final Command command = (Command) o;
		return numero == command.numero;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(numero);
	}

	/**
	 * Methode abstraite permettant de faire une execution de commande
	 */
	public abstract void execute() throws Exception;
}
