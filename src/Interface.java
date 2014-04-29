import java.sql.ResultSet;
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
			String password = PasswordField.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
			connection = new ConnectionBase(args[0], password);

			printMenu();

			choix = readInt();
			evalMenu(choix);

		} catch (SQLException e) {
			System.out.println(e);
		}
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
		String login, password;
		String choixCrit;
		switch (choice) {
		case 0:
			System.exit(0);
			break;
		case 1:
			InterfaceRecherche.printListeCritere();
			choixCrit = readString();
			InterfaceRecherche.evalChoixCrit(choixCrit);
			break;

		case 2:
			System.out.print("login: ");
			login = readString();
			password = PasswordField.readPassword("password for " + login
					+ ": ");
			// password = "QCX87SQJ7WX"; //TODO
			if (connection.connecteCompte(login, password)) {
				InterfaceConnecte.printConnecter();
				choice = readInt();
				InterfaceConnecte.evalConnecte(choice, login);
			} else
				System.out
						.println("Erreur dans l'identifiant ou le mot de passe");
			break;
		case 3:
			int n = InterfaceInscription.getPerson();
			System.out.print("login: ");
			login = readString();
			password = PasswordField.readPassword("password: ");
			connection.insertCompte(login, password, n);
			break;
		default:
			System.out.println("Erreur");
		}
	}
	
	public static void printLogement(ResultSet set) throws SQLException {
		while (set != null && set.next()) {
			System.out.println("Description\t: " + set.getString("description"));
			System.out.println("Type\t\t: " +	set.getString("type"));
			System.out.println("Surface\t\t: " + set.getFloat("surface"));
			System.out.println("Nbr de pieces\t: " + set.getString("nb_pieces"));
			System.out.println("Prix\t\t: " + set.getFloat("prix"));
			System.out.println("Situe a\t\t: " + set.getString("ville"));
			Interface.ligne(30);
		}
	}

	public static void usage() {
		System.out
				.println("Veuillez entrer votre nom identifiant pour Postgres.");
		System.out.println("usage : java Interface <nomUtilisateur>");
		System.exit(1);
	}

	public static int readInt() {
		try {
			String s = in.nextLine();
			return Integer.parseInt(s);
		} catch (Exception e) {
			System.out.print(" ↳ Entrez un nombre: ");
					return readInt();
		}
	}

	public static void ligne(int n) {
		for(int i = 0; i < n; i++)
			System.out.print('-');
		System.out.println("");
	}

	static public String readString() {
		try {
			return in.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void print(String s, int i) {
		System.out.print(s);
		for (i -= s.length(); i >= 0; i--)
			System.out.print(" ");
	}

}
