package model;

/**
 * Interface pour une action
 */
public interface IAction {

    /**
     * Methode qui retourne le contenu de l'action
     * @return String contenu de l'action
     */
    String getContent();

    /**
     * Methode qui permet de modifier le contenu d'une action
     * @param content nouveau contenu
     */
    void setContent(final String content);

    /**
     * Methode qui retourne le numero de destination de l'action
     * @return int numero de destination
     */
    int getDestination();

    /**
     * Methode qui permet de modifier le numero de destination de l'action
     * @param destination int nouveau numero de destination
     */
    void setDestination(final int destination);
}
