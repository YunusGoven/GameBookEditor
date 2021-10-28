package model.impl;

import model.IAction;

import java.io.Serializable;

/**
 * Classe Action qui implemente IAction, Serializable
 */
public class Action implements IAction, Serializable {

	private int destination;
	private String content;

	/**
	 * Constructeur daction
	 *
	 * @param destination numero de laction
	 * @param content     texte de laction
	 */
	public Action(final String content, final int destination) {
		this.content = content;
		this.destination = destination;
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
	public int getDestination() {
		return destination;
	}

	@Override
	public void setDestination(final int destination) {
		this.destination = destination;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final Action action = (Action) o;
		return destination == action.destination;
	}

	@Override
	public int hashCode() {
		return destination;
	}
}
