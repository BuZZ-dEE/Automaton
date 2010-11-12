package automaton;

import java.util.Scanner;

public class Automaton {

	// Sigma ist einfach gleich ASCII

	int sizeOfQ;
	// Q ist die Menge {0,...,sizeOfQ-1}

	int sizeOfSigma;

	char[] sigma;

	int[][] delta;
	// delta[q][x] ist gerade delta(q,x)

	int q_0;
	// Startzustand

	boolean[] Q_a;
        // Q_a[i] ist genau dann wahr, wenn i \in Q_a

	public String eingabe() {

		Scanner scanner = new Scanner(System.in);
		String eingabe = scanner.nextLine();

		return eingabe;
	}

	public void sizeOfQ() {

		System.out.println("Bitte geben Sie die Anzahl der Zustände ein.");

		try {

			sizeOfQ = Integer.parseInt(eingabe());
		} catch (Exception e) {

			System.out.println("Das war kein Integer.");
		}
	}

	public void sizeOfSigma() {

		System.out.println("Bitte geben Sie die Anzahl der Zeichen ein.");

		try {

			sizeOfSigma = Integer.parseInt(eingabe());
			sigma = new char[sizeOfSigma];
		} catch (Exception e) {

			System.out.println("Das war kein Integer.");
		}
	}

	public void sigma() {

		;

		System.out.println("Bitte geben Sie die Zeichen ein.");

		for (int i = 0; i < sizeOfSigma; i++) {

			sigma[i] = eingabe().charAt(0);
		}
	}

	public void delta() {

		delta = new int[sizeOfQ][sizeOfSigma];

		System.out.println("Bitte geben Sie die Zustandsübergänge an.");
		System.out.println("Alphabet = {0,1}");
		System.out.println("Zustände werden durch Integerwerte beschrieben.");

		for (int i = 0; i < sizeOfQ; i++) {

			try {

				for (int j = 0; j < sizeOfSigma; j++) {

					System.out.println("Geben sie für Zustand " + i
							+ " und Zeichen " + sigma[j]
							+ " den Zielzustand ein.");
					delta[i][j] = Integer.parseInt(eingabe());

					if (delta[i][j] > sizeOfQ - 1) {

						System.out.println("Den Zustand gibt es nicht.");
						j--;
					}
				}
			} catch (Exception e) {

				System.out.println("Das war kein Integer.");
			}
		}
	}

	public void acceptedState() {

		int anzahlQ_a;

		try {

			System.out
					.println("Geben Sie die Anzahl akzeptierender Zustände ein.");
			anzahlQ_a = Integer.parseInt(eingabe());
			Q_a = new boolean[sizeOfQ];
			// Eventuell vorher alle auf false setzen.

			System.out.println("Geben Sie die akzeptierenden Zustände ein.");
			for (int i = 0; i < anzahlQ_a; i++) {

				Q_a[Integer.parseInt(eingabe())] = true;

			}

		} catch (Exception e) {

			System.out.println("Das war kein Integer." + eingabe());
		}
	}

	public void startState() {

		try {

			System.out.println("Geben Sie den Startzustand ein.");
			q_0 = Integer.parseInt(eingabe());
		} catch (Exception e) {

			System.out.println("Das war kein Integer.");
		}
	}

	public int deltaStar(int state, char[] word) {

		// for (int i = 0; i < word.length(); i++) {
		//
		// state = delta[state][word.charAt(i) - '0'];
		// }
		for (int i = 0; i < word.length; i++) {

			// state = delta[state][word[i] - '0'];
            state = delta[state][charMapping(word[i])];
		}
		return state;
	}

	public int charMapping(char c) {

		int number = -1;

		for (int i = 0; i < sigma.length; i++) {
			
			if (c == sigma[i]) {
				
				number = i;
			}
		}
		
		return number;
	}

	public boolean isAccepted(String word) {

		boolean accepted = false;

		if (Q_a[deltaStar(q_0, word.toCharArray())]) {

			accepted = true;
		}

		return accepted;
	}

	public static void main(String[] args) {

		boolean quit = false;
		String end = null;

		Automaton automat = new Automaton();
		automat.sizeOfSigma();
		automat.sigma();
		automat.sizeOfQ();
		automat.delta();
		automat.acceptedState();
		automat.startState();

		while (!quit) {

			System.out.println("Bitte geben Sie das zu prüfende Wort ein.");

			if (automat.isAccepted(automat.eingabe())) {

				System.out.println("Das Wort wurde akzeptiert");
			} else {

				System.out.println("Das Wort wurde nicht akzeptiert");
			}

			System.out.println("Weiter? (j/n)");
			end = automat.eingabe();

			if (end.toLowerCase().equals("n")) {

				quit = true;
			}
		}
	}
}
