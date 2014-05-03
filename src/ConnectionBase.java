import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

public class ConnectionBase {

	static Connection connection;
	private PreparedStatement selectCompte, selectVille, insertVille,
			insertAdresse, insertPerson, insertCompte, selectAppart,
			insertAppart, insertPrestation, insertPhoto, insertReduction,
			insertDureeReduction, insertPeriodeReduction;
	static Statement selectCritere;
	
	
	public ConnectionBase(String user, String password) throws SQLException {
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/base", user, "base");

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

		String selectAppartQuery = "SELECT idLogement, description, type, surface, nb_pieces, prix, ville FROM Logement LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse WHERE login=?";
		selectAppart = connection.prepareStatement(selectAppartQuery);

		String insertAppartQuery = "INSERT INTO Logement (Description, type, surface, nb_pieces, prix, idAdresse, login) VALUES (?,?,?,?,?,?,?)";
		insertAppart = connection.prepareStatement(insertAppartQuery,
				Statement.RETURN_GENERATED_KEYS);

		String insertPrestationQuery = "INSERT INTO Prestation (prestation, prix, idLogement) VALUES (?,?,?)";
		insertPrestation = connection.prepareStatement(insertPrestationQuery);

		String insertPhotoQuery = "INSERT INTO Photo (image, idLogement) VALUES (?,?)";
		insertPhoto = connection.prepareStatement(insertPhotoQuery);
		
		String insertReductionQuery = "INSERT INTO Reduction (pourcentage, idLogement) VALUES (?,?)";
		insertReduction = connection.prepareStatement(insertReductionQuery);
		
		String insertDureeReductionQuery = "INSERT INTO Reduction_duree (duree_min, idReduction) VALUES (?,?)";
		insertDureeReduction = connection.prepareStatement(insertDureeReductionQuery);
		
		String insertPeriodeReductionQuery = "INSERT INTO Reduction_periode (debut, fin, idRecution) VALUES (?,?,?)";
		insertPeriodeReduction = connection.prepareStatement(insertPeriodeReductionQuery);
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
		setInt(insertAdresse, 3, numero);
		insertAdresse.setString(4, rue);
		insertAdresse.setString(5, ville);

		return getGeneratedKey(insertAdresse);
	}

	public int insertPerson(String nom, String prenom, String mail, int adresse)
			throws SQLException {
		insertPerson.setString(1, nom);
		insertPerson.setString(2, prenom);
		insertPerson.setString(3, mail);
		setInt(insertPerson, 4, adresse);

		return getGeneratedKey(insertPerson);
	}

	public void insertCompte(String login, String password, int n)
			throws SQLException {
		insertCompte.setString(1, login);
		insertCompte.setString(2, password);
		setInt(insertCompte, 3, n);

		insertCompte.executeUpdate();
	}

	
	public ResultSet selectAppartement(String login) throws SQLException {
		selectAppart.setString(1, login);

		return selectAppart.executeQuery();
	}

	public int[] insertAppart(String description, int type, int nbChambres,
			float surface, int nbPieces, float prix, int idAdresse, String login)
					throws SQLException {
		String s = "";
		int[] t = new int[nbChambres];

		for (int i = 0; i < nbChambres; i++) {
			if (type == InterfaceConnecte.CHAMBRE)
				s = "Chambre " + i + " - ";
			insertAppart.setString(1, s + description);
			setInt(insertAppart, 2, type);
			setFloat(insertAppart, 3, surface);
			setInt(insertAppart, 4, nbPieces);
			setFloat(insertAppart, 5, prix);
			setInt(insertAppart, 6, idAdresse);
			insertAppart.setString(7, login);

			t[i] = getGeneratedKey(insertAppart);
		}
		return t;
	}

	public void insertPrestation(String prestation, float prix, int appart)
			throws SQLException {
		insertPrestation.setString(1, prestation);
		setFloat(insertPrestation, 2, prix);
		setInt(insertPrestation, 3, appart);

		insertPrestation.executeUpdate();
	}

	public void insertPhoto(int appart, String path) throws SQLException {
		setInt(insertPhoto, 1, appart);
		insertPhoto.setString(2, path);

		insertPhoto.executeUpdate();
	}

	private int insertReduction(int reduc, int appart) throws SQLException{
		insertReduction.setInt(1, reduc);
		insertReduction.setInt(2, appart);
		
		return getGeneratedKey(insertDureeReduction);
	}
	
	public void insertPeriodeReduction(int i, int reduc, Date debut, Date fin) throws SQLException {
		int idReduc = insertReduction(reduc, i);
		
		insertPeriodeReduction.setDate(1, debut);
		insertPeriodeReduction.setDate(2, fin);
		insertPeriodeReduction.setInt(3, idReduc);
		
		insertPeriodeReduction.executeUpdate();
	}

	public void insertDureeReduction(int i, int reduc, int duree) throws SQLException {
		int idReduc = insertReduction(reduc, i);
		
		insertPeriodeReduction.setInt(1, duree);
		insertPeriodeReduction.setInt(2, idReduc);
		
		insertPeriodeReduction.executeUpdate();
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

	public static void setInt(PreparedStatement p, int index, int value)
			throws SQLException {
		if (value != -1)
			p.setInt(index, value);
	}

	private static void setFloat(PreparedStatement p, int index, float value)
			throws SQLException {
		if (value != -1)
			p.setFloat(index, value);
	}
}