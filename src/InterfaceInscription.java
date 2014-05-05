import java.sql.SQLException;

public class InterfaceInscription {

	public static void getCompte() throws SQLException {
		String login, password;
		int n;
		Interface.enTete("Inscription");
		System.out.print("login: ");
		login = ReadTools.readString();
		if (Interface.connection.selectCompte(login))
			ReadTools.continuer("Le login est déjà utilisé");
		else {
			password = PasswordField.readPassword("password: ");
			n = getPerson();
			Interface.connection.insertCompte(login, password, n);
		}
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
		
		int n = Interface.connection.selectPersonne(nom, prenom, mail);
		if(n != -1)
			return n;
		adresse = getAddr();

		return Interface.connection.insertPerson(nom, prenom, mail, adresse);
	}

	public static int getAddr() throws SQLException {
		String pays, cp, ville, rue;
		int numero;
		
		Interface.enTete2("Adresse");
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
