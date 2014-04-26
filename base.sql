DROP TABLE Vehicule;
DROP TABLE Transport;
DROP TABLE Ville;
DROP TABLE Suggestion;
DROP TABLE Disponibilite;
DROP TABLE Photo;
DROP TABLE Adresse;
DROP TABLE Logement;
DROP TABLE Compte;
DROP TABLE Prestation;
DROP TABLE Reservation;
DROP TABLE Reduction;
DROP TABLE Reduction_duree;
DROP TABLE Reduction_periode;
DROP TABLE Personne;
DROP TABLE Facture;


CREATE TABLE Vehicule(
       idDisponible serial,
       heure date,
       PRIMARY KEY(idDisponible)
);       


CREATE TABLE Transport(
       nb_vehicule_libre integer,
       prix money
);


CREATE TABLE Ville(
       ville varchar(64)
);


CREATE TABLE Suggestion(
       idSuggestion serial,
       suggestion varchar(256),
       PRIMARY KEY(idSuggestion)
);


CREATE TABLE Disponibilite(
       idDisponibilite serial,
       jour date,
       PRIMARY KEY(idDisponibilite)
);
       

CREATE TABLE Photo(
       idPhoto serial,
       image varchar(64),
       PRIMARY KEY(idPhoto)
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
       PRIMARY KEY(idLogement)
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
	
