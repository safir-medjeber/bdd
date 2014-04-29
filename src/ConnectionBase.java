import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionBase {

	private Connection connection;
	private PreparedStatement selectCompte, selectVille, insertVille,
			insertAdresse, insertPerson, insertCompte, selectAppart;
	static Statement selectCritere;

	public ConnectionBase(String user, String password) throws SQLException {
		connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/base", user, "base");

		String connectionQuery = "SELECT login FROM Compte WHERE login=? AND password=?";
		selectCompte = connection.prepareStatement(connectionQuery);

		String selectVilleQuery = "SELECT ville FROM Ville WHERE ville=?";
		selectVille = connection.prepareStatement(selectVilleQuery);

		String insertVilleQuery = "INSERT INTO Ville (ville) VALUES (?)";
		insertVille = connection.prepareStatement(insertVilleQuery);

		String insertAdresseQuery = "INSERT INTO Adresse(pays,cp,numero,rue,ville) VALUES (?,?,?,?,?)";
		insertAdresse = connection.prepareStatement(insertAdresseQuery,
				Statement.RETURN_GENERATED_KEYS);

		String insertPersonQuery = "INSERT INTO Personne (nom, prenom, mail, idAdresse) VALUES (?,?,?,?)";
		insertPerson = connection.prepareStatement(insertPersonQuery,
				Statement.RETURN_GENERATED_KEYS);

		String insertCompteQuery = "INSERT INTO Compte (login, password, idPersonne) VALUES (?,?,?)";
		insertCompte = connection.prepareStatement(insertCompteQuery);

		String selectAppartQuery = "SELECT description, type, surface, nb_pieces, prix, ville FROM Logement LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse";
		selectAppart = connection.prepareStatement(selectAppartQuery);
	}

	public void close() throws SQLException {
		connection.close();
	}

	public boolean connecteCompte(String login, String password)
			throws SQLException {
		selectCompte.setString(1, login);
		selectCompte.setString(2, password);

		return selectCompte.executeQuery().next();
	}

	public int insertAdresse(String pays, String cp, int numero, String rue,
			String ville) throws SQLException {
		ville = ville.toLowerCase();
		selectVille.setString(1, ville);
		if (!selectVille.executeQuery().next()) {
			insertVille.setString(1, ville);
			insertVille.execute();
		}

		insertAdresse.setString(1, pays);
		insertAdresse.setString(2, cp);
		insertAdresse.setInt(3, numero);
		insertAdresse.setString(4, rue);
		insertAdresse.setString(5, ville);

		return getGeneratedKey(insertAdresse);
	}

	public int insertPerson(String nom, String prenom, String mail, int adresse)
			throws SQLException {
		insertPerson.setString(1, nom);
		insertPerson.setString(2, prenom);
		insertPerson.setString(3, mail);
		insertPerson.setInt(4, adresse);

		return getGeneratedKey(insertPerson);
	}

	public void insertCompte(String login, String password, int n)
			throws SQLException {
		insertCompte.setString(1, login);
		insertCompte.setString(2, password);
		insertCompte.setInt(3, n);

		insertCompte.executeUpdate();
	}

	public void selectionCritere(String lieu, String prix, String surface,
			String nbPiece, String prestations, boolean aucunCrit)
			throws SQLException {

		selectCritere = connection.createStatement();
		String cmd = "";
		if (aucunCrit == true)
			cmd = "SELECT * FROM Logement";

		selectCritere.execute(cmd);
}

	private static int getGeneratedKey(PreparedStatement prStatement)
			throws SQLException {
		int n = prStatement.executeUpdate();
		ResultSet set = prStatement.getGeneratedKeys();
		if (set.next()) {
			n = set.getInt(1);
		}
		return n;
	}

	public ResultSet selectAppartement(String login) throws SQLException {
		return selectAppart.executeQuery();
	}

}