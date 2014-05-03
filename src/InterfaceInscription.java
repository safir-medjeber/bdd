import java.sql.SQLException;


public class InterfaceInscription {
	
	public static void getCompte() throws SQLException   {
		String login, password;
		int n;
		
		n = getPerson();
		System.out.print("login: ");
		login = ReadTools.readString();
		password = PasswordField.readPassword("password: ");
		Interface.connection.insertCompte(login, password, n);
	}
	
	public static int getPerson() throws SQLException {
		String nom, prenom, mail;
		int adresse;

		System.out.print("Nom: ");
		nom = ReadTools.readString();
		System.out.print("Prenom: ");
		prenom = ReadTools.readString();
		System.out.print("Mail: ");
		mail = ReadTools.readString();
		adresse = getAddr();

		return Interface.connection.insertPerson(nom, prenom, mail, adresse);
	}

	public static int getAddr() throws SQLException {
		String pays, cp, ville, rue;
		int numero;
		System.out.print("Pays: ");
		pays = ReadTools.readString();
		System.out.print("Code Postal: ");
		cp = ReadTools.readString();
		System.out.print("Ville: ");
		ville = ReadTools.readString();
		System.out.print("Numero: ");
		numero = ReadTools.readInt();
		System.out.print("Rue: ");
		rue = ReadTools.readString();

		return Interface.connection.insertAdresse(pays, cp, numero, rue, ville);
	}

}
