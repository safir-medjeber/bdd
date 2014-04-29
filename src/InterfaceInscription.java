import java.sql.SQLException;
import java.util.Scanner;


public class InterfaceInscription {
	
	static Scanner in = new Scanner(System.in);

	public static int getPerson() throws SQLException {
		String nom, prenom, mail;
		int adresse;

		System.out.print("Nom: ");
		nom = Interface.readString();
		System.out.print("Prenom: ");
		prenom = Interface.readString();
		System.out.print("Mail: ");
		mail = Interface.readString();
		adresse = getAddr();

		return Interface.connection.insertPerson(nom, prenom, mail, adresse);
		// return person
	}

	public static int getAddr() throws SQLException {
		String pays, cp, ville, rue;
		int numero;
		System.out.print("Pays: ");
		pays = Interface.readString();
		System.out.print("Code Postal: ");
		cp = Interface.readString();
		System.out.print("Ville: ");
		ville = Interface.readString();
		System.out.print("Numero: ");
		numero = Interface.readInt();
		System.out.print("Rue: ");
		rue = Interface.readString();

		return Interface.connection.insertAdresse(pays, cp, numero, rue, ville);
	}
	
	
}
