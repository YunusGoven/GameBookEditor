package model;

import model.impl.Action;

import java.util.Set;

/**
 * Interface d'un paragraphe
 */
public interface IParagraph extends Iterable<Action>{

    /**
     * Methode qui retorune le contenu du paragraphe
     *
     * @return String contenu du paragraphe
     */
    String getContent();
    /**
     * Methode qui permet de modifer le contenu du paragraphe
     *
     * @param content String nouveau contenu
     */
    void setContent(String content);

    /**
     * Methode qui permet de savoir si le paragraphe a des actions
     * @return true si il possede des actions sinon false
     */
    boolean hasAction();

    /**
     * Methode qui retourne le numéro du paragraphe
     * @return int numero du paragraohe
     */
    int getNumber();

    /**
     * Methode qui permet de modifier le numero du paragraphe
     * @param number int nouveau numero
     */
    void setNumber(final int number);

    /**
     * Methode qui permet de changer toutes les actions du paragraphe
     * @param actions Set<Integer> nouvelles collections d'actions
     */
    void setActions(final Set<Action> actions);

    /**
     * Methhide qui retourne la collection d'action
     * @return Set<Action> collection des actions
     */
    Set<Action> getActions();

    /**
     * Methode qui permet de recuperer les informations du paragraohe en cours
     * @return String numero du paragraphe et son contenu en court
     */
    String getInformationInShort();

    /**
     * Methode qui permet d'ajouter une action
     * @param action action a ajouter
     * @return true si la collection des actions ne contient pas une autre action qui pointe vers le meme numero sinon false
     */
    boolean addAction(final Action action);

    /**
     * Methode qui permet de supprimer une action depuis son numero
     * @param key int numero de destination de l'action a supprimer
     */
    void deleteAction(final int key);

    /**
     * Methode qui permet de supprimer une action par rapport a une action
     * @param action action a supprimer
     */
    void removeAction(final IAction action);

    /**
     * Methode qui retourne l'action qui pointe vers le paragraphe key
     * @param key int numero de destionation de l'acction
     * @return IAction,interface d'action
     */
    IAction getThisAction(final int key);

    /**
     * Methode qui retourne une collection qui contient les numeros de destination de tous les actions
     * @return Set<Integer> collection qui contient les numeros de destinnations
     */
    Set<Integer> getNumberOfAllActionPossible();



}
