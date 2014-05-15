import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class RechercheInBase {
	static Statement selectCritere;

	public static void selectionCritere(String lieu, String prix,
			String typeLocation, String surface, String nbPiece,
			String prestation, Date debut, Date fin, int duree,
			boolean transport, int depart, int arrive, boolean aucunCrit)
			throws SQLException {

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
					where += "\n\tAND ";
				where += "(";
				decoup = prix.split(" * ");
				where += parseInterval(decoup, "Logement.prix");
				where += ")";
			}
			if (typeLocation != "") {
				if (n++ > 0)
					where += "\n\tAND ";
				where += "(";
				decoup = typeLocation.split(" * ");
				where += parseType(decoup);
				where += ")";
			}
			if (surface != "") {
				if (n++ > 0)
					where += "\n\tAND ";
				where += "(";
				decoup = surface.split(" * ");
				where += parseInterval(decoup, "surface");
				where += ")";
			}
			if (nbPiece != "") {
				if (n++ > 0)
					where += "\n\tAND ";
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
					where += "\n\tAND ";
				where += "(";
				where += presta;
				where += ")";
			}

			if (debut != null && fin != null) {
				Date tmpDebut, tmpFin;
				if (n++ > 0)
					where += "\n\tAND ";
				where += "(";
				where += "Logement.idLogement NOT IN ( SELECT idLogement FROM Disponibilite WHERE ( ";

				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(debut.getTime());
				tmpDebut = new Date(c.getTimeInMillis());
				c.add(Calendar.DATE, duree);
				tmpFin = new Date(c.getTimeInMillis());
				while (tmpFin.before(fin)) {
					System.out.println("ok");
					where += " \n('" + tmpDebut + "' <jour AND jour<'" + tmpFin
							+ "'" + ") OR";
					c.setTimeInMillis(tmpDebut.getTime());
					c.add(Calendar.DATE, 1);
					tmpDebut = new Date(c.getTimeInMillis());
					c.add(Calendar.DATE, duree);
					tmpFin = new Date(c.getTimeInMillis());
				}
				where = where.substring(0, where.length() - 2);
				where += ")))";

				if (transport == true) {
					if (n++ > 0)
						where += "\n\tAND ";

					where += generateTransport(debut, depart);
					where += "\n\tAND ";
					where += generateTransport(fin, arrive);

				}
			}

			if (!(where.equals(" WHERE "))) {
				cmd += where;

			}
				System.out.println(cmd);
			Printer.printLogement(selectCritere.executeQuery(cmd));
		}
	}

	private static String generateTransport(Date date, int heure) {
		return "Adresse.ville IN ("
				+ "SELECT ville FROM Transport "
				+ "LEFT JOIN Vehicule ON Transport.ville = Vehicule.idTransport "
				+ "WHERE  ville=Adresse.ville " + "AND (heure=" + heure
				+ " OR heure IS NULL) " + "AND (jour='" + date
				+ "' OR jour IS NULL) " + "GROUP BY ville "
				+ "HAVING COUNT(Vehicule) < nb_vehicule_libre)";
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
			return "type='0'";

		if (type.equals("1"))
			return "type='1'";
		return "";
	}

	public static String parseType(String[] decoup) {
		String cmd = "";
		int i;
		for (i = 0; i < decoup.length - 1; i++) {
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
