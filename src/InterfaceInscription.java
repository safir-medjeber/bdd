import java.sql.SQLException;
import java.util.Scanner;


public class InterfaceInscription {
	
	static Scanner in = new Scanner(System.in);
	public static int getPerson() throws SQLException {
		String nom, prenom, mail;
		int adresse;

		System.out.print("Nom: ");
		nom = readString();
		System.out.print("Prenom: ");
		prenom = readString();
		System.out.print("Mail: ");
		mail = readString();
		adresse = getAddr();

		return Interface.connection.insertPerson(nom, prenom, mail, adresse);
		// return person
	}

	public static int getAddr() throws SQLException {
		String pays, cp, ville, rue;
		int numero;
		System.out.print("Pays: ");
		pays = readString();
		System.out.print("Code Postal: ");
		cp = readString();	
		System.out.print("Ville: ");
		ville = readString();
		System.out.print("Numero: ");
		numero = readInt();
		System.out.print("Rue: ");
		rue = readString();

		return Interface.connection.insertAdresse(pays, cp, numero, rue, ville);
	}
	
	
	static public String readString() {
		try {
			return in.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int readInt() {
		try {
			String s = in.nextLine();
			return Integer.parseInt(s);
		} catch (Exception e) {
			System.out.print(" ↳ Entrez un nombre: ");
			return readInt();
		}
	}

	
}
