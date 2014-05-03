import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class RechercheInBase {
	static Statement selectCritere;



	public static void selectionCritere(String lieu, String prix,String surface, String typeLocation, 
			String nbPiece, String prestation, String dates, boolean aucunCrit) throws SQLException {

		selectCritere = ConnectionBase.connection.createStatement();
		String cmd = "";
		if (aucunCrit == true) {
			cmd = "SELECT description, type, surface, nb_pieces, prix , ville FROM Logement LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse";
			Interface.printLogement(selectCritere.executeQuery(cmd));

		}
		
		if (lieu != "") {
			String[] decoup;
			decoup = lieu.split(",");
			System.out.println(decoup[0]+"lj");
			for (int i = 0; i < decoup.length; i++) {
				cmd = "SELECT description, type, surface, nb_pieces, prix, ville FROM Logement "
						+ "LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse "
						+ "WHERE Adresse.ville='glendon'";
						//+ decoup[i] + "'glendon'";
				Interface.printLogement(selectCritere.executeQuery(cmd));

			}
		}

		if (prix != "") {
			cmd = "SELECT * FROM Logement WHERE prix=" + prix;
			selectCritere.execute(cmd);
		}
		if (surface != "") {
			cmd = "SELECT * FROM Logement WHERE surface=" + surface;
			selectCritere.execute(cmd);
		}
		if (nbPiece != "") {
			cmd = "SELECT * FROM Logement WHERE nbPiece="+nbPiece;
			selectCritere.execute(cmd);
		}

		if (prestation != "") {
			String[] decoup;
			decoup = lieu.split(",");
			for (int i = 0; i < decoup.length; i++) {
				cmd = "SELECT * FROM Logement WHERE Adresse.ville='"
						+ decoup[i] + "'";
				selectCritere.execute(cmd);
			}
		}

	}

}
