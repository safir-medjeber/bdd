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
			selectPersonne, insertVille, insertAdresse, insertPerson,
			insertCompte, selectAppartById, selectAppartByLogin,
			selectAppartByLoginAndId, insertAppart, insertPrestation,
			insertPhoto, insertReduction, insertDureeReduction,
			insertPeriodeReduction, selectPrestationOfLogement,
			selectPhotoOfLogement, selectDureeReductionOfLogement,
			selectPeriodeReductionOfLogement, reserveLogement, delReduction,
			delPrestation, delPhoto, logementIsDisponible,
			transportIsDisponible, insertReservation, insertVehicule,
			insertReservationPrestation, countFactureOfReservation,
			getReservationProprietaire, getReservationLocataire,
			getPrix, getPrixPrestation, getPourcentageDure, insertFacture;

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

		query = "SELECT idPersonne FROM Personne WHERE nom=? AND prenom=? AND mail=?";
		selectPersonne = connection.prepareStatement(query);

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

		query = "SELECT ville FROM Logement LEFT JOIN Adresse ON Logement.idAdresse = Adresse.idAdresse "
				+ "WHERE idLogement=?";
		selectVilleOfLogement = connection.prepareStatement(query);

		query = "SELECT idLogement FROM Disponibilite WHERE ?<jour AND jour<?";
		logementIsDisponible = connection.prepareStatement(query);

		query = "SELECT ville as vehicules FROM Transport "
				+ "WHERE ville=?"
				+ "AND nb_vehicule_libre > (SELECT Count(Vehicule.idTransport) FROM Vehicule "
				+ "WHERE idTransport=? AND jour=? AND heure=?)";
		transportIsDisponible = connection.prepareStatement(query);

		query = "INSERT INTO Reservation(date_reservation, debut, fin, idPersonne, idLogement) "
				+ "VALUES (CURRENT_DATE,?,?,?,?)";
		insertReservation = connection.prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS);

		query = "INSERT INTO Vehicule(jour, heure, idReservation, idTransport) VALUES (?,?,?,?)";
		insertVehicule = connection.prepareStatement(query);

		query = "INSERT INTO PrestationOfReservation (idPrestation, idReservation) VALUES (?,?)";
		insertReservationPrestation = connection.prepareStatement(query);

		query = "SELECT COUNT(*) as factures FROM Facture "
				+ "WHERE idPayeur=? " + "AND datefacture>current_date-182";
		countFactureOfReservation = connection.prepareStatement(query);

		query = "SELECT Compte.idPersonne FROM Personne LEFT JOIN Compte ON Compte.idPersonne = Personne.idPersonne "
				+ "LEFT JOIN Logement ON Logement.login = Compte.login "
				+ "LEFT JOIN Reservation ON Reservation.idLogement = Logement.idLogement "
				+ "WHERE idReservation=?";
		getReservationProprietaire = connection.prepareStatement(query);

		query = "SELECT idPersonne FROM Reservation WHERE idReservation=?";
		getReservationLocataire = connection.prepareStatement(query);

		query = "SELECT Logement.prix as prix_jour, "
				+ "Logement.prix * (fin - debut) as prix_sejour "
				+ "FROM Reservation "
				+ "LEFT JOIN Logement ON Logement.idLogement = Reservation.idReservation "
				+ "WHERE idReservation=?";
		getPrix = connection.prepareStatement(query);

		query = "SELECT SUM(prix) as prix_prestations FROM prestationofreservation as por "
				+ "LEFT JOIN Prestation ON por.idPrestation = Prestation.idPrestation "
				+ "WHERE idReservation=?";
		getPrixPrestation = connection.prepareStatement(query);
		
		query = "SELECT pourcentage FROM Reservation LEFT JOIN Reduction ON Reduction.idLogement = Reservation.idLogement "
				+ "LEFT JOIN Reduction_Duree ON Reduction_Duree.idReduction = Reduction.idreduction "
				+ "WHERE Reduction_duree.duree_min > fin-debut "
				+ "AND Reduction.idReduction = ?";
		getPourcentageDure = connection.prepareStatement(query);
		
		query = "INSERT INTO Facture (datefacture, montant, idpayeur, idloueur, idReservation) "
				+ "Values(current_date, ?,?,?,?)";
		insertFacture = connection.prepareStatement(query);
				
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
		logementIsDisponible.setDate(2, fin);

		return !logementIsDisponible.executeQuery().next();
	}

	public boolean transportIsDisponible(int idLogement, Date jour, int heure,
			String ville) throws SQLException {
		transportIsDisponible.setString(1, ville);
		transportIsDisponible.setString(2, ville);
		transportIsDisponible.setDate(3, jour);
		transportIsDisponible.setInt(4, heure);

		ResultSet set = transportIsDisponible.executeQuery();
		return set.next();
	}

	public String selectVilleOf(int idLogement) throws SQLException {
		selectVilleOfLogement.setInt(1, idLogement);

		ResultSet set = selectVilleOfLogement.executeQuery();
		set.next();
		return set.getString("ville");
	}

	public int insertReservation(Date debut, Date fin, int personne,
			int idLogement) throws SQLException {

		insertReservation.setDate(1, debut);
		insertReservation.setDate(2, fin);
		insertReservation.setInt(3, personne);
		insertReservation.setInt(4, idLogement);

		return getGeneratedKey(insertReservation);
	}

	public void insertVehicule(Date jour, int heure, int idReservation,
			String ville) throws SQLException {
		insertVehicule.setDate(1, jour);
		insertVehicule.setInt(2, heure);
		insertVehicule.setInt(3, idReservation);
		insertVehicule.setString(4, ville);

		insertVehicule.executeUpdate();
	}

	public void insertReservationPrestation(int idReservation, int idReduction)
			throws SQLException {
		insertReservationPrestation.setInt(1, idReduction);
		insertReservationPrestation.setInt(2, idReservation);

		insertReservationPrestation.executeUpdate();
	}

	public int selectPersonne(String nom, String prenom, String mail)
			throws SQLException {
		selectPersonne.setString(1, nom);
		selectPersonne.setString(2, prenom);
		selectPersonne.setString(3, mail);

		ResultSet set = selectPersonne.executeQuery();
		if (set.next())
			return set.getInt("idPersonne");
		return -1;
	}

	public int countFactureOf(int idPersonne) throws SQLException {
		countFactureOfReservation.setInt(1, idPersonne);

		ResultSet set = countFactureOfReservation.executeQuery();

		if (set.next())
			return set.getInt("factures");
		else
			return 0;
	}

	int getReservationLocataire(int idReservation) throws SQLException {
		getReservationLocataire.setInt(1, idReservation);

		ResultSet set = getReservationLocataire.executeQuery();

		if (set.next())
			return set.getInt("idPersonne");
		else
			return -1;
	}

	public int getReservationProprietaire(int idReservation)
			throws SQLException {
		getReservationProprietaire.setInt(1, idReservation);

		ResultSet set = getReservationProprietaire.executeQuery();
		if (set.next())
			return set.getInt("idPersonne");
		return -1;
	}

	public ResultSet getPrix(int idReservation) throws SQLException {
		getPrix.setInt(1, idReservation);

		return getPrix.executeQuery();
	}

	public ResultSet getPrixPrestation(int idReservation) throws SQLException{
		getPrixPrestation.setInt(1, idReservation);
		
		return getPrixPrestation.executeQuery();
	}
	
	public ResultSet getPourcentageDure(int Reduction) throws SQLException{
		getPourcentageDure.setInt(1, Reduction);
		
		return getPourcentageDure.executeQuery();
	}
	
	public boolean reservationExiste(int idReservation) throws SQLException {
		getReservationLocataire.setInt(1, idReservation);

		return getReservationLocataire.executeQuery().next();
	}

	public void insertFacture(float n, int personne, int n2, int idReservation) throws SQLException {
		insertFacture.setFloat(1, n);
		insertFacture.setInt(2, personne);
		insertFacture.setInt(3, n2);
		insertFacture.setInt(4, idReservation);
	
		insertFacture.executeUpdate();
	}
	
	

}
