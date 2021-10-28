package commands;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import console.Console;
import model.IParagraph;
import model.impl.Book;
import model.impl.Paragraph;

public class GrapheLivre extends Command {

	private final Console console;
	private final Book book;
	private List<Integer> graphe;

	public GrapheLivre(Console console, Book book) {
		super(4, "Afficher le graphe");
		this.book = book;
		this.console = console;
		this.graphe = new LinkedList<>();
	}
	@Override
	public void execute() throws Exception {
		if (book.getNumberOfParagraph() == 0) {
			console.printLine("Graphe vide.");
			return;
		}
		for (Paragraph paragraph : book) {
			int numero = paragraph.getNumber();
			Set<Integer> numbersActions = paragraph.getNumberOfAllActionPossible();
			if (numbersActions.isEmpty()) {
				console.printLine("%d", numero);
			} else {
				for (int action : numbersActions) {
					graphe.add(numero);
					graphe.add(action);
					parcours(action);
					graphe = new LinkedList<>();
				}
			}
		}
	}
	private void parcours(int numero) throws Exception {
		IParagraph paragraph = book.getThisParagraph(numero);
		Set<Integer> numbersActions = paragraph.getNumberOfAllActionPossible();
		if(!numbersActions.isEmpty()) {
			for (int action : numbersActions) {
				graphe.add(action);
				parcours(action);
			}
		}else{
			console.printLine(graphe.toString());
		}
		graphe.remove(graphe.size()-1);
	}
}
