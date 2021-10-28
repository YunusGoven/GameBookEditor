package console;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Interagit avec l'utilisateur Ã  travers un terminal de saisies de
 * commandes.
 *
 *
 */
public class UserConsole implements Console {
	private static final String INTEGER_PATTERN = "^[+-]?\\d+$";
	private final PrintStream output;
	private final Scanner input;

	/**
	 * Constructeur
	 */
	public UserConsole() {
		this.output = System.out;
		this.input = new Scanner(System.in);
	}

	@Override
	public String readLine(final String fmt, final Object... args) {
		print(fmt, args);
		output.println();
		return input.nextLine();
	}

	@Override
	public void printLine(final String fmt, final Object... args) {
		print(fmt, args);
		printLine();
	}

	@Override
	public void print(final String fmt, final Object... args) {
		output.printf(fmt, args);
	}

	@Override
	public void printLine() {
		output.println();
	}

	@Override
	public int readInteger(final String fmt, final Object... args) {
		String intAsText;
		do {
			intAsText = readLine(fmt, args);
		} while (!intAsText.matches(INTEGER_PATTERN));

		return Integer.parseInt(intAsText);
	}

	@Override
	public void exit() {
		System.exit(0);
	}

}
