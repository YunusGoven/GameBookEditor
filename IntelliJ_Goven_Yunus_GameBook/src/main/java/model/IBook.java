package model;

import graph.Graph;
import model.impl.Paragraph;

import java.util.List;
import java.util.Set;

/**
 * Interface de livre-jeu
 */
public interface IBook extends Iterable<Paragraph> {

    /**
     * Methode qui permet de supprimer un paragraphe
     * @param key numero du paragraphe a supprimre
     * @throws Exception qui se lance si le livre ne contient pas le paragrahe
     */
    void deleteParagraph(final int key) throws Exception;

    /**
     * Methode qui retourne le titre du livre
     * @return String titre du livre
     */
    String getTitle();

    /**
     * Methode qui modifie le titre du livre
     * @param title String nouveau titre
     */
    void setTitle(final String title);

    /**
     * Methode qui permet d'ajouter un paragraphe
     * @param paragraph Paragraph a ajouter
     */
    void addParagraph(final Paragraph paragraph);

    /**
     * Methode qui permet d'ajouter un paragraohe vide
     * @param num numero du paragraphe
     */
    void addParagraph(final int num);

    /**
     * Methode qui retorune le nombre de paragraphe
     * @return int nombre de paragraphe
     */
    int getNumberOfParagraph();

    /**
     * Methode qui permet de savoir si le livre-jeu contien un paragraphe par son numero
     * @param id int numero du paragraphe a chercher
     * @return true si le lvre-jeu contient un paragraphe dans l'indice id sinon false
     */
    boolean containsKey(final int id);

    /**
     * Methode qui retourne les paragraphes finaux du livre-jeu
     * @return Set<Integer> collection qui contient les paragraphe finaux
     */
    Set<Integer> getLastParagraphs();

    /**
     * Methode qui permet de savoir si le livre-jeu est vide cad ne contient au paragraphe
     * @return true si le livre est vide sinon false
     */
    boolean isEmpty();

    /**
     * Methode qui retourne le dernier paragraphe qui existe dans le livre
     * @return int dernier paragraphe
     */
    int getLastParagraph();

    /**
     * Methode qui retourne un paragraphe selon son numero <id>>
     * @param id numero du paragraphe que vous souhaitez recuperer
     * @return IParagraphe paragraphe qui contient le numero id
     * @throws Exception qui se lance si le livre-jeu ne contient pas le paragraphe
     */
    IParagraph getThisParagraph(final int id) throws Exception;

    /**
     * Methode qui retourne une liste qui contient toute les actions qui existe dans le livre-jeu
     * @return List<Integer> list qui contient des entiers des numero de destionations des toutes les actions
     */
    List<Integer> getAllNumberOfAction();

    /**
     * Methode qui retourne une collection qui contient tout les numero des paragraphes
     * @return Set<Integer> qui contient le numero des paragraphes
     */
    Set<Integer> getAllNumberOfParagraphs();

    /**
     * Methode qui retourne les noeud absent
     * @return Set<Integer> collection qui contient les noeud absent
     */
    Set<Integer> getNodeAbsent();

    /**
     * Methode qui retourne les numeros des paragraphes non accessible depuis le 1er paragraohe
     * @return Set<Integer> qui contient les numeros des paragraphe
     * @throws Exception qui se lance si le livre ne contient pas le 1er paragraphe
     */
    Set<Integer> getInaccesibleParagraph() throws Exception;

    /**
     * Methode qui retourne une instance de Graph qui contient le graph des different chemin possible
     * @return Graph
     * @throws Exception qui se lance si le livre-jeu ne contient pas un paragraphe demande
     */
    Graph getGraph() throws Exception;

}
