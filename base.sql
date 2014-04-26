DROP TABLE IF EXISTS Vehicule;
DROP TABLE IF EXISTS Transport;
DROP TABLE IF EXISTS Ville;
DROP TABLE IF EXISTS Suggestion;
DROP TABLE IF EXISTS Disponibilite;
DROP TABLE IF EXISTS Photo;
DROP TABLE IF EXISTS Adresse;
DROP TABLE IF EXISTS Logement;
DROP TABLE IF EXISTS Compte;
DROP TABLE IF EXISTS Prestation;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Reduction;
DROP TABLE IF EXISTS Reduction_duree;
DROP TABLE IF EXISTS Reduction_periode;
DROP TABLE IF EXISTS Personne;
DROP TABLE IF EXISTS Facture;


CREATE TABLE Vehicule(
       idDisponible integer,
       heure date,  
       idReservation integer,
       idTransport varchar(64),
       PRIMARY KEY(idDisponible),
       FOREIGN KEY(idReservation) REFERENCES Reservation(idReservation),
       FOREIGN KEY(idTransport) REFERENCES Transport(ville)
);       


CREATE TABLE Transport(
       ville varchar(64),
       nb_vehicule_libre integer,
       prix money,
       PRIMARY KEY(ville),
       FOREIGN KEY(ville) REFERENCES Ville(ville)
);


CREATE TABLE Ville(
       ville varchar(64), 
       PRIMARY KEY(ville)
);


CREATE TABLE Suggestion(
       idSuggestion integer,
       suggestion varchar(256),
       ville varchar(64),
       PRIMARY KEY(idSuggestion),
       FOREIGN KEY(vill) REFERENCES Ville(ville)
);


CREATE TABLE Disponibilite(
       idDisponibilite integer,
       jour date,
       idLogement integer,
       PRIMARY KEY(idDisponibilite),
       FOREIGN KEY(idLogement) REFERENCES Logement(idLogement)
);
       

CREATE TABLE Photo(
       idPhoto integer,
       image varchar(64),
       idLogement integer,
       PRIMARY KEY(idPhoto),
       FOREIGN KEY(idLogement) REFERENCES Logement(idLogement)
);


CREATE TABLE Adresse(
       idAdresse integer,	
       cp integer,
       numero integer,
       rue varchar(256)
);


CREATE TABLE Logement(
       idLogement integer,
       description varchar(512),
       type varchar(16),
       surface real,
       nb_pieces integer,
       prix money,
       idAdresse integer,
       login varchar(32),
       PRIMARY KEY(idLogement),
       FOREIGN KEY(idAdresse) REFERENCES Adresse(idAdresse),
       FOREIGN KEY(login) REFERENCES Compte(login)
);       	    


CREATE TABLE Compte(
       login varchar(32),
       password varchar(63),
       PRIMARY KEY(login)  
);


CREATE TABLE Prestation(
       idPrestation integer,
       prestation varchar(32),
       prix money,
       PRIMARY KEY(idPresatation)  
);	


CREATE TABLE Reservation(
       idReservation integer,
       date_reservation date,
       debut date,
       fin date,    
       PRIMARY KEY(idReservation)
);	       


CREATE TABLE Reduction(
       idReduction integer,
       pourcentage integer,
       PRIMARY KEY(idReduction)
);


CREATE TABLE Reduction_duree(
       duree_min integer
);


CREATE TABLE Reduction_periode(
       debut date,
       fin date
);


CREATE TABLE Personne(
       idPersonne integer,
       nom varchar(32),
       prenom varchar(32),
       mail varchar(64),
       PRIMARY KEY(idPersonne)
);


CREATE TABLE Facture(
       idFacture integer,
       date_facture timestamp,	
       PRIMARY KEY(idFacture)
);
	
