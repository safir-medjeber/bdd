import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Set;

public class InterfaceConnecte {
	public static final int CHAMBRE = 1, APPART = 0;

	public static void testeConnection() throws SQLException {
		System.out.print("login: ");
		String login = ReadTools.readString();
		String password = PasswordField.readPassword("password for " + login
				+ ": ");
		if (Interface.connection.connecteCompte(login, password)) {
			printConnecter(login);
		} else
			System.out.println("Erreur dans l'identifiant ou le mot de passe");
	}

	public static void printConnecter(String login) throws SQLException {
		int choix;

		System.out.println("Veuillez entrez votre choix : ");
		System.out.println("------------------------------");
		System.out.println("0 - Retour au menu");
		System.out.println("1 - Voir mes appartements");
		System.out.println("2 - Modifier un appartement");
		System.out.println("3 - Ajouter un appartement");
		System.out.println("------------------------------");

		choix = ReadTools.readInt();
		evalConnecte(choix, login);
	}

	public static void evalConnecte(int choice, String login)
			throws SQLException {

		switch (choice) {
		case 0:
			return;
		case 1:
			Printer.printLogement(Interface.connection.selectAppartement(login));
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
		printConnecter(login);
	}

	private static void modifAppart(String login) throws SQLException {
		int logement;
		System.out.println("Entrer le numero du logement que voulez modifiez");
		System.out.println("(-1 pour annuler) ");

		logement = ReadTools.readInt();
		if (logement == -1)
			return;
		if (Interface.connection.selectAppartement(login, logement)) {
			printModif(login, logement);
		}
	}

	private static void printModif(String login, int logement)
			throws SQLException {
		int choix;

		System.out.println("0   - Retour");
		System.out.println("1/2 - Ajouter/Supprimer des prestations");
		System.out.println("3/4 - Ajoutes/Supprimer des photos");
		System.out.println("5/6 - Ajouter/Supprimer des reductions");
		System.out.println("7   - Reserver une periode");

		choix = ReadTools.readInt();
		switch (choix) {
		case 0:
			return;
		case 1:
			addPrestation(new int[] { logement });
			break;
		case 2:
			suppressionPrestation(logement);
			break;
		case 3:
			addPhotos(new int[] { logement });
			break;
		case 4:
			suppressionPhoto(logement);
			break;
		case 5:
			addReduction(new int[] { logement });
			break;
		case 6:
			suppressionReduction(logement);
			break;
		case 7:
			reservePeriode(logement);
			break;
		default:
			System.out.println("Entrer un chiffre entre 0 et 7");
			break;
		}
		printModif(login, logement);
	}

	private static void reservePeriode(int logement) throws SQLException {
		Date debut, fin;

		System.out.println("Entrer une periode: ");
		System.out.print("Debut - ");
		debut = ReadTools.readDate();
		System.out.print("Fin - ");
		fin = ReadTools.readDate();

		Interface.connection.reservePeriode(logement, debut, fin);
	}

	private static void suppressionReduction(int logement) throws SQLException {
		int id;
		ResultSet periodeSet = Interface.connection
				.selectPeriodeReductionOf(logement);
		ResultSet dureeSet = Interface.connection
				.selectDureeReductionOf(logement);

		Printer.printReductionDuree(dureeSet);
		Printer.printReductionPeriode(periodeSet);

		System.out.print("Entrer l'id à supprimer (ou -1): ");
		id = ReadTools.readInt();
		if (id != -1)
			Interface.connection.delReduction(id);
	}

	private static void suppressionPhoto(int logement) throws SQLException {
		int id;
		ResultSet photoSet = Interface.connection.selectPhotoOf(logement);
		
		Printer.printPhoto(photoSet);
		System.out.print("Entrer l'id à supprimer (ou -1): ");
		id = ReadTools.readInt();
		if (id != -1)
			Interface.connection.delPhoto(id);
	}

	private static void suppressionPrestation(int logement) throws SQLException {
		int id;
		
		ResultSet prestationSet = Interface.connection
				.selectPrestationOf(logement);

		Printer.printPrestation(prestationSet);

		System.out.print("Entrer l'id à supprimer (ou -1): ");
		id = ReadTools.readInt();
		if (id != -1)
			Interface.connection.delPrestation(id);
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

	private static void printReduction() {
		System.out.println("--- Ajouter des reductions ---");
		System.out.println("↵ - Pour continuer");
		System.out.println("0 - Sur la periode");
		System.out.println("1 - Sur la duree");
	}

	private static void addReduction(int[] appart) throws SQLException {
		int choix;
		int periode;
		Date debut, fin;
		int reduc;

		printReduction();

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
			printReduction();
			choix = ReadTools.readEmptyInt();
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
			path = ReadTools.readString();
		}
	}

	private static void addPrestation(int[] appart) throws SQLException {
		String prestation;
		float prix;

		System.out.println("--- Ajouter des prestations ---");
		System.out.println("↵ - Pour continuer");
		System.out.println("0 - Petit-déjeuner");
		System.out.println("1 - Repas");
		System.out.println("2 - Visite");
		System.out.println("    Autre");

		prestation = ReadTools.readString();
		while (prestation.length() != 0) {
			System.out.print("Prix: ");
			prix = ReadTools.readFloat();
			for (int i : appart)
				Interface.connection.insertPrestation(prestation, prix, i);
			prestation = ReadTools.readString();
		}
	}

}

