package model.impl;

import model.IAction;
import model.IParagraph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Classe Paragraph qui implemente IParagraph et Serializable
 * 
 * @author Yunus G.
 */
public class Paragraph implements IParagraph, Serializable {
	private String content;
	private int number;
	private Set<Action> actions;

	/**
	 * Constructeur
	 *
	 * @param number  du paragragpe
	 * @param content texteduparagraphe
	 */
	public Paragraph(final int number, final String content) {
		this.number = number;
		this.content = content;
		this.actions = new HashSet<>();
	}

	/**
	 * Constructeur
	 * 
	 * @param number  du paragragpe
	 * @param content texteduparagraphe
	 * @param actions list daction possible
	 */
	public Paragraph(final int number, final String content, final HashSet<Action> actions) {
		this.content = content;
		this.number = number;
		this.actions = actions;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setContent(final String content) {
		this.content = content;
	}

	@Override
	public boolean hasAction() {
		return !actions.isEmpty();
	}

	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public void setNumber(final int number) {
		this.number = number;
	}

	@Override
	public void setActions(final Set<Action> actions) {
		this.actions = actions;
	}

	@Override
	public Set<Action> getActions() {
		return actions;
	}

	@Override
	public String getInformationInShort() {
		final int cont = content.length();
		final int min = Math.min(20, cont);
		return number + ". " + content.substring(0, min);
	}

	@Override
	public boolean addAction(final Action action) {
		if (action != null)
			return actions.add(action);
		return false;
	}

	@Override
	public void deleteAction(final int key) {
		actions.removeIf(action1 -> key == action1.getDestination());
	}

	@Override
	public void removeAction(final IAction action) {
		if (action != null) actions.remove(action);
	}

	@Override
	public IAction getThisAction(final int key) {
		for (final var action : actions) {
			if (action.getDestination() == key)
				return action;
		}
		return null;
	}

	@Override
	public Set<Integer> getNumberOfAllActionPossible() {
		final Set<Integer> set = new HashSet<>();
		for (final var action : actions) {
			set.add(action.getDestination());
		}

		return set;
	}

	@Override
	public Iterator<Action> iterator() {
		return actions.iterator();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Paragraph))
			return false;
		final Paragraph that = (Paragraph) o;
		return number == that.number;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(number);
	}
}
