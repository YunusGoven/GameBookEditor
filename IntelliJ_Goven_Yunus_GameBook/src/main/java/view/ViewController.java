package view;

import presenter.IPresenter;
import graph.Graph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.impl.Action;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Classe controlleuse de la vue
 */
@SuppressWarnings("unchecked")
public class ViewController extends ViewElements implements Initializable, IView {

	/**
	 * Constructeur qui instancie une ViewControlleur
	 * 
	 * @param pstr presentation
	 */
	public ViewController(final IPresenter pstr) {
		super(pstr);
	}

	@Override
	public void initialize(final URL url, final ResourceBundle resourceBundle) {
		final var presenter = getPrensenter();
		final var items = presenter.getObservableList();
		final var title = presenter.getTitle();
		lvlAllParagraph.setItems(items);
		tfTitle.setText(title);
		initializeChoiceBox();
		miModifyParagraphs.setDisable(true);
	}

	/**
	 * Methode qui permet d'ajouter des element dans les choicebox
	 */
	private void initializeChoiceBox() {
		final ObservableList<Integer> numParagraph = FXCollections.observableArrayList();
		for (int i = 1; i <= 20; i++)
			numParagraph.add(i);
		cbNewParagraphNum.setItems(numParagraph);
		cbNewActionDest.setItems(numParagraph);
	}

	/**
	 * Methode qui permet de savoir le paragraphe qui a ete selectionne dans la
	 * listview
	 * 
	 * @param mouseEvent evenement au clique
	 * @throws Exception qui se declanche si le livre ne contient pas le paragraphe
	 */
	@FXML
	public void paragraphChoice(final MouseEvent mouseEvent) throws Exception {
		final var presenter = getPrensenter();
		final var listOfSelection = lvlAllParagraph.getSelectionModel();
		final var selected = listOfSelection.getSelectedItem();
		if (selected != null) {
			final var content = (String) selected;
			final var numP = getNumberOfParagraphInText(content);
			presenter.setParagraphChoice(numP);
		}
	}

	/**
	 * Methode qui permet de declancher un evenement lorsque l'utilisateur clique
	 * sur un des bouton concernant un paragraphe
	 * 
	 * @param actionEvent le bouton qui a ete clique
	 */
	@FXML
	public void crudParagraphButton(final ActionEvent actionEvent)  {
		final var target = actionEvent.getTarget();
		final var presenter = getPrensenter();
		if (btnDeletePa.equals(target)) {
			disableConfirmationContent(true);
			final int num = presenter.getNumParagraph();
			lDeleteParagraphMsg.setText("Etes vous sur de vouloir supprimer le paragraphe : " + num);
		} else if (btnSaveContent.equals(target)) {
			final var content = taModifyContent.getText();
			presenter.saveParagraphContent(content);
		} else if (btnAddParagraph.equals(target)) {
			final var content = taNewParagraphContent.getText();
			addParagraph(content);
		}
	}

	/**
	 * Methode qui permet dajouter un paragraphe danns le livre
	 * 
	 * @param content contenu du paragraphe
	 */
	private void addParagraph(final String content) {
		final var prensenter = getPrensenter();
		if (!content.isBlank()) {
			final var listOfNumber = cbNewParagraphNum.getSelectionModel();
			final var selectedNum = listOfNumber.getSelectedItem();
			final var num = selectedNum != null ? (int) selectedNum : -1;
			prensenter.addParagraph(num, content);
		}
	}

	/**
	 * Methode qui permet de declancher un evenement lorsque l'utilisateur clique un
	 * bouton concernant une confiramtion de supprimer de paragraphe
	 * 
	 * @param actionEvent le bouton qui a ete clique
	 * @throws Exception qui se lance si le livre ne contient pas une paragraphe
	 */
	@FXML
	public void removeConfirmationButton(final ActionEvent actionEvent) throws Exception {
		final var presenter = getPrensenter();
		final var target = actionEvent.getTarget();
		if (btnConfirlmDelPara.equals(target)) {
			presenter.removeParagraph();
			taModifyContent.clear();
		}

		disableConfirmationContent(false);

	}

	/**
	 * Methode qui permet de declancher un evenement lorsque l'utilisateur clique un
	 * bouton concernant une action
	 * 
	 * @param actionEvent le bouton qui a ete clique
	 */
	@FXML
	public void crudActionButton(final ActionEvent actionEvent) {
		final var target = actionEvent.getTarget();
		if (btnNewAction.equals(target)) {
			disableButton(vbNewActionDisplay, true, 1);
		} else if (btnDeleteAction.equals(target)) {
			lDeleteAction.setText("Etes vous de supprimer l'action -> " + destinationInitial);
			disableButton(vbDeleteAction, true, 2);
		} else if (btnSaveAction.equals(target)) {
			saveAction();

		}
	}

	/**
	 * Methode qui permet de sauvegarder une action
	 */
	private void saveAction() {
		final var presenter = getPrensenter();
		final var actCont = tfContentAction.getText();
		final var actDest = tfDestinationAction.getText();
		if (Pattern.matches("^[+-]?\\d+$", actDest)) {
			final int numDest = Integer.parseInt(actDest);
			presenter.modifyAction(numDest, actCont, destinationInitial);
		}
	}

	/**
	 * Methode qui permet de declancher un evenement lorsque l'utilisateur clique un
	 * bouton concernant les confirmation dune action
	 * 
	 * @param event le bouton qui a ete clique
	 */
	@FXML
	public void actionButtonConfirmation(final ActionEvent event) {
		final var presenter = getPrensenter();
		final var target = event.getTarget();
		if (btnAddNewAction.equals(target)) {
			doAddNewAction();
		} else if (btnRejectAction.equals(target)) {
			disableButton(vbDeleteAction, false, 3);
		} else if (btnConfirmAction.equals(target)) {
			presenter.deleteAction(destinationInitial);
			disableButton(vbDeleteAction, false, 4);
		}
	}

	/**
	 * Methode qui permet dajouter une action a un paragraphe
	 */
	private void doAddNewAction() {
		final var presenter = getPrensenter();
		final var content = tfNewActionText.getText();
		if (!content.isBlank()) {
			final var selectionModel = cbNewActionDest.getSelectionModel();
			final var num = selectionModel.getSelectedItem();
			if (num != null) {
				final var numDest = (int) num;
				presenter.addAction(numDest, content);
			}
		}
	}

	/**
	 * Methode qui permt de modifier le titre du libre
	 * 
	 * @param actionEvent le bouton change a ete clique
	 */
	@FXML
	public void changeTitle(final ActionEvent actionEvent) {
		final var presenter = getPrensenter();
		final var target = actionEvent.getTarget();
		if (btnChangeTitle.equals(target)) {
			final var title = tfTitle.getText();
			presenter.changeTitle(title);
		}
	}

	/**
	 * Methode qui permet de declancher un evenement lorsque l'utilisateur clique un
	 * bouton concernant de renouveler le livre
	 * 
	 * @param actionEvent boutoon qui a ete cliqye
	 */
	@FXML
	public void newBook(final ActionEvent actionEvent) {
		final var presenter = getPrensenter();
		final var target = actionEvent.getTarget();
		if (btnYNB.equals(target)) {
			presenter.newBook();
		}
		nbookcont.setVisible(false);
		container.setVisible(true);
	}

	@Override
	public void addActionInView(final int destination, final String content, final boolean added) {
		if (added) {
			setActionInformation(destination, content);
		}
		disableButton(vbNewActionDisplay, false, 5);

	}

	/**
	 * Methode qui permet d'ajouter un evenemnt au action lorsquil sajoute
	 * 
	 * @param destination numero de destionation de laction
	 * @param tf          tablaeu des textfield
	 */
	private void setOnMouseClicked(final int destination, final TextField... tf) {
		tf[0].setOnMouseClicked(mouseEvent -> {
			tfContentAction = tf[1];
			tfDestinationAction = tf[2];
			destinationInitial = destination;
			disableButtonWhenAction();
		});
	}

	@Override
	public void updateActionList(final int destination) {

		final var children = vbAllActionDetail.getChildren();
		for (final var child : children) {
			final var id = child.getId();
			if (Integer.parseInt(id) == destination) {
				children.remove(child);
				break;
			}
		}
	}

	@Override
	public void updateNoeudAbsent(final Set<Integer> nodeAbsent) {
		hbNoedAbsent.getChildren().clear();
		if (nodeAbsent.isEmpty()) {
			showInView(hbNoedAbsent, "Aucun noeud");
		} else {
			for (final int val : nodeAbsent) {
				showInView(hbNoedAbsent, val + ", ");
			}
		}
	}

	@Override
	public void updateNoedInacessible(final Set<Integer> inaccesible) {
		hbNoedTermin.getChildren().clear();
		if (inaccesible.isEmpty()) {
			showInView(hbNoedTermin, "Aucun noeud");
		} else {
			for (final int val : inaccesible) {
				showInView(hbNoedTermin, "" + val);
			}
		}
	}

	@Override
	public void showGraph(final int numP, final Graph graph){
		if(!graph.isEmpty()){
			for(int i=1; i<=numP;++i){
				final var tfNum = createwNode(i);
				tfNum.setLayoutY(i*50);
				lvGraph.getChildren().add(tfNum);
			}
			for(int i=1;i<=numP;++i){
				var list = graph.getNoeud(i);
				if(!list.isEmpty())
					drawLine(i,list);
			}
		}else{
			final var label = new Label("Graphe vide");
			lvGraph.getChildren().add(label);
		}
	}

	/**
	 * Methode qui permet de creer un label dont son contenu est le numero du paragraphe
	 * @param numNode numero du paragraphe
	 * @return un Label dont le contenu est numNode
	 */
	private Label createwNode(final int numNode){
		final var iToString = String.valueOf(numNode);
		final var tfNum = new Label(iToString);
		tfNum.setDisable(true);

		final var paneX =lvGraph.getHeight()/2;
		if(numNode==1){
			tfNum.setLayoutX(paneX);
		}else if(numNode%2==0){
			tfNum.setLayoutX(paneX/2);
		}else if(numNode%2!=0){
			tfNum.setLayoutX(paneX*1.5);
		}
		return tfNum;
	}

	/**
	 * Methode qui permet de dessiner les ligne du graohe
	 * @param src noeud source de la ligne
	 * @param list liste des different noeud qui est accecisble par le noeud source
	 */
	private void drawLine(final int src, final Set<Integer> list) {
		var posNodeSource = getNodePosition(src);
		for(final var nodeDest : list){
			var posNodeDest = getNodePosition(nodeDest);
			final var line = new Line(posNodeSource[0]+6,posNodeSource[1]+12,posNodeDest[0],posNodeDest[1]);
			final var circle = new Circle(2);
			circle.setCenterX(posNodeDest[0]);
			circle.setCenterY(posNodeDest[1]);

			lvGraph.getChildren().add(line);
			lvGraph.getChildren().add(circle);
		}

	}

	/**
	 * Methode qui permet de trouver la position d'un noued dans une vue par rapport au contenu du noeud
	 * @param src contenu du noeud recherche
	 * @return un tableau de type Double de 2 elements, l'element en position 0 correspont a l'axe X et celui en position 1 correspont a l'axe Y
	 */
	private Double[] getNodePosition(final int src){
		final Double[] pos = new Double[2];
		for(final var element : lvGraph.getChildren()){
			if(element instanceof Label) {
				final var tfText = ((Label)element).getText();
				final var srcInText = String.valueOf(src);
				if (tfText.equals(srcInText)) {
					pos[0] = element.getLayoutX();
					pos[1] = element.getLayoutY();
					break;
				}
			}
		}
		return pos;
	}

	@Override
	public void displayAlertMessage(final String title, final String header, final String content) {
		final Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@Override
	public void updateTitle(String title) {
		tfTitle.setText(title);
	}

	@Override
	public void updateModifAndAction(final String pContent, final Set<Action> actions) {
		vbAllActionDetail.getChildren().clear();
		taModifyContent.setText(pContent);
		disableDefaultButton(vbAllActionDetail, false, 7);
		hbButtonAction.setDisable(false);

		for (final var action : actions) {
			final var destination = action.getDestination();
			final var content = action.getContent();
			setActionInformation(destination, content);
		}

	}

	private void setActionInformation(final int destination, final String content) {
		final HBox hb = new HBox();
		final var children = vbAllActionDetail.getChildren();
		hb.setId(destination + "");
		final TextField tfD = new TextField("" + destination);
		final TextField tfC = new TextField(content);
		setOnMouseClicked(destination, tfC, tfC, tfD);
		setOnMouseClicked(destination, tfD, tfC, tfD);
		hb.getChildren().addAll(tfC, new Label("       "), tfD);
		children.add(hb);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateParagraphList(final ObservableList<String> paragraphList, final int num) {
		lvlAllParagraph.setItems(paragraphList);
		if (num == 0 || num == 2 || num == 4) {
			taNewParagraphContent.clear();
			disableDefaultButton(vbAllActionDetail, true, 6);
			hbButtonAction.setDisable(true);
		}
		if (num == 4) {
			vbAllActionDetail.setVisible(false);
			taModifyContent.clear();
			tfTitle.clear();
		}

	}

}
