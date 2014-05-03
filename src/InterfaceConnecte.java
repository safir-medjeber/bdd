import java.sql.SQLException;
import java.sql.Date;

public class InterfaceConnecte {
	public static final int CHAMBRE = 1, APPART = 0;

	public static void testeConnection() throws SQLException {
		System.out.print("login: ");
		String login = ReadTools.readString();
		String password = PasswordField.readPassword("password for " + login
				+ ": ");
		if (Interface.connection.connecteCompte(login, password)) {
			printConnecter();
			int choice = ReadTools.readInt();
			evalConnecte(choice, login);
		} else
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
			Interface.printLogement(
					Interface.connection.selectAppartement(login), true);
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
		int logement;
		System.out.print("Entrer le numero du logement que voulez modifiez");
		System.out.println("(-1 pour annuler) ");
		
		logement = ReadTools.readInt();
		if(logement == -1)
			return;
	}

	private static void getAppartement(String login) throws SQLException {
		String description;
		int nbPieces, nbChambres = 1, type;
		float surface, prix;
		int[] appart;

		System.out.print("Description: ");
		description = ReadTools.readString();
		System.out.println("Type d'appartement: ");
		System.out.println("0 - Appartement");
		System.out.println("1 - Chambre d'hotes");
		type = ReadTools.readInt();
		while (type < 0 || type > 1) {
			System.out.println("Entrer un nombre entre 0 et 1");
			type = ReadTools.readInt();
		}
		if (type == CHAMBRE) {
			System.out.print("Nombre chambres: ");
			nbChambres = ReadTools.readInt();
		}
		System.out.print("Prix: ");
		prix = ReadTools.readFloat();
		System.out.print("Surface: ");
		surface = ReadTools.readFloat();
		System.out.print("Nombre de pieces: ");
		nbPieces = ReadTools.readInt();

		System.out.println("--- Adresse ---");
		int n = InterfaceInscription.getAddr();

		appart = Interface.connection.insertAppart(description, type,
				nbChambres, surface, nbPieces, prix, n, login);
		addPrestation(appart);
		addPhotos(appart);
		addReduction(appart);
	}

	private static void addReduction(int[] appart) throws SQLException {
		int choix;
		int periode;
		Date debut, fin;
		int reduc;

		System.out.println("--- Ajouter des reductions ---");
		System.out.println("↵ - Pour continuer");
		System.out.println("0 - Sur la duree");
		System.out.println("1 - Sur la periode");

		choix = ReadTools.readEmptyInt();
		while (choix != -1) {
			switch (choix) {
			case 0:
				System.out.println("Entrer le pourcentage de reduction");
				reduc = ReadTools.readInt();
				System.out.println("Entrer la date de debut: ");
				debut = ReadTools.readDate();
				System.out.println("Entrer la date de fin: ");
				fin = ReadTools.readDate();
				for (int i : appart)
					Interface.connection.insertPeriodeReduction(i, reduc,
							debut, fin);
				break;
			case 1:
				System.out.println("Entrer le pourcentage de reduction");
				reduc = ReadTools.readInt();
				System.out.println("Entrer une periode en nombre de jour : ");
				periode = ReadTools.readInt();
				for (int i : appart)
					Interface.connection
							.insertDureeReduction(i, reduc, periode);
				break;
			default:
				System.out.println("Entrer un nombre entre 0 et 1");
			}
		}
	}

	private static void addPhotos(int[] appart) throws SQLException {
		String path;
		System.out.println("------- Ajouter des photos -------");
		System.out.println("Entrer un lien de photo par ligne");
		path = ReadTools.readString();
		while (path.length() != 0) {
			for (int i : appart)
				Interface.connection.insertPhoto(i, path);
		}
	}

	private static void addPrestation(int[] appart) throws SQLException {
		String prestation;
		float prix;

		System.out.println("--- Ajouter des prestations ---");
		System.out.println("↵ - Pour continuer");
		System.out.println("0 - Petit-déjeuner");
		System.out.println("1 - Repas");
		System.out.println("3 - Visite");
		System.out.println("    Autre");

		prestation = ReadTools.readString();
		while (prestation.length() != 0) {
			prix = ReadTools.readFloat();
			for (int i : appart)
				Interface.connection.insertPrestation(prestation, prix, i);
			prestation = ReadTools.readString();
		}
	}

}
