import java.sql.ResultSet;
import java.sql.SQLException;

public class Printer {

	public static void printLogement(ResultSet set) throws SQLException {
		System.out.println();
		System.out.println();
		while (set != null && set.next()) {
			System.out.println(("Reference\t: " + set.getInt("idLogement")));
			System.out
					.println("Description\t: " + set.getString("description"));
			System.out.println("Type\t\t: " + set.getString("type"));
			System.out
					.println("Surface\t\t: " + set.getFloat("surface") + "m2");
			System.out
					.println("Nbr de pieces\t: " + set.getString("nb_pieces"));
			System.out.println("Prix journalier\t: " + set.getFloat("prix")
					+ " €");
			System.out.println("Situe a\t\t: " + set.getString("ville"));
			Interface.ligne(Interface.largeurEcran);
			System.out.println();
		}
		ReadTools.readString();

	}

	public static void printPrestation(ResultSet set) throws SQLException {
		String prestation;
		while (set != null && set.next()) {
			System.out.println("Id - " + set.getInt("idPrestation"));
			prestation = set.getString("prestation");
			if (prestation.equals("0"))
				prestation = "Petit-dejeuner";
			else if (prestation.equals("1"))
				prestation = "Dejeuner";
			else if (prestation.equals("2"))
				prestation = "Diner";
			System.out.println("Prestation \t: " + prestation);
			System.out.println("Prix \t\t: " + set.getFloat("prix"));
		}
	}

	public static void printReductionPeriode(ResultSet set) throws SQLException {
		while (set != null && set.next()) {
			System.out.println("Id - " + set.getInt("idReduction"));
			System.out.println("Pourcentage \t: " + set.getInt("pourcentage"));
			System.out.println("Periode \t: " + set.getDate("debut") + " �� "
					+ set.getDate("fin"));
		}
	}

	public static void printReductionDuree(ResultSet set) throws SQLException {
		while (set != null && set.next()) {
			System.out.println("Id - " + set.getInt("idReduction"));
			System.out.println("Pourcentage \t: " + set.getInt("pourcentage"));
			System.out.println("Duree Minimum \t: " + set.getInt("duree_min")
					+ " jours");
		}
	}

	public static void printPhoto(ResultSet set) throws SQLException {
		while (set != null && set.next()) {
			System.out.println("_________________"
					+ //
					"\n|     /  0  \\    |"
					+ //
					"\n|    /_______\\   |"
					+ //
					"\n|    | []  _ |   |"
					+ //
					"\n|____|____| ||___|" + "Photo num "
					+ set.getInt("idPhoto"));
		}
	}

	public static float printPrix(ResultSet prix, ResultSet prestations,
			ResultSet pourcentage_duree) throws SQLException {
		float n = 0;
		int pourcentage;
		if (prix != null && prix.next()) {
			n = prix.getFloat("prix_sejour");
			System.out.println("Prix de base : " + prix.getFloat("prix_jour"));
			System.out.println("Prix du sejour : "
					+ n);
		}
		if (prestations != null && prestations.next()) {
			pourcentage =  prestations.getInt("prix_prestations");
			System.out.println("Prix des prestation : "
					+ pourcentage);
		}
		while (pourcentage_duree != null && pourcentage_duree.next()) {
			System.out
					.println("POurcentage appliquable : " + pourcentage_duree);
		}
		return n;
	}
}
