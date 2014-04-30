import java.sql.SQLException;


public class InterfaceInscription {
	
	public static void getCompte() throws SQLException   {
		String login, password;
		int n;
		
		n = getPerson();
		System.out.print("login: ");
		login = Interface.readString();
		password = PasswordField.readPassword("password: ");
		Interface.connection.insertCompte(login, password, n);
	}
	
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
