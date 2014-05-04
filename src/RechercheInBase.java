import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;


public class RechercheInBase {
	static Statement selectCritere;



	public static void selectionCritere(String lieu, String prix, String typeLocation, String surface,  
			String nbPiece, String prestation, Date d1, Date d2, 
			boolean transport, boolean aucunCrit) throws SQLException {

		String cmdbase = "SELECT description, type, surface, nb_pieces, logement.prix , adresse.ville , logement.idlogement FROM Logement " +
				"LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse ";

		String where=" WHERE ";


		String[] decoup;
		selectCritere = ConnectionBase.connection.createStatement();
		String cmd = "SELECT description, type, surface, nb_pieces, prix , ville , idlogement FROM Logement " +
				"LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse";

		if (aucunCrit == true) {
			Printer.printLogement(selectCritere.executeQuery(cmd));
		}

		if (transport == true) {
			String cmdTrans = cmdbase+"RIGHT JOIN Transport on Transport.ville=Adresse.ville";
			System.out.println(cmdTrans);
			Printer.printLogement(selectCritere.executeQuery(cmdTrans));
		}

		else{

			if (lieu != "") {
				decoup = lieu.split(" *, * ");
				where+="(";
				for (int i = 0; i < decoup.length; i++) {
					where += " Adresse.ville='"+ decoup[i] + "' OR "	;
				}
				where+=")";

			}

			if (prix != "") {
				where+="(";

				decoup = prix.split(" * ");
				where+= parseInterval(decoup, "prix");
				where+=")";

			}

			if (typeLocation != "") {
				where+="(";

				decoup = typeLocation.split(" * ");
				where+= parseType(decoup);	
				where+=")";

			}

			if (surface != "") {
				where+="(";
				decoup = surface.split(" * ");
				where+= parseInterval(decoup, "surface");
				where+=")";

			}


			if (nbPiece != "") {
				where+="(";
				decoup = nbPiece.split(" * ");
				where+= parseInterval(decoup, "nb_pieces");	
				where+=")";

			}




			if (prestation != "") {
				decoup = prestation.split(" *, * ");
				String presta= parsePrestation(decoup);
				String cmdPresta=cmdbase+"LEFT JOIN Prestation on Prestation.idlogement=Logement.idlogement "+
						"WHERE "+ presta;
				cmdPresta=cmdPresta.substring(0,cmdPresta.length()-4);
				System.out.println(cmdPresta+"\n\n");
				Printer.printLogement(selectCritere.executeQuery(cmdPresta));

			}


			if(d1!=null && d2!=null){
				String cmdDate=cmd+
						"LEFT JOIN Disponibilite on Logement.idlogement=Disponibilite.idlogement "+
						"WHERE jour<'"+d1+ "' OR jour>'"+d2+"'";
				System.out.println(cmdDate+"\n\n");
				//	Printer.printLogement(selectCritere.executeQuery(cmdDate));

			}


			cmd=cmd.substring(0,cmd.length()-4);
			//System.out.println(cmd);

			//Printer.printLogement(selectCritere.executeQuery(cmd));


			if(!(where.equals(" WHERE "))){
				cmdbase = cmdbase+=where;
				
			}

		}
	}


	public static String parseInterval(String[] decoup, String condition){
		String cmd="";
		if(decoup.length==1){
			if(decoup[0].charAt(0)=='=')
				cmd+= condition+ decoup[0]+ " OR ";
			if(decoup[0].charAt(0)=='>')
				cmd+= condition+ decoup[0] + " OR ";
			if(decoup[0].charAt(0)=='<')
				cmd+= condition+ decoup[0] + " OR ";
		}

		if(decoup.length==2){
			if(decoup[0].charAt(0)=='>')
				cmd+= condition + decoup[0] + " AND ";
			cmd+= condition + decoup[1] + " OR ";
		}

		return cmd;
	}

	public static String parseType(String[] decoup){
		String cmd="";
		for (int i = 0; i < decoup.length; i++) {
			if(decoup[i].equals("0"))
				cmd+= "type='Appartement' OR ";

			if(decoup[i].equals("1"))
				cmd+= "type='Chambre' OR "; 
		}
		return cmd;

	}

	public static String parsePrestation(String[] decoup){
		String cmd="";
		for (int i = 0; i < decoup.length; i++) {
			cmd+= "prestation='" +decoup[i]+ "' OR "; 
		}
		return cmd;

	}


}
