-- 1
SELECT COUNT(*) as nombre_de_logement_disponible FROM Logement 
LEFT JOIN Adresse ON Adresse.idAdresse=Logement.idAdresse 
LEFT JOIN Disponibilite ON Disponibilite.idLgogement != Logement.idLogement
WHERE Logement.type = '0' 
      AND ville='paris'
      AND '2015-04-4'<jour AND jour<'2015-04-08' ;

-- 2
SELECT * FROM Logement
LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse  
LEFT JOIN Prestation on Prestation.idlogement=Logement.idlogement  
LEFT JOIN Disponibilite on Logement.idlogement!=Disponibilite.idlogement  
WHERE ( Adresse.ville='pékin' ) 
      AND (Logement.prix<200 ) 
      AND (prestation='0' ) 
	AND (Logement.idLogement NOT IN ( SELECT idLogement FROM Disponibilite WHERE (  
	('2014-04-01' <jour AND jour<'2014-04-04') OR 
	('2014-04-02' <jour AND jour<'2014-04-05') OR 
	('2014-04-03' <jour AND jour<'2014-04-06') OR 
	('2014-04-04' <jour AND jour<'2014-04-07') OR 
	('2014-04-05' <jour AND jour<'2014-04-08') OR 
	('2014-04-06' <jour AND jour<'2014-04-09') OR 
	('2014-04-07' <jour AND jour<'2014-04-10') OR 
	('2014-04-08' <jour AND jour<'2014-04-11') OR 
	('2014-04-09' <jour AND jour<'2014-04-12') OR 
	('2014-04-10' <jour AND jour<'2014-04-13') OR 
	('2014-04-11' <jour AND jour<'2014-04-14') OR 
	('2014-04-12' <jour AND jour<'2014-04-15') OR 
	('2014-04-13' <jour AND jour<'2014-04-16') OR 
	('2014-04-14' <jour AND jour<'2014-04-17') OR 
	('2014-04-15' <jour AND jour<'2014-04-18') OR 
	('2014-04-16' <jour AND jour<'2014-04-19') OR 
	('2014-04-17' <jour AND jour<'2014-04-20') OR 
	('2014-04-18' <jour AND jour<'2014-04-21') OR 
	('2014-04-19' <jour AND jour<'2014-04-22') OR 
	('2014-04-20' <jour AND jour<'2014-04-23') OR 
	('2014-04-21' <jour AND jour<'2014-04-24') OR 
	('2014-04-22' <jour AND jour<'2014-04-25') OR 
	('2014-04-23' <jour AND jour<'2014-04-26') OR 
	('2014-04-24' <jour AND jour<'2014-04-27') OR 
	('2014-04-25' <jour AND jour<'2014-04-28') OR 
	('2014-04-26' <jour AND jour<'2014-04-29') OR 
	('2014-04-27' <jour AND jour<'2014-04-30') OR 
	('2014-04-28' <jour AND jour<'2014-05-01') )));


-- 3
SELECT AVG(Logement.prix*7) FROM Logement 

-- 4
SELECT AVG(count) FROM ( SELECT COUNT(Logement.idLogement) as count FROM Logement GROUP BY login) t

-- 5
SELECT COUNT(idFacture) FROM Facture 
LEFT JOIN Reservation ON Reservation.idReservation = Facture.idReservation
WHERE '2014-01-01'<Reservation.debut AND Reservation.debut<'2014-01-04'
AND '2014-01-01'<Reservation.fin AND Reservation.fin<'2014-01-04'

-- 6
SELECT AVG(Reservation.debut - Reservation.fin) FROM Facture
LEFT JOIN Reservation ON Reservation.idReservation = Facture.idReservation


-- 7
SELECT AVG(montant) FROM Facture;


--8
SELECT description, type, surface, nb_pieces, logement.prix , adresse.ville , logement.idlogement 
FROM Logement LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse  
LEFT JOIN Prestation ON Prestation.idlogement=Logement.idlogement 
LEFT JOIN Suggestion ON Suggestion.ville = Adresse.ville
WHERE ( Adresse.ville='moscou' )
	AND Suggestion.suggestion LIKE '%kremlin%'
	AND (prestation='2') AND prestation.prix = 0
	AND (Logement.idLogement NOT IN ( SELECT idLogement FROM Disponibilite 
	    WHERE (('2014-07-08' <jour AND jour<'2014-07-13'))))
	AND Adresse.ville IN (SELECT ville FROM Transport 
	    LEFT JOIN Vehicule ON Transport.ville = Vehicule.idTransport 
	    WHERE  ville=Adresse.ville AND (heure=17 OR heure IS NULL) 
	    	   AND (jour='2014-07-08' OR jour IS NULL) 
	    GROUP BY ville HAVING COUNT(Vehicule) < nb_vehicule_libre)
	AND Adresse.ville IN (SELECT ville FROM Transport 
		LEFT JOIN Vehicule ON Transport.ville = Vehicule.idTransport
		WHERE  ville=Adresse.ville AND (heure=13 OR heure IS NULL)
		       AND (jour='2014-07-13' OR jour IS NULL)
		GROUP BY ville 
		HAVING COUNT(Vehicule) < nb_vehicule_libre)
ORDER BY Logement.prix;



--9
SELECT * FROM Logement 
LEFT JOIN Prestation on Prestation.idlogement=Logement.idlogement  
WHERE prestation='3' 
AND  (Logement.idLogement NOT IN ( SELECT idLogement FROM Disponibilite
WHERE ((current_date+1 <jour 
AND (jour<current_date+2 
OR jour<current_date+3 
OR jour<current_date+4 
OR jour<current_date+5
OR jour<current_date+6
OR jour<current_date+7 
OR jour<current_date+8 
OR jour<current_date+9 
OR jour<current_date+10 
OR jour<current_date+11 
OR jour<current_date+12 
OR jour<current_date+13 
OR jour<current_date+14 
OR jour<current_date+14
OR jour<current_date+15 
OR jour<current_date+16 
OR jour<current_date+17 
OR jour<current_date+18 
OR jour<current_date+19 
OR jour<current_date+20 
OR jour<current_date+21 
OR jour<current_date+22 
OR jour<current_date+23 
OR jour<current_date+24 
OR jour<current_date+25 
OR jour<current_date+26 
OR jour<current_date+27 
OR jour<current_date+28 
OR jour<current_date+29 
OR jour<current_date+30)))

))

--10
SELECT * FROM Logement WHERE login IN (
SELECT Login FROM Logement LEFT JOIN Adresse ON Logement.idAdresse = Adresse.idAdresse WHERE ville='rio de janeiro' 
)

--11
SELECT SUM(montant) - 10/100 * SUM(montant) as revenue FROM Facture LEFT JOIN Personne ON idPersonne = idLoueur 
WHERE nom ='bettencourt' AND '2014-01-01'<=datefacture AND datefacture<'2015-01-01' 

--12
SELECT COUNT(Disponibilite.*) as reserve, COUNT(Logement.*) as logemen FROM Disponibilite 
LEFT JOIN Logement ON Logement.idLogement = Disponibilite.idLogement 
LEFT JOIN Adresse ON Adresse.idAdresse = Logement.idAdresse
WHERE ville = 'berlin' AND '2014-01-01'<jour AND jour<'2015-01-01'
