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

	public static void evalMenu(int choice) throws SQLException {
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
//			password = "QCX87SQJ7WX"; //TODO
			if(connection.connecteCompte(login, password))
				System.out.println("Vous etes connecté");
			else
				System.out.println("Erreur dans l'identifiant ou le mot de passe");
			break;
		case 3:
			getPerson();
			System.out.println("Inscription ...");
			break;
		default:
			System.out.println("Erreur");
		}
	}

	public static int getPerson() throws SQLException {
		String nom, prenom, mail;
		int adresse;
		
		System.out.print("Nom: ");
		nom = readString();
		System.out.print("Prenom: ");
		prenom = readString();
		System.out.print("Mail: ");
		mail = readString();
		adresse = getAddr();

		return connection.insertPerson(nom, prenom, mail, adresse);
		// return person
	}

	public static int getAddr() throws SQLException {
		String pays, cp, ville, rue;
		int numero;
		System.out.print("Pays: ");
		pays = readString();
		System.out.print("Code Postal: ");
		cp = readString();	
		System.out.print("Ville: ");
		ville = readString();
		System.out.print("Numero: ");
		numero = readInt();
		System.out.print("Rue: ");
		rue = readString();

		return connection.insertAdresse(pays, cp, numero, rue, ville);
	}

	public static void usage() {
		System.out
				.println("Veuillez entrer votre nom identifiant pour Postgres.");
		System.out.println("usage : java Interface <nomUtilisateur>");
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
