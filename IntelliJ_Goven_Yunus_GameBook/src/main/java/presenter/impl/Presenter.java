package presenter.impl;

import presenter.IPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.IBook;
import model.IParagraph;
import model.impl.Action;
import model.impl.Book;
import model.impl.Paragraph;
import storage.IManager;
import view.IView;

import java.io.File;

/**
 * Classe Preseneter qui implement IPresenter => Design Patterns MVP
 */
public class Presenter implements IPresenter {

	private IBook book;
	private IView view;
	private final ObservableList<String> paragraphList;
	private int numParagraph;
	private IParagraph paragraph;
	private final IManager loadAndSave;

	/**
	 * Constructeur qui creer une instance de prensenter
	 * 
	 * @param book    livre-jeu
	 * @param manager permet de sauvergarder et recuperer le livre
	 */
	public Presenter(final IBook book, final IManager manager) {
		this.book = book;
		numParagraph = 0;
		this.loadAndSave = manager;
		this.paragraphList = bookInfo();
	}

	/**
	 * Methode qui retourne une list observable de String
	 * 
	 * @return ObservableList<String> qui contient les informations des paragrapghes
	 *         en court
	 */
	private ObservableList<String> bookInfo() {
		final ObservableList<String> list = FXCollections.observableArrayList();

		for (Paragraph paragraph : book) {
			final var content = paragraph.getInformationInShort();
			list.add(content);
		}
		return list;
	}

	/**
	 * Methode qui permet de verifier le numero du paragraphe
	 * 
	 * @param num numero du paragaphe que l'utilisateur a choisi
	 * @return num si num est plus petit que le dernier paragraphe dans le livre et
	 *         plus grand que 0 sinon numero du dernier paragraphe +1
	 */
	private int verification(final int num) {
		var numero = num;
		final var lastParagraph = book.getLastParagraph();
		if (num > lastParagraph || num == -1)
			numero = lastParagraph + 1;

		return numero;
	}

	/**
	 * Methode qui permet d'ajouuter une action dans le livvre et ajouter un
	 * paragraphe si le numero de destination de contient pas le paragraphe
	 * 
	 * @param content     contenu de laction
	 * @param destination numero de destionation de laction
	 * @return true si laction a ete ajouter sinon false
	 */
	private boolean addActionInBook(final String content, final int destination) {
		final var action = new Action(content, destination);
		final var added = paragraph.addAction(action);
		if (!book.containsKey(destination)) {
			book.addParagraph(destination);
			updateParagraphList();
		}
		return added;
	}

	/**
	 * Methode qui met a jour la list des paragraphe dans la ObservableList dans le
	 * cas ou le livre change
	 */
	private void updateParagraphList() {
		paragraphList.clear();
		for (final var paragraph : book) {
			final var info = paragraph.getInformationInShort();
			paragraphList.add(info);
		}
	}

	@Override
	public void setView(final IView view) {
		this.view = view;
	}

	@Override
	public ObservableList<String> getObservableList() {
		return paragraphList;
	}

	@Override
	public void setParagraphChoice(final int key) throws Exception {
		this.numParagraph = key;
		paragraph = book.getThisParagraph(key);
		final var pContent = paragraph.getContent();
		final var actions = paragraph.getActions();
		if (view != null)
			view.updateModifAndAction(pContent, actions);
	}

	@Override
	public void removeParagraph() throws Exception {
		if (numParagraph != 0) {
			book.deleteParagraph(numParagraph);
			updateParagraphList();
			numParagraph = 0;
			if (view != null)
				view.updateParagraphList(paragraphList, 2);
		}
	}

	@Override
	public void addParagraph(int num, final String content) {
		num = verification(num);
		final var paragraph = new Paragraph(num, content);
		book.addParagraph(paragraph);
		final var paragraphInfo = paragraph.getInformationInShort();
		paragraphList.add(paragraphInfo);
		if (view != null)
			view.updateParagraphList(paragraphList, 0);
	}

	@Override
	public void saveParagraphContent(final String newContent) {
		if (numParagraph != 0) {
			final var contentInitial = paragraph.getContent();
			if (!newContent.equals(contentInitial)) {
				paragraphList.remove(paragraph.getInformationInShort());
				paragraph.setContent(newContent);
				final var newInformation = paragraph.getInformationInShort();
				paragraphList.add(newInformation);
				if (view != null)
					view.updateParagraphList(paragraphList, 1);
			}
		}
	}

	@Override
	public void addAction(final int destination, final String content) {

		if (numParagraph != 0 && destination != 1 && destination != numParagraph) {
			final boolean added = addActionInBook(content, destination);
			if (view != null) {
				view.updateParagraphList(paragraphList, 3);
				view.addActionInView(destination, content, added);
			}
		}

	}

	@Override
	public void deleteAction(final int destination) {
		if (numParagraph != 0) {
			final var action = paragraph.getThisAction(destination);
			paragraph.removeAction(action);
			if (view != null) {
				view.updateActionList(destination);
			}
		}
	}

	@Override
	public void modifyAction(final int newDestination, final String newContent, final int destination) {
		if (numParagraph != 0) {
			final var action = paragraph.getThisAction(destination);
			if (!newContent.isBlank()) {
				action.setDestination(newDestination);
				action.setContent(newContent);
			}
		}
	}

	@Override
	public int getNumParagraph() {
		return numParagraph;
	}

	@Override
	public void changeTitle(final String title) {
		if (!title.isBlank()) {
			book.setTitle(title);
		}
	}

	@Override
	public String getTitle() {
		return book.getTitle();
	}

	@Override
	public void saveBook(final String path) {
		final boolean isSaved = loadAndSave.save((Book) book, path);
		if (view != null) {
			if (isSaved) {
				view.displayAlertMessage("Information", "Sauvegarde réussie",
						"Le livre-jeu a été correctement sauvegardé");
			} else {
				view.displayAlertMessage("Attention", "Echec de la sauvegarde",
						"Le livre-jeu n'a pas pu être sauvegardé");
			}
		}
	}

	@Override
	public void loadBook(final File file) {
		final var path = file.getPath();
		var loaded = false;
		final Book gameBook = loadAndSave.load(path);
		if (gameBook != null) {
			book = gameBook;
			loaded = true;
			updateParagraphList();
		}

		if (view != null) {
			if (loaded)
				view.displayAlertMessage("Information", "Chargement réussi",
						"Le livre-jeu a été correctement récupéré");
			else
				view.displayAlertMessage("Attention", "Echec du chargement", "Le livre-jeu n'a pas pu être récupéré");
			view.updateParagraphList(paragraphList, 0);
			view.updateTitle(book.getTitle());
		}
	}

	@Override
	public void newBook() {
		book = new Book("");
		updateParagraphList();
		if (view != null) {
			view.updateParagraphList(paragraphList, 4);
		}
	}

	@Override
	public void verifyBook() throws Exception {
		final var nodeAbsent = book.getNodeAbsent();
		final var inaccesible = book.getInaccesibleParagraph();

		if (view != null) {
			view.updateNoeudAbsent(nodeAbsent);
			view.updateNoedInacessible(inaccesible);
		}
	}

	@Override
	public void showGraph() throws Exception {
		final var graph = book.getGraph();
		final var numP = book.getNumberOfParagraph();
		if (view != null) {
			view.showGraph(numP, graph);
		}
	}
}
