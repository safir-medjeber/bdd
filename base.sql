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
       idDisponible serial,
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
       idSuggestion serial,
       suggestion varchar(256),
       ville varchar(64),
       PRIMARY KEY(idSuggestion),
       FOREIGN KEY(vill) REFERENCES Ville(ville)
);


CREATE TABLE Disponibilite(
       idDisponibilite serial,
       jour date,
       idLogement integer,
       PRIMARY KEY(idDisponibilite),
       FOREIGN KEY(idLogement) REFERENCES Logement(idLogement)
);
       

CREATE TABLE Photo(
       idPhoto serial,
       image varchar(64),
       idLogement integer,
       PRIMARY KEY(idPhoto),
       FOREIGN KEY(idLogement) REFERENCES Logement(idLogement)
);


CREATE TABLE Adresse(
       idAdresse serial,	
       cp integer,
       numero integer,
       rue varchar(256),
       PRIMARY KEY(idAdresse)
);


CREATE TABLE Logement(
       idLogement serial,
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
       idPersonne integer,
       PRIMARY KEY(login), 
       FOREIGN KEY(idPersonne) REFERENCES Personne(idPersonne)
);


CREATE TABLE Prestation(
       idPrestation serial,
       prestation varchar(32),
       prix money,
       idLogement  integer,
       PRIMARY KEY(idPrestation),
       FOREIGN KEY(idLogement) REFERENCES Personne(idLogement)
  
);	


CREATE TABLE Reservation(
       idReservation serial,
       date_reservation date,
       debut date,
       fin date,   
       idPersonne integer, 
       PRIMARY KEY(idReservation),
       FOREIGN KEY(idPersonne) REFERENCES Personne(idPersonne),
       FOREIGN KEY(idLogement) REFERENCES Personne(idLogement)
);	       


CREATE TABLE Reduction(
       idReduction serial,
       pourcentage integer,
       PRIMARY KEY(idReduction)
);


CREATE TABLE Reduction_duree(
       duree_min integer,
       idReduction integer,
       FOREIGN KEY(idReduction) REFERENCES Reduction(idReduction)
);


CREATE TABLE Reduction_periode(
       debut date,
       fin date,
       idReduction integer,	
       FOREIGN KEY(idReduction) REFERENCES Reduction(idReduction)
);


CREATE TABLE Personne(
       idPersonne integer,
       nom varchar(32),
       prenom varchar(32),
       mail varchar(64),
       idAdresse integer, 
       login integer,    
       PRIMARY KEY(idPersonne),
       FOREIGN KEY(idAdresse) REFERENCES Adresse(idAdresse)
);


CREATE TABLE Facture(
       idFacture serial,
       dateFacture timestamp,		
       montant money, 
       idPayeur integer,
       idLoueur integer,
       idReservation integer,	
       PRIMARY KEY(idFacture),
       FOREIGN KEY(idPayeur, idLoueur) REFERENCES Personne(idPersonne,idPersonne),
       FOREIGN KEY(idReservation) REFERENCES Reservation(idReservation)
);
	
