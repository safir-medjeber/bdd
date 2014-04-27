import java.sql.SQLException;
import java.util.Scanner;

public class Interface {
	static Scanner in = new Scanner(System.in);
	static ConnectionBase connection;

	public static void main(String[] args) {
		int choix;
		if (args.length != 1)
			usage();

		try {
			String password = PasswordField
					.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
			connection = new ConnectionBase(args[0], password);

			clear();
			printMenu();
			choix = readInt();
			evalMenu(choix);

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public static void clear() {
		System.out.print("\033c");
	}

	public static void printMenu() {
		System.out.println("Veuillez entrez votre choix : ");
		System.out.println("------------------------------");
		System.out.println("0 - Fin");
		System.out.println("1 - Effectuer une recherche");
		System.out.println("2 - Se connecter");
		System.out.println("3 - S'inscrire");
		System.out.println("------------------------------");
	}

	public static void evalMenu(int choice) {
		switch (choice) {
		case 0:
			System.exit(0);
			break;
		case 1:
			System.out.println("Recherche");
			break;
		case 2:
			System.out.print("login: ");
			String login = readString();
			String password = PasswordField.readPassword("password for "
					+ login + ": ");
			System.out.println("Connexion ... (" + login + " - " + password
					+ ")");
			break;
		case 3:
			getPerson();
			System.out.println("Inscription ...");
			break;
		default:
			System.out.println("Erreur");
		}
	}

	public static void getPerson() {
		String nom, prenom;
		System.out.print("Nom: ");
		nom = readString();
		System.out.print("Prenom: ");
		prenom = readString();
		getAddr();

		// return person
	}

	public static void getAddr() {
		String ville, rue;
		int numero;
		System.out.print("Ville: ");
		ville = readString();
		System.out.print("Numero: ");
		numero = readInt();
		System.out.print("Rue: ");
		rue = readString();

		// return addr;
	}

	public static void usage() {
		System.out
				.println("Veuillez entrer votre nom identifiant pour Postgres.");
		System.out.println("usage : java ChaineHotels <nomUtilisateur>");
		System.exit(1);
	}

	public static int readInt() {
		try {
			return in.nextInt();
		} catch (Exception e) {
			System.out.print(" ↳ Entrez un nombre: ");
			in.next();
			return readInt();
		}
	}

	static public String readString() {
		try {
			return in.next();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
