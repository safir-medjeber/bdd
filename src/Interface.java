import java.sql.SQLException;

public class Interface {
	static ConnectionBase connection;
	static int largeurEcran = 70;

	public static void main(String[] args) {
		if (args.length != 1)
			usage();

		try {
			 String password =
			 PasswordField.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
			connection = new ConnectionBase(args[0], password);
			menuPrincipal();

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public static void menuPrincipal() throws SQLException {

		int choix;

			enTete("AirChambreDhotes");
			System.out.println("0 - Fin");
			System.out.println("1 - Effectuer une recherche");
			System.out.println("2 - Reserver un logement");
			System.out.println("3 - Modifier/Annuler une reservation");
			System.out.println("4 - Se connecter");
			System.out.println("5 - S'inscrire");

			ligne(largeurEcran);
			choix = ReadTools.readInt();
			while (choix < 0 && choix > 3) {
				System.out.print(" ↳ Entrez un nombre: ");
				choix = ReadTools.readInt();
			}
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
			InterfaceReservation.reservation();
			break;
		case 3:
			System.out.println("Entrez l'id de reservation");
			choice = ReadTools.readInt();
			if(connection.reservationExiste(choice))
				InterfaceReservation.payer(choice);
			break;
		case 4:
			InterfaceConnecte.testeConnection();
			break;
		case 5:
			InterfaceInscription.getCompte();
			break;
		default:
			System.out.println("Erreur");
		}
		menuPrincipal();
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
	
	public static void ligne(){
		ligne(largeurEcran);
	}

	public static void espace(int n) {
		for (int i = 0; i < n; i++)
			System.out.print(' ');
	}

	public static void efface() {
		System.out.print("\033c");
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
}
