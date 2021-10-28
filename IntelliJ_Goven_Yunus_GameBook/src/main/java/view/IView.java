package view;

import graph.Graph;
import javafx.collections.ObservableList;
import model.impl.Action;

import java.util.Set;

/**
 * Interface IView
 * @author GOVEN Y.
 */
public interface IView {
    /**
     *Methode qui permet de mettre a jour la vue des actions et celle qui affiche le contenu du pararagraphe
     * @param pContent contenu du paragraphe
     * @param actions collection des actions du pargraphe
     */
    void updateModifAndAction(final String pContent, final Set<Action> actions);

    /**
     * Methode qui permet de mettre a jour la liste des vue des paragraphe
     * @param paragraphList la liste des paragrapghes
     * @param value valeur qui permet de savoir l'elemnt qui declenche cette methode
     */
    void updateParagraphList(final ObservableList<String> paragraphList, final int value);

    /**
     * Methode qui permet de mettre a jour la vue qui contient les action et d'ajouter une action
     * @param destination numero de destination de laction
     * @param content contenu de laction
     * @param added boolean qui dit dis laction a aete ajouter ou non au paragraphe
     */
    void addActionInView(final int destination, final String content,final  boolean added);

    /**
     * Methode qui permet de mettre a jour la liste des actions dans la vue lorsqu'une action est supprimer
     * @param destination numero de destination a supprimer
     */
    void updateActionList(final int destination);

    /**
     * Methode qui met a jour la vue qui afficher les noeud absent
     * @param nodeAbsent collection qui contient les noeuad absent
     */
    void updateNoeudAbsent(final Set<Integer> nodeAbsent);

    /**
     * Methode qui met a jour la vue qui affiche les noed incaceccible a partir du premier
     * @param inaccesible collection des noeud inacessible
     */
    void updateNoedInacessible(final Set<Integer> inaccesible);

    /**
     * Methode qui met a jour la vue qui affiche le graphe
     * @param numP numero du paragraphe
     * @param graph graphe
     */
    void showGraph(final int numP, final Graph graph);

    /**
     * Methode qui permet d'afficher une alerte
     * @param title titre de lalerte
     * @param header de lalerte
     * @param content contenu de lalerte
     */
    void displayAlertMessage(final String title,final String header,final String content);

    /**
     * Methode qui permet de mettre a jour le titre du livre dans le vue
     * @param title le titre du livre
     */
    void updateTitle(String title);
}
