package view;

import presenter.IPresenter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Classe ViewElements qui contient les elements principaux de la vue
 * 
 * @author GOVEN Y.
 */
public class ViewElements {

	@FXML
	public Button btnDeletePa, btnSaveContent,btnAddParagraph,btnConfirlmDelPara, btnNewAction;
	@FXML
	public Button btnSaveAction, btnDeleteAction,btnAddNewAction,btnConfirmAction,btnRejectAction,btnChangeTitle, btnYNB;
	@FXML
	public TextArea taModifyContent,taNewParagraphContent;
	@FXML
	public TextField tfNewActionText, tfTitle;
	@FXML
	public Label lDeleteAction,lDeleteParagraphMsg;
	@FXML
	public ChoiceBox cbNewParagraphNum,cbNewActionDest;
	@FXML
	public HBox hbButtonAction,hbTtitle, hbNoedAbsent,hbNoedTermin;
	@FXML
	public VBox vbAllActionDetail,vbNewActionDisplay,vbDeleteAction;
	@FXML
	public GridPane gParagraphList,gContent,gNewParagraph,gActions,gDeletePConfirm;
	@FXML
	public MenuItem miSave,miLoad,miNewBook, miViewGraph,miVerifyBook,miModifyParagraphs;
	@FXML
	public Menu mFile;
	@FXML
	public GridPane container,nbookcont,gVerify,gGraph;
	@FXML
	public ListView lvlAllParagraph;
	@FXML
	public Pane lvGraph;

	protected TextField tfDestinationAction;
	protected TextField tfContentAction;
	protected int destinationInitial;
	private final IPresenter prensenter;

	/**
	 * Constructeur de ViewElement
	 * 
	 * @param pstr presentatteur
	 */
	protected ViewElements(final IPresenter pstr) {
		this.prensenter = pstr;
	}

	/**
	 * Methode qui retourne le presenteur
	 * 
	 * @return le modele de presentation
	 */
	public IPresenter getPrensenter() {
		return prensenter;
	}

	/**
	 * Methode qui permet de declancher un evenement lorsque l'utilisateur clique
	 * sur element du menu file
	 * 
	 * @param actionEvent action qui se realise
	 */
	@FXML
	public void menuFileItem(final ActionEvent actionEvent) {
		final var target = actionEvent.getTarget();
		if (miSave.equals(target)) {
			saveBook();
		} else if (miLoad.equals(target)) {
			loadBook();
		}
	}

	/**
	 * Methode qui permet deffectuer la sauvegarder du livre jeu
	 */
	private void saveBook() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sauver un livre-jeu");
		final File file = fileChooser.showSaveDialog(btnDeleteAction.getScene().getWindow());
		if (file != null) {
			final var path = file.getPath();
			prensenter.saveBook(path);
		}

	}

	/**
	 * Methoed qui permet de recuperer le livre-jeu
	 */
	private void loadBook() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sauver un livre-jeu");
		final File file = fileChooser.showOpenDialog(btnYNB.getScene().getWindow());
		if (file != null) {
			prensenter.loadBook(file);
		}

	}

	/**
	 * Methode qui permet de declancher un evenement lorsque l'utilisateur clique un
	 * element du menu Book
	 * 
	 * @param actionEvent clique sur litem
	 * @throws Exception qui se lance si le livre ne contient pas un paragraphe
	 */
	@FXML
	public void menuBookItem(final ActionEvent actionEvent) throws Exception {
		final var target = actionEvent.getTarget();
		if (miNewBook.equals(target)) {
			container.setVisible(false);
			nbookcont.setVisible(true);
		} else if (miVerifyBook.equals(target)) {
			hbNoedAbsent.getChildren().clear();
			hbNoedTermin.getChildren().clear();
			disableMenuItem(true, miVerifyBook, miNewBook, miModifyParagraphs, miViewGraph);
			setVisiblePane(false, container, gGraph, gVerify);
			prensenter.verifyBook();
		} else if (miModifyParagraphs.equals(target)) {
			disableMenuItem(false, miVerifyBook, miNewBook, miModifyParagraphs);
			miViewGraph.setDisable(false);
			setVisiblePane(true, gGraph, gVerify, container);
		} else if (miViewGraph.equals(target)) {
			lvGraph.getChildren().clear();
			disableMenuItem(true, miNewBook, miViewGraph, miVerifyBook, miModifyParagraphs);
			setVisiblePane(false, container, gVerify, gGraph);
			prensenter.showGraph();
		}

	}

	/**
	 * Methode qui permet de desactiver les boutons des menu
	 * 
	 * @param isDisable boolean si les element doivent etre desactivier ou non
	 * @param items     items du menu
	 */
	private void disableMenuItem(final boolean isDisable, final MenuItem... items) {
		mFile.setDisable(isDisable);
		items[0].setDisable(isDisable);
		items[1].setDisable(isDisable);
		items[2].setDisable(!isDisable);
		if (items.length == 4)
			items[3].setDisable(!isDisable);

	}

	/**
	 * Mthode qui permet de rendre visible ou non les gridpane
	 * 
	 * @param titleIsVisible boolean qui determine si le titre doit etre visible
	 * @param panes          les gridspane qui doivent changer de visibilibe
	 */
	private void setVisiblePane(final boolean titleIsVisible, final GridPane... panes) {
		panes[0].setVisible(false);
		panes[1].setVisible(false);
		panes[2].setVisible(true);
		hbTtitle.setVisible(titleIsVisible);
	}

	/**
	 * Methode qui permet de desactiver les boutons
	 * 
	 * @param vbox   vbox qui doit se rendre visible ou non
	 * @param open   bolean qui determine si les bouton doivent etre desactiver ou
	 *               non
	 * @param option numero qui determine qui declanche cette appel de methode
	 */
	protected void disableButton(final VBox vbox, final boolean open, final int option) {
		disableDefaultButton(vbox, open, option);
		lvlAllParagraph.setDisable(open);
		btnAddParagraph.setDisable(open);
		btnNewAction.setDisable(open);
		btnDeleteAction.setDisable(!open);
		btnSaveAction.setDisable(open);
		doSwitch(open, option);
	}

	/**
	 * Methode qui permet de desactiver les bouton specifique par par raport a
	 * levenement qui lappel
	 * 
	 * @param open   bolean qui determine si les bouton doivent etre desactiver ou
	 *               non
	 * @param option numero qui determine qui declanche cette appel de methode
	 */
	private void doSwitch(final boolean open, final int option) {
		switch (option) {
		case 1:
			btnNewAction.setDisable(!open);
			btnDeleteAction.setDisable(open);
			break;
		case 5:
			btnDeleteAction.setDisable(open);
			break;
		case 3:
		case 4:
			btnSaveAction.setDisable(!open);
			break;
		default:
			break;
		}
	}

	/**
	 * Methode qui permet de desactiver des bouton de default
	 * 
	 * @param vbox   bvox qui doit etre visible ou non
	 * @param open   bolean qui determine si les bouton doivent etre desactiver ou
	 *               non
	 * @param option numero qui determine qui declanche cette appel de methode
	 */
	protected void disableDefaultButton(final VBox vbox, final boolean open, final int option) {
		vbox.setVisible(open);
		btnSaveContent.setDisable(open);
		btnDeletePa.setDisable(open);
		if (option == 7) {
			vbox.setVisible(!open);
			btnDeleteAction.setDisable(!open);
			btnSaveAction.setDisable(!open);
			btnNewAction.setDisable(open);
		}
	}

	/**
	 * Methode qui permet de desactiver les bouton de confirmation d'un paragraphe
	 * 
	 * @param open bolean qui determine si les bouton doivent etre desactiver ou non
	 */
	protected void disableConfirmationContent(final boolean open) {
		gDeletePConfirm.setVisible(open);
		gParagraphList.setDisable(open);
		gActions.setDisable(open);
		gContent.setDisable(open);
		gNewParagraph.setVisible(!open);
	}

	/**
	 * Methode qui permet de desactiver les boutons des actions
	 *
	 */
	protected void disableButtonWhenAction() {
		btnDeleteAction.setDisable(false);
		btnSaveAction.setDisable(false);
	}

	/**
	 * Methode qui retorune le numero dun paragraphe depuis un texte
	 * 
	 * @param content texte qui contient les infos dun paragraphe
	 * @return un entier
	 */
	protected int getNumberOfParagraphInText(final String content) {
		final int posNum = content.indexOf(". ");
		final String numInString = content.substring(0, posNum);
		return Integer.parseInt(numInString);
	}

	/**
	 * Methode qui permet dafficher dans le conteneur des noeud terminaux des
	 * information
	 * 
	 * @param content inforamtion a afficher
	 */
	protected void showInView(final HBox box, final String content) {
		final Label lbl = new Label(content);
		lbl.setFont(new Font(23));
		final ObservableList<Node> list = box.getChildren();
		list.add(lbl);
	}
}
