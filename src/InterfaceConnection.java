import java.sql.SQLException;


public class InterfaceConnection {




	public static void testeConnection() throws SQLException{
		System.out.print("login: ");
		String login = Interface.readString();
		String password = PasswordField.readPassword("password for " + login + ": ");
		if (Interface.connection.connecteCompte(login, password)) {
			InterfaceConnection.printConnecter();
			int choice = Interface.readInt();
			InterfaceConnection.evalConnecte(choice, login);
		} 
		else
			System.out.println("Erreur dans l'identifiant ou le mot de passe");
	}


	public static void printConnecter() {
		System.out.println("Veuillez entrez votre choix : ");
		System.out.println("------------------------------");
		System.out.println("0 - Fin");
		System.out.println("1 - Voir mes appartements");
		System.out.println("2 - Ajouter un appartement");
		System.out.println("------------------------------");

	}

	public static void evalConnecte(int choice, String login)
			throws SQLException {
		switch (choice) {
		case 0:
			System.exit(0);
			break;
		case 1:
			Interface.printLogement(Interface.connection.selectAppartement(login));
			break;
		case 2: 
			getAppartement(login);
			break;
		default:
			System.out.println("Erreur");
		}
	}

	private static void getAppartement(String login) throws SQLException {
		String description;
		int nbPieces, nbChambres = 1, type;
		float surface, prix;
		
		System.out.print("Description: ");
		description = Interface.readString();
		System.out.println("Type d'appartement: ");
		System.out.println("0 - Appartement");
		System.out.println("1 - Chambre d'hotes");
		type = Interface.readInt();
		while(type < 0 || type > 1){
			System.out.println("Entrer un nombre entre 0 et 1");
			type = Interface.readInt();
		}
		if(type == 1){
			System.out.print("Nombre chambres: ");
			nbChambres = Interface.readInt();
		}
		System.out.println("Prix: ");
		prix = Interface.readFloat();
		System.out.println("Surface: ");
		surface = Interface.readFloat();
		System.out.println("Nombre de pieces: ");
		nbPieces = Interface.readInt();
	
		System.out.println("--- Adresse ---");
		int n = InterfaceInscription.getAddr();

		Interface.connection.insertAppart(description, type, nbChambres, surface, nbPieces, prix, n, login);
	}

}
