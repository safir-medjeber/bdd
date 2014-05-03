import java.sql.SQLException;

public class InterfaceConnecte {
	public static final int CHAMBRE = 1, APPART = 0;


	public static void testeConnection() throws SQLException{
		System.out.print("login: ");
		String login = Interface.readString();
		String password = PasswordField.readPassword("password for " + login + ": ");
		if (Interface.connection.connecteCompte(login, password)) {
			printConnecter();
			int choice = Interface.readInt();
			evalConnecte(choice, login);
		} 
		else
			System.out.println("Erreur dans l'identifiant ou le mot de passe");
	}


	public static void printConnecter() {
		System.out.println("Veuillez entrez votre choix : ");
		System.out.println("------------------------------");
		System.out.println("0 - Fin");
		System.out.println("1 - Voir mes appartements");
		System.out.println("2 - Modifier un appartement");
		System.out.println("3 - Ajouter un appartement");
		System.out.println("------------------------------");

	}

	public static void evalConnecte(int choice, String login)
			throws SQLException {
		
		switch (choice) {
		case 0:
			System.exit(0);
			break;
		case 1:
			Interface.printLogement(Interface.connection
					.selectAppartement(login));
			break;
		case 2:
			modifAppart(login);
			break;
		case 3:
			getAppartement(login);
			break;
		default:
			System.out.println("Erreur");
		}
	}

	private static void modifAppart(String login) {
		
	}

	private static void getAppartement(String login) throws SQLException {
		String description, prestation;
		int nbPieces, nbChambres = 1, type;
		float surface, prix;
		int[] appart;

		System.out.print("Description: ");
		description = Interface.readString();
		System.out.println("Type d'appartement: ");
		System.out.println("0 - Appartement");
		System.out.println("1 - Chambre d'hotes");
		type = Interface.readInt();
		while (type < 0 || type > 1) {
			System.out.println("Entrer un nombre entre 0 et 1");
			type = Interface.readInt();
		}
		if (type == CHAMBRE) {
			System.out.print("Nombre chambres: ");
			nbChambres = Interface.readInt();
		}
		System.out.print("Prix: ");
		prix = Interface.readFloat();
		System.out.print("Surface: ");
		surface = Interface.readFloat();
		System.out.print("Nombre de pieces: ");
		nbPieces = Interface.readInt();

		System.out.println("--- Adresse ---");
		int n = InterfaceInscription.getAddr();

		appart = Interface.connection.insertAppart(description, type,
				nbChambres, surface, nbPieces, prix, n, login);

		System.out.println("--- Ajouter des prestations ---");
		System.out.println("0 - Petit-déjeuner");
		System.out.println("1 - Repas");
		System.out.println("3 - Visite");
		System.out.println("Autre");
		prestation = Interface.readString();
		while (prestation.length() != 0) {
			System.out.println(prestation.length());
			for (int i : appart)
				Interface.connection.insertPrestation(prestation, prix, i);
			prestation = Interface.readString();
		}
	}

}
