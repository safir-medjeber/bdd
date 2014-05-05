import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.Calendar;

public class ConnectionBase {

	static Connection connection;
	private PreparedStatement selectCompte, selectLogin, selectVille,
			insertVille, insertAdresse, insertPerson, insertCompte,
			selectAppartById, selectAppartByLogin, selectAppartByLoginAndId,
			insertAppart, insertPrestation, insertPhoto, insertReduction,
			insertDureeReduction, insertPeriodeReduction,
			selectPrestationOfLogement, selectPhotoOfLogement,
			selectDureeReductionOfLogement, selectPeriodeReductionOfLogement,
			reserveLogement, delReduction, delPrestation, delPhoto,
			logementIsDisponible, transportIsDisponible;
	PreparedStatement selectVilleOfLogement;

	static Statement selectCritere;

	public ConnectionBase(String user, String password) throws SQLException {
		connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/base", user, "base");
		String query;
		query = "SELECT login FROM Compte WHERE login=? AND password=?";
		selectCompte = connection.prepareStatement(query);

		query = "SELECT login FROM Compte WHERE login=?";
		selectLogin = connection.prepareStatement(query);

		query = "SELECT ville FROM Ville WHERE ville=?";
		selectVille = connection.prepareStatement(query);

		query = "INSERT INTO Ville (ville) VALUES (?)";
		insertVille = connection.prepareStatement(query);

		query = "INSERT INTO Adresse(pays,cp,numero,rue,ville) VALUES (?,?,?,?,?)";
		insertAdresse = connection.prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS);

		query = "INSERT INTO Personne (nom, prenom, mail, idAdresse) VALUES (?,?,?,?)";
		insertPerson = connection.prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS);

		query = "INSERT INTO Compte (login, password, idPersonne) VALUES (?,?,?)";
		insertCompte = connection.prepareStatement(query);

		query = "SELECT idLogement, description, type, surface, nb_pieces, prix, ville FROM Logement LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse WHERE login=?";
		selectAppartByLogin = connection.prepareStatement(query);

		query = "SELECT idLogement FROM Logement LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse WHERE login=? AND idLogement=?";
		selectAppartByLoginAndId = connection.prepareStatement(query);

		query = "SELECT idLogement FROM Logement WHERE idLogement=?";
		selectAppartById = connection.prepareStatement(query);

		query = "INSERT INTO Logement (Description, type, surface, nb_pieces, prix, idAdresse, login) VALUES (?,?,?,?,?,?,?)";
		insertAppart = connection.prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS);

		query = "INSERT INTO Prestation (prestation, prix, idLogement) VALUES (?,?,?)";
		insertPrestation = connection.prepareStatement(query);

		query = "INSERT INTO Photo (image, idLogement) VALUES (?,?)";
		insertPhoto = connection.prepareStatement(query);

		query = "INSERT INTO Reduction (pourcentage, idLogement) VALUES (?,?)";
		insertReduction = connection.prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS);

		query = "INSERT INTO Reduction_duree (duree_min, idReduction) VALUES (?,?)";
		insertDureeReduction = connection.prepareStatement(query);

		query = "INSERT INTO Reduction_periode (debut, fin, idReduction) VALUES (?,?,?)";
		insertPeriodeReduction = connection.prepareStatement(query);

		query = "SELECT idPrestation, prestation, prix FROM Prestation WHERE idLogement=?";
		selectPrestationOfLogement = connection.prepareStatement(query);

		query = "SELECT idPhoto, image FROM Photo WHERE idLogement=?";
		selectPhotoOfLogement = connection.prepareStatement(query);

		query = "SELECT Reduction.idReduction, pourcentage, duree_min FROM Reduction_duree "
				+ "LEFT JOIN Reduction ON Reduction_duree.idReduction = Reduction.idReduction "
				+ "WHERE idLogement=?";
		selectDureeReductionOfLogement = connection.prepareStatement(query);

		query = "SELECT Reduction.idReduction, pourcentage, debut, fin FROM Reduction_periode "
				+ "LEFT JOIN Reduction ON Reduction_periode.idReduction = Reduction.idReduction "
				+ "WHERE idLogement=?";
		selectPeriodeReductionOfLogement = connection.prepareStatement(query);

		query = "INSERT INTO Disponibilite (jour, idLogement) VALUES (?,?)";
		reserveLogement = connection.prepareStatement(query);

		query = "DELETE FROM Reduction WHERE idReduction=?";
		delReduction = connection.prepareStatement(query);

		query = "DELETE FROM Prestation WHERE idPrestation=?";
		delPrestation = connection.prepareStatement(query);

		query = "DELETE FROM Photo WHERE idPhoto=?";
		delPhoto = connection.prepareStatement(query);

		query = "SELECT ville FROM Logement LEFT JOIN Adresse ON Logement.idAddresse = Adresse.idAdresse "
				+ "WHERE idLogement=?";
		selectVilleOfLogement = connection.prepareStatement(query);

		query = "SELECT idLogement FROM Disponibilite WHERE ?<jour AND jour<?";
		logementIsDisponible = connection.prepareStatement(query);

		query = "SELECT nb_vehicle_libre - COUNT(Vehicule.*) as vehicules FROM Transport "
				+ "LEFT JOIN Vehicule ON Transport.ville = Vehicule.ville "
				+ "WHERE jour=? AND heure=? AND ville=?"
				+ "GROUP BY ville";
		transportIsDisponible = connection.prepareStatement(query);
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
		selectAppartByLogin.setString(1, login);

		return selectAppartByLogin.executeQuery();
	}

	public boolean selectAppartement(int id) throws SQLException {
		selectAppartById.setInt(1, id);

		return selectAppartById.executeQuery().next();
	}

	public boolean selectAppartement(String login, int logement)
			throws SQLException {
		selectAppartByLoginAndId.setString(1, login);
		selectAppartByLoginAndId.setInt(2, logement);

		return selectAppartByLoginAndId.executeQuery().next();
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
		insertPhoto.setString(1, path);
		setInt(insertPhoto, 2, appart);

		insertPhoto.executeUpdate();
	}

	private int insertReduction(int reduc, int appart) throws SQLException {
		insertReduction.setInt(1, reduc);
		insertReduction.setInt(2, appart);

		return getGeneratedKey(insertReduction);
	}

	public void insertPeriodeReduction(int i, int reduc, Date debut, Date fin)
			throws SQLException {
		int idReduc = insertReduction(reduc, i);

		insertPeriodeReduction.setDate(1, debut);
		insertPeriodeReduction.setDate(2, fin);
		insertPeriodeReduction.setInt(3, idReduc);

		insertPeriodeReduction.executeUpdate();
	}

	public void reservePeriode(int logement, Date debut, Date fin)
			throws SQLException {
		Calendar c = Calendar.getInstance();
		while (debut.before(fin)) {
			reserveLogement.setDate(1, debut);
			reserveLogement.setInt(2, logement);

			reserveLogement.executeUpdate();
			c.setTimeInMillis(debut.getTime());
			c.add(Calendar.DATE, 1);
			debut = new Date(c.getTimeInMillis());
		}

	}

	public ResultSet selectPrestationOf(int logement) throws SQLException {
		return selectById(logement, selectPrestationOfLogement);
	}

	public ResultSet selectPhotoOf(int logement) throws SQLException {
		return selectById(logement, selectPhotoOfLogement);
	}

	public ResultSet selectPeriodeReductionOf(int logement) throws SQLException {
		return selectById(logement, selectPeriodeReductionOfLogement);
	}

	public ResultSet selectDureeReductionOf(int logement) throws SQLException {
		return selectById(logement, selectDureeReductionOfLogement);
	}

	public void delReduction(int id) throws SQLException {
		delById(id, delReduction);
	}

	public void delPrestation(int id) throws SQLException {
		delById(id, delPrestation);
	}

	public void delPhoto(int id) throws SQLException {
		delById(id, delPhoto);
	}

	private static void delById(int id, PreparedStatement p)
			throws SQLException {
		p.setInt(1, id);
		p.executeUpdate();
	}

	private static ResultSet selectById(int id, PreparedStatement p)
			throws SQLException {
		p.setInt(1, id);
		return p.executeQuery();
	}

	public void insertDureeReduction(int i, int reduc, int duree)
			throws SQLException {
		int idReduc = insertReduction(reduc, i);

		insertDureeReduction.setInt(1, duree);
		insertDureeReduction.setInt(2, idReduc);

		insertDureeReduction.executeUpdate();
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

	public boolean selectCompte(String login) throws SQLException {
		selectLogin.setString(1, login);

		return selectLogin.executeQuery().next();
	}

	public boolean logementIsDisponible(int idLogement, Date debut, Date fin)
			throws SQLException {
		logementIsDisponible.setDate(1, debut);
		logementIsDisponible.setDate(1, fin);

		return !logementIsDisponible.executeQuery().next();
	}

	public boolean transportIsDisponible(int idLogement, Date jour, int heure, String ville) throws SQLException {
		transportIsDisponible.setDate(1, jour);
		transportIsDisponible.setInt(2, heure);
		transportIsDisponible.setString(3, ville);
		
		ResultSet set = transportIsDisponible.executeQuery();
		if(!set.next())
			return false;
		return set.getInt("vehicules") > 0;
	}

//	public ResultSet selectVilleOf(int idLogement) {
//
//	}
}