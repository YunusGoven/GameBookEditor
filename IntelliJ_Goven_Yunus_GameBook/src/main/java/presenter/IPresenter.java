package presenter;

import javafx.collections.ObservableList;
import view.IView;

import java.io.File;

/**
 * Interface de presentation
 * 
 * @author GOVEN Y.
 */
public interface IPresenter {

	/**
	 * Methode qui permet de changer le controlleur de la vue
	 * 
	 * @param view nouveeau controlleur de vue
	 */
	void setView(final IView view);

	/**
	 * Methode qui retorurne une liste observable de String
	 * 
	 * @return la list contient les informations des paragraphes du livre-jeu
	 */
	ObservableList<String> getObservableList();

	/**
	 * Methode qui permet de determiner le paragraphe lorsque l'utilisateur le
	 * choisit dans la vue
	 * 
	 * @param key numero du paragraphe
	 * @throws Exception qui se lance si le livre-jeu ne contient pas le paragrahe
	 */
	void setParagraphChoice(final int key) throws Exception;

	/**
	 * Methode qui permet de supprimer le paragraphe qui a ete choisi au prealable
	 * dans le livre et dans la vue
	 * 
	 * @throws Exception qui se lance si la paragraphe n'existe pas
	 */
	void removeParagraph() throws Exception;

	/**
	 * Methode qui permet d'ajouter un nouveau paragraphe
	 * 
	 * @param num     numero du paragraphe
	 * @param content contenu du paragraphe
	 */
	void addParagraph(int num, final String content);

	/**
	 * Methode qui permet de modifier et de sauvegarder le contenu du paragraphe
	 * 
	 * @param newContent nouveau contenu
	 */
	void saveParagraphContent(final String newContent);

	/**
	 * Methode qui permet d'jaouter une action au paragrapghe selectionne au
	 * prealable
	 * 
	 * @param destination numero de destionation du paragraphe
	 * @param content     contenu de laction
	 */
	void addAction(final int destination, final String content);

	/**
	 * Methode qui permet de supprimer une action d'un paragaphe et de la vue
	 * 
	 * @param destination numero de destiantion de laction
	 */
	void deleteAction(final int destination);

	/**
	 * Methode qui peremt de modifier les information d'une action
	 * 
	 * @param newDestination nouvelle destination de laction
	 * @param newContent     nouveau contenu de laction
	 * @param destination    destination initiale de laction
	 */
	void modifyAction(final int newDestination, final String newContent, final int destination);

	/**
	 * Methode qui retorune le numero du paragraphe selectioné
	 * 
	 * @return numero du paragphe selectionné
	 */
	int getNumParagraph();

	/**
	 * Methode qui permet de modifier le titre du livre depuis la vue
	 * 
	 * @param title nouveau titre
	 */
	void changeTitle(final String title);

	/**
	 * Methode qui permet de recuperer le titre du livre
	 * 
	 * @return titre du livre
	 */
	String getTitle();

	/**
	 * Methode qui permet de sauvegarder le livre-jeu
	 * 
	 * @param path chemin ou l'utilisateur y veux enregister le fichier
	 */
	void saveBook(final String path);

	/**
	 * Methode qui permet de recuperer un livre-jeu depuis un ficier
	 * 
	 * @param file fichier qui contient le livre-jeu
	 */
	void loadBook(final File file);

	/**
	 * Methode qui permet de creer un nouveau livre
	 */
	void newBook();

	/**
	 * Methode qui permet de verifier un livre-jeu
	 * 
	 * @throws Exception qui se lance si un paragraphe demande n'existe pas
	 */
	void verifyBook() throws Exception;

	/**
	 * Methode qui permet d'afficher le graphe
	 * 
	 * @throws Exception qui se lance si un paragrapphe demandé n'existe pas
	 */
	void showGraph() throws Exception;
}