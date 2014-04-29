import java.sql.ResultSet;
import java.sql.SQLException;


public class InterfaceConnecte {

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
		}
	}

}
