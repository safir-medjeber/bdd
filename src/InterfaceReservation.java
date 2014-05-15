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
		int arrivee = 0, depart = 0;
		boolean transport, prestation;
		int n;
		ArrayList<Integer> prestations = new ArrayList<Integer>();

		String ville = Interface.connection.selectVilleOf(idLogement);

		System.out.println("Reserve du: ");
		debut = ReadTools.readDate();
		System.out.println("Au: ");
		fin = ReadTools.readDate();

		if (!Interface.connection.logementIsDisponible(idLogement, debut, fin)) {
			ReadTools
					.continuer("Ce logement n'est pas disponibe pour cet periode");
			return;
		}

		System.out
				.println("Souhaitez-vous les transport à l'arrivée et au départ ?(y/n)");
		transport = ReadTools.readYesNo();
		if (transport)
			if (Interface.connection.villeADesTransport(ville)) {
				System.out.println("Quelle est l'heure d'arrivée ?");
				arrivee = ReadTools.readInt();
				if (Interface.connection.transportIsDisponible(idLogement,
						debut, arrivee, ville)) {
					ReadTools
							.continuer("Transport non disponible pour l'arrivée");
					return;
				}
				System.out.println("Quelle est l'heure de départ ?");
				depart = ReadTools.readInt();
				if (Interface.connection.transportIsDisponible(idLogement, fin,
						depart, ville)) {
					ReadTools
							.continuer("Transport non disponible pour le départ");
					return;
				}
			}
			else{
				System.out.println("Cette ville ne dispose pas de ce service");
				transport = false;
			}

		System.out.println("Souhaitez-vous des prestations ? (y/n)");
		prestation = ReadTools.readYesNo();
		if (prestation) {
			ResultSet prestationSet = Interface.connection
					.selectPrestationOf(idLogement);
			Printer.printPrestation(prestationSet);
			System.out.println("Entrer les prestations souhaitez: ");
			n = ReadTools.readEmptyInt();
			while (n != -1) {
				prestations.add(n);
				n = ReadTools.readEmptyInt();
			}
		}

		Interface.connection.reservePeriode(idLogement, debut, fin);
		int idPersonne = InterfaceInscription.getPerson();
		int idReservation = Interface.connection.insertReservation(debut, fin,
				idPersonne, idLogement);
		if (transport) {
			Interface.connection.insertVehicule(debut, arrivee, idReservation,
					ville);
			Interface.connection.insertVehicule(fin, depart, idReservation,
					ville);
		}

		if (prestation) {
			for (int i : prestations)
				Interface.connection.insertReservationPrestation(idReservation,
						i);
		}

		printPayer(idReservation);
	}

	private static void printPayer(int idReservation) throws SQLException {
		Interface.enTete("Payer");
		System.out.println("0 - Payer maintenant");
		System.out.println("1 - Payer plus tard");
		System.out.println("Votre id de resrvation est : " + idReservation);
		int choix = ReadTools.readInt();
		switch (choix) {
		case 0:
			payer(idReservation);
			break;
		case 1:
			ReadTools.continuer("Vous pouvez payer à partir du menu");
			break;
		default:
			printPayer(idReservation);
			break;
		}
	}

	public static void payer(int idReservation) throws SQLException {
		Interface.enTete("Payer");

		int locataire = Interface.connection
				.getReservationLocataire(idReservation);
		int proprietaire = Interface.connection.getReservationProprietaire(idReservation);
		int n = Interface.connection.countFactureOf(locataire);
		Printer.printPrix(Interface.connection.getPrix(idReservation),
				Interface.connection.getPrixPrestation(idReservation),
				Interface.connection.getPourcentageDure(idReservation));
		Interface.connection.insertFacture(n, locataire, proprietaire, idReservation);
		ReadTools.continuer("");
	}
}
