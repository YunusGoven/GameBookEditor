package storage;

import model.impl.Book;

/**
 * Interface Manager qui permet de fournier les methode pour sauvegarder un livre et charger  un livre
 * @author GOVEN Y
 */
public interface IManager {
    /**
     * Methode qui permet de charger un livre
     * @param path chemin du fichier qui contient le livre-jeu
     * @return un instance de Book si le fichier en contient sinon null
     */
    Book load(final String path);

    /**
     * Methode qui permet de sauver un livre jeu
     * @param book livre-jeu a sauvegarder
     * @param path chemin du fcihier ou l'utilisateur veux enregister son livre-jeu
     * @return true si le le livre-jeu a ete sauvegarder sinon false
     */
    boolean save(final Book book, final String path);
}
