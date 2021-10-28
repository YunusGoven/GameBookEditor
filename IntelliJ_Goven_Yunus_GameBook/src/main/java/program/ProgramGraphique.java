package program;

import presenter.IPresenter;
import presenter.impl.Presenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.impl.Book;
import storage.BookManager;
import storage.IManager;
import view.IView;
import view.ViewController;

import java.io.IOException;

public class ProgramGraphique extends Application {

    /**
     * Methode qui permet de demarrer lapplication
     *
     * @param args argument
     */
    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        Parent root;
        final IManager storage = new BookManager();
        try {
            final var ressource = getClass().getResource("/View.fxml");
            final FXMLLoader chargeurFXML = new FXMLLoader(ressource);
            final IPresenter presenter = new Presenter(new Book(""), storage);
            final IView view = new ViewController(presenter);
            chargeurFXML.setController(view);
            presenter.setView(view);

            root = chargeurFXML.load();
            final Scene scene = new Scene(root);
            primaryStage.setTitle("Un livre dont vous etes le héros");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
