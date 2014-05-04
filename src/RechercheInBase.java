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


		String[] decoup;
		selectCritere = ConnectionBase.connection.createStatement();
		String cmd = "SELECT description, type, surface, nb_pieces, prix , ville FROM Logement " +
				"LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse";

		if (aucunCrit == true) {
			Printer.printLogement(selectCritere.executeQuery(cmd));
		}


		cmd+= " WHERE ";

		System.out.println(nbPiece);
		if (lieu != "") {
			decoup = lieu.split(" *, * ");
			for (int i = 0; i < decoup.length; i++) {
				cmd += " Adresse.ville='"+ decoup[i] + "' OR "	;
			}
		}

		if (prix != "") {
			decoup = prix.split(" * ");
			cmd+= parseInterval(decoup, "prix");		
		}

		if (prix != "") {
			decoup = prix.split(" * ");
			cmd+= parseInterval(decoup, "prix");		
		}

		if (typeLocation != "") {
			System.out.println("pk");
			decoup = typeLocation.split(" * ");
			cmd+= parseType(decoup);		
		}

		if (surface != "") {
			decoup = surface.split(" * ");
			cmd+= parseInterval(decoup, "surface");	
		}


		if (nbPiece != "") {
			decoup = nbPiece.split(" * ");
			cmd+= parseInterval(decoup, "nb_pieces");	
		}

		if (prestation != "") {
			decoup = lieu.split(" *, *");
		}


		if(d1!=null && d2!=null){
			String cmdDate="SELECT description, type, surface, nb_pieces, prix , ville FROM Logement "+
					"LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse "+
					"LEFT JOIN Disponibilite on Logement.idlogement=Disponibilite.idlogement "+
					"WHERE jour<'"+d1+ "' OR jour>'"+d2+"'";
			System.out.println(cmdDate);
			Printer.printLogement(selectCritere.executeQuery(cmdDate));

		}
		
		
		cmd=cmd.substring(0,cmd.length()-4);
		System.out.println(cmd);

		//Interface.printLogement(selectCritere.executeQuery(cmd));


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
			System.out.println(cmd + "lol" + decoup[i]+"m");
	}
		return cmd;

	}


}
