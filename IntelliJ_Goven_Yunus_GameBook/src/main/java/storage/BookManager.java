package storage;

import model.impl.Action;
import model.impl.Book;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * Class bookmanager qui implmente l'interface IManager
 * 
 * @author Yunus
 *
 */
public class BookManager implements IManager {

	@Override
	public Book load(final String path) {
		try (final ObjectInputStream input = new ObjectInputStream(new FileInputStream(path))) {
			return (Book) input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean save(final Book book, final String path) {
		try (final ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path))) {
			output.writeObject(book);
			exportToJson(book,path);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Methode qui permet d'exporter le livre-jeu en json
	 * 
	 * @param book livre-jeu a exporter
	 */
	private void exportToJson(final Book book, String path) {
		final var pos = path.lastIndexOf("\\");
		path = path.substring(0,pos+1);
		var title = book.getTitle();
		title = title.replace(" ","_");
		final File output = new File(path+title+".json");
		try (FileWriter writer = new FileWriter(output, StandardCharsets.UTF_8)) {
			writer.write(toJson(book));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode qui retourne une chaine de caraceter qui contient le livre-jeu sous
	 * forme json
	 * 
	 * @param book livre-jeu a exporter
	 * @return String livre-jeu sous format .json
	 */
	protected String toJson(final Book book) {
		return Json.createObjectBuilder().add("title", book.getTitle()).add("paragraphs", paragraphToJson(book)).build()
				.toString();
	}

	/**
	 * Methode qui permet de creer un format json pour les paragraphe du livre
	 * 
	 * @param book livre-jeu
	 * @return JsonArrayBuilder livre sous forme de tableau json
	 */
	private JsonArrayBuilder paragraphToJson(final Book book) {
		final JsonArrayBuilder json = Json.createArrayBuilder();
		for (final var paragraph : book) {
			json.add(Json.createObjectBuilder().add("number", paragraph.getNumber())
					.add("content", paragraph.getContent()).add("actions", actionsToJson(paragraph.getActions())));

		}

		return json;
	}

	/**
	 * Methode qui permet de creer un format json pour les action dune action
	 * 
	 * @param actions collection d'actins
	 * @return JsonArrayBuilder action sous forme de tableau json
	 */
	private JsonArrayBuilder actionsToJson(final Set<Action> actions) {
		final JsonArrayBuilder json = Json.createArrayBuilder();
		for (final var action : actions) {
			json.add(Json.createObjectBuilder().add("contentAction", action.getContent()).add("destiantion",
					action.getDestination()));
		}

		return json;
	}
}
