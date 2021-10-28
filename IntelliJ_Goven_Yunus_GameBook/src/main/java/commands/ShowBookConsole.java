package commands;

import model.impl.Action;
import model.impl.Book;
import model.impl.Paragraph;

import java.util.Iterator;

public class ShowBookConsole {
    /**
     * Methode qui retourne le livre sous une chaine
     *
     * @return chaine de caractere
     */
    public static String showBook(final Book book) {
        String bookText = "(Aucun Paragraphe)";

        if (!book.isEmpty()) {
            bookText = showParagrapheAndAction(book);
        }

        return bookText;

    }

    /**
     * Methode qui retourne les paragraphe avec ses action sous une chaine de
     * caractere
     *
     * @return chaine de caractere
     */
    private static String showParagrapheAndAction(final Book book) {
        StringBuilder content = new StringBuilder();
        for (Paragraph p : book) {
            final Iterator<Action> actions = p.iterator();
            content.append(showParagrapheInShort(p));
            content.append(showActionInShort(actions)).append("\n");
        }
        return content.toString();
    }

    /**
     * Methode qui retourne le contenu dun paragraphe en petit
     *
     * @param p paragraphe
     * @return chaine de caractere
     */
    private static String showParagrapheInShort(final Paragraph p) {
        String content = "";
        String textParagraphe = p.getContent();
        final int numParagraphe = p.getNumber();
        final int paragrapheLength = textParagraphe.length();
        final int minText = Math.min(20, paragrapheLength);
        textParagraphe = textParagraphe.substring(0, minText);
        content += String.format("P:%d %-20s... -", numParagraphe, textParagraphe);
        return content;
    }

    /**
     * Methode qui retourne les actions sous une chaine de caractere
     *
     * @param actions iterateur dactions
     * @return chaine de caractere
     */
    private static String showActionInShort(final Iterator<Action> actions) {
        StringBuilder content = new StringBuilder();
        int numAction, actionLength, minText;
        while (actions.hasNext()) {
            final Action a = actions.next();
            String textAction = a.getContent();
            numAction = a.getDestination();
            actionLength = textAction.length();
            minText = Math.min(10, actionLength);
            textAction = textAction.substring(0, minText);
            content.append(String.format("%s %d - ", textAction, numAction));
        }
        return content.toString();
    }
}
