import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

public class RechercheInBase {
	static Statement selectCritere;

	public static void selectionCritere(String lieu, String prix,
			String typeLocation, String surface, String nbPiece,
			String prestation, Date d1, Date d2, boolean transport,
			boolean aucunCrit) throws SQLException {

		int n = 0;
		String cmd = "SELECT description, type, surface, nb_pieces, logement.prix , adresse.ville , logement.idlogement FROM Logement "
				+ "LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse ";

		String where = " WHERE ";

		String[] decoup;
		selectCritere = ConnectionBase.connection.createStatement();

		if (aucunCrit == true) {
			Printer.printLogement(selectCritere.executeQuery(cmd));
		}

		else {
			if (lieu != "") {
				n++;
				decoup = lieu.split(" *, * ");
				where += "(";
				int i = 0;
				for (i = 0; i < decoup.length - 1; i++)
					where += " Adresse.ville='" + decoup[i] + "' OR ";
				where += " Adresse.ville='" + decoup[i] + "' ";
				where += ")";
			}
			if (prix != "") {
				if (n++ > 0)
					where += " AND ";
				where += "(";
				decoup = prix.split(" * ");
				where += parseInterval(decoup, "prix");
				where += ")";
			}
			if (typeLocation != "") {
				if (n++ > 0)
					where += " AND ";
				where += "(";
				decoup = typeLocation.split(" * ");
				where += parseType(decoup);
				where += ")";
			}
			if (surface != "") {
				if (n++ > 0)
					where += " AND ";
				where += "(";
				decoup = surface.split(" * ");
				where += parseInterval(decoup, "surface");
				where += ")";
			}
			if (nbPiece != "") {
				if (n++ > 0)
					where += " AND ";
				where += "(";
				decoup = nbPiece.split(" * ");
				where += parseInterval(decoup, "nb_pieces");
				where += ")";
			}
			if (prestation != "") {
				decoup = prestation.split(" *, * ");
				String presta = parsePrestation(decoup);
				cmd += " LEFT JOIN Prestation on Prestation.idlogement=Logement.idlogement ";
				if (n++ > 0)
					where += " AND ";
				where += "(";
				where += presta;
				where += ")";
			}

			if (d1 != null && d2 != null) {
				cmd += " LEFT JOIN Disponibilite on Logement.idlogement=Disponibilite.idlogement ";
				if (n++ > 0)
					where += " AND ";
				where += "(";
				where += " jour<'" + d1 + "' AND jour<'" + d2 + "'";
				where += ")";
			}

			if (transport == true)
				cmd += " RIGHT JOIN Transport on Transport.ville=Adresse.ville ";

			if (!(where.equals(" WHERE "))) {
				cmd += where;

			}
			Printer.printLogement(selectCritere.executeQuery(cmd));
		}
	}

	public static String parseInterval(String[] decoup, String condition) {
		String cmd = "";
		if (decoup.length == 1) {
			if (decoup[0].charAt(0) == '=')
				cmd += condition + decoup[0] + " ";
			if (decoup[0].charAt(0) == '>')
				cmd += condition + decoup[0] + " ";
			if (decoup[0].charAt(0) == '<')
				cmd += condition + decoup[0] + " ";
		}

		if (decoup.length == 2) {
			if (decoup[0].charAt(0) == '>')
				cmd += condition + decoup[0] + " AND ";
			cmd += condition + decoup[1] + " ";
		}

		return cmd;
	}

	private static String parseType(String type) {
		if (type.equals("0"))
			return "type='Appartement'";

		if (type.equals("1"))
			return "type='Chambre'";
		return "";
	}

	public static String parseType(String[] decoup) {
		String cmd = "";
		int i;
		for (i = 0; i < decoup.length-1; i++) {
			cmd += parseType(decoup[i]) + " OR ";
		}
		cmd += parseType(decoup[i]);
		return cmd;

	}

	public static String parsePrestation(String[] decoup) {
		String cmd = "";
		int i;
		for (i = 0; i < decoup.length - 1; i++) {
			cmd += "prestation='" + decoup[i] + "' OR ";
		}
		cmd += "prestation='" + decoup[i] + "' ";
		return cmd;

	}

}
