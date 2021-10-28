package console;

/**
 * Definit les methodes d'interactions avec l'utilisateur.
 */
public interface Console {
	/**
	 * Affiche un texte mis en forme a l'utilisateur, lit une ligne encodee par
	 * l'utilisateur et la retourne.
	 *
	 * @param fmt un modele de texte. Les regles de description du modele sont
	 *            definies dans <a href=
	 *            "https://docs.oracle.com/javase/10/docs/api/java/util/Formatter.html#syntax">ce
	 *            document</a>.
	 * @param args les arguments utilises pour mettre en forme le texte.
	 * @return args la ligne encodee par l'utilisateur sans les caracteres de sauts de
	 *         lignes.
	 */
	String readLine(String fmt, Object... args);

	/**
	 * Affiche un texte mis en forme e l'utilisateur, lit une ligne encodee par
	 * l'utilisateur et tente de la convertir en int qu'elle retourne. La methode
	 * repete la demande jusqu'a ce que l'utilisateur encode un texte convertible
	 * en int.
	 *
	 * @param fmt un modele de texte. Les regles de description du modele sont
	 *            definies dans <a href=
	 *            "https://docs.oracle.com/javase/10/docs/api/java/util/Formatter.html#syntax">ce
	 *            document</a>.
	 * @param  args les arguments utilises pour mettre en forme le texte.
	 * @return l'entier encode par l'utilisateur.
	 */
	int readInteger(String fmt, Object... args);

	/**
	 * Affiche un texte mis en forme  l'utilisateur et passe a� la ligne suivante.
	 *
	 * @param fmt un modele de texte. Les regles de description du modele sont
	 *            definies dans
	 * @param args les arguments utilises pour mettre en forme le texte.
	 */
	void printLine(String fmt, Object... args);

	/**
	 * Affiche un texte mis en forme a l'utilisateur.
	 *
	 * @param fmt un modele de texte. Les regles de description du modele sont
	 *            definies dans <a href=
	 *            "https://docs.oracle.com/javase/10/docs/api/java/util/Formatter.html#syntax">ce
	 *            document</a>.
	 * @param args  les arguments utilises pour mettre en forme le texte.
	 */
	void print(String fmt, Object... args);

	/**
	 * Passe a la ligne suivante.
	 */
	void printLine();

	/**
	 * Termine l'execution de l'application.
	 */
	void exit();
}
