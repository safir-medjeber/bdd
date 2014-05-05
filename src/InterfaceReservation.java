import java.awt.List;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InterfaceReservation {

	public static void reservation() throws SQLException {
		int id;

		Interface.enTete("Reservation");
		System.out.println("Entrer l'id de l'appartement");
		id = ReadTools.readInt();

		if (Interface.connection.selectAppartement(id))
			printReservation(id);
		else {
			System.out.println("L'appartement voulu n'existe pas");
		}
	}

	private static void printReservation(int idLogement) throws SQLException {
		Date debut, fin;
		int arrivee, depart;
		boolean transport, prestation;
		int n;
		ArrayList<Integer> prestations = new ArrayList<Integer>();
				
//		ResultSet set = Interface.connection.selectVilleOf(idLogement));
//		set.next();
//		String ville = set.getString("ville");

		System.out.println("Reserve du: ");
		debut = ReadTools.readDate();
		System.out.println("Au: ");
		fin = ReadTools.readDate();

		if (!Interface.connection.logementIsDisponible(idLogement, debut, fin)) {
			ReadTools
					.continuer("Ce logement n'est pas disponibe pour cet periode");
			return;
		}

//		System.out.println("Souhaitez-vous les transport à l'arrivée et au départ ?(y/n)");
//		transport = ReadTools.readYesNo();
//		if (transport) {
//			System.out.println("Quelle est l'heure d'arrivée ?");
//			arrivee = ReadTools.readInt();
//			if (!Interface.connection.transportIsDisponible(idLogement, debut, arrivee)) { 
//				ReadTools.continuer("Transport non disponible pour l'arrivée");
//				return;
//			}
//			System.out.println("Quelle est l'heure de départ ?");
//			depart = ReadTools.readInt();
//			if (!Interface.connection.transportIsDisponible(idLogement, fin, depart)) { 
//				ReadTools.continuer("Transport non disponible pour le départ");
//				return;
//			}
//		}
//		
//		System.out.println("Souhaitez-vous des prestations ? (y/n)");
//		prestation = ReadTools.readYesNo();
//		if(prestation){
//			ResultSet prestationSet = Interface.connection.selectPrestationOf(idLogement);
//			Printer.printPrestation(prestationSet);
//			System.out.println("Entrer les prestations souhaitez: ");
//			n = ReadTools.readEmptyInt();
//			while(n != -1){
//				prestations.add(n);
//				n = ReadTools.readEmptyInt();
//			}
//		}
//		
//		Interface.connection.insertVehicule(debut, arrivee, idReservation)
	}
}
