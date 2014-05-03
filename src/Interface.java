import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Interface {
	static Scanner in = new Scanner(System.in);
	static ConnectionBase connection;
	static int largeurEcran = 70;
	
	public static void main(String[] args) {
		if (args.length != 2)
			usage();

		try {
		//	String password = PasswordField.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
			connection = new ConnectionBase(args[0], args[1]);


			MenuPrincipal();

			

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public static void MenuPrincipal() throws SQLException {

		int choix;

		enTete("AirChambreDhotes");
		System.out.println("0 - Quitter l'application");
		System.out.println("1 - Effectuer une recherche");
		System.out.println("2 - Se connecter");
		System.out.println("3 - S'inscrire");
		ligne(largeurEcran);
		choix = readInt();
		while(choix > 3){
			System.out.print(" ↳ Entrez un nombre: ");
			choix = readInt();			
		}
		evalMenu(choix);
	}

	public static void evalMenu(int choice) throws SQLException {
		String login, password;
		String choixCrit;
		
		switch (choice) {

		case 0:
			System.exit(0);
			break;

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
		while (set != null && set.next()) {
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

	public static int readInt() {
		try {
			String s = in.nextLine();
			return Integer.parseInt(s);
		} catch (Exception e) {
			System.out.print(" ↳ Entrez un nombre: ");
			return readInt();
		}
	}

	public static String readString() {
		try {
			return in.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
		ligne(largeurEcran);
		espace((largeurEcran / 2) - (txt.length() / 2));
		System.out.println(txt);
		ligne(largeurEcran);
	}

	public static void enTete2(String txt) {
		ligne(largeurEcran);
		espace((largeurEcran / 2) - (txt.length() / 2));
		System.out.println(txt);
		ligne(largeurEcran);
	}

	static public float readFloat() {
		try {
			String s = in.nextLine();
			return Float.parseFloat(s);
		} catch (Exception e) {
			System.out.print(" ↳ Entrez un nombre: ");
			return readInt();
		}
	}
}
