import java.sql.ResultSet;
import java.sql.SQLException;

public class Interface {
	static ConnectionBase connection;

	public static void main(String[] args) {
		if (args.length != 1)
			usage();

		try {
			String password = PasswordField
					.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
			connection = new ConnectionBase(args[0], password);

			MenuPrincipal();

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public static void MenuPrincipal() throws SQLException {

		int choix;

		enTete("AirChambreDhotes");
		System.out.println("0 - Retour au menu");
		System.out.println("1 - Effectuer une recherche");
		System.out.println("2 - Se connecter");
		System.out.println("3 - S'inscrire");
		ligne(70);
		choix = ReadTools.readInt();
		evalMenu(choix);
	}

	public static void evalMenu(int choice) throws SQLException {
		switch (choice) {
		case 0:
			return;
		case 1:
			InterfaceRecherche.listeCritere();
			break;

		case 2:
			InterfaceConnecte.testeConnection();
			break;
		case 3:
			InterfaceInscription.getCompte();
			break;
		default:
			System.out.println("Erreur");
		}
	}

	public static void printLogement(ResultSet set) throws SQLException {
		printLogement(set, false);
	}

	public static void printLogement(ResultSet set, boolean withId)
			throws SQLException {
		while (set != null && set.next()) {
			if (withId)
				System.out.println(("ID - " + set.getInt("idLogement")));
			System.out
					.println("Description\t: " + set.getString("description"));
			System.out.println("Type\t\t: " + set.getString("type"));
			System.out.println("Surface\t\t: " + set.getFloat("surface"));
			System.out
					.println("Nbr de pieces\t: " + set.getString("nb_pieces"));
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

	public static void ligne(int n) {
		for (int i = 0; i < n; i++)
			System.out.print('-');
		System.out.println("");
	}

	public static void espace(int n) {
		for (int i = 0; i < n; i++)
			System.out.print(' ');
	}

	public static void efface() {
		System.out.println("\033c");
	}

	public static void enTete(String txt) {
		efface();
		ligne(70);
		espace((70 / 2) - (txt.length() / 2));
		System.out.println(txt);
		ligne(70);
	}

	public static void enTete2(String txt) {
		efface();
		ligne(70);
		espace((70 / 2) - (txt.length() / 2));
		System.out.println(txt);
		ligne(70);
	}
}
