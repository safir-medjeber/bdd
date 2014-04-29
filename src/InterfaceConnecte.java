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
			printLogement(Interface.connection.selectAppartement(login));
			break;
		}
	}

	private static void printLogement(ResultSet set) throws SQLException {
		while (set != null && set.next()) {
			Interface.print(set.getString("description"), 15);
			Interface.print(String.valueOf(set.getInt("nb_pieces")), 10);
			Interface.print(set.getString("ville"), 10);
		}
	}


}
