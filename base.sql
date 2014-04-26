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
       idDisponible integer,
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
       idSuggestion integer,
       suggestion varchar(256),
       PRIMARY KEY(idSuggestion)
);


CREATE TABLE Disponibilite(
       idDisponibilite integer,
       jour date,
       PRIMARY KEY(idDisponibilite)
);
       

CREATE TABLE Photo(
       idPhoto integer,
       image varchar(64),
       PRIMARY KEY(idPhoto)
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
       PRIMARY KEY(idLogement)
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
	
