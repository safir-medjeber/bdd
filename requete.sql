-- 1
SELECT * FROM Logement LEFT JOIN Adresse ON Adresse.idAdresse=Logement.idAdresse 
LEFT JOIN Disponibilite ON Disponibilite.idLgogement != Logement.idLogement
WHERE Logement.type = '0' AND ville='paris'
AND '2015-04-4'<jour AND jour<'2015-04-08' ;

-- 2
SELECT * FROM Logement
LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse  
LEFT JOIN Prestation on Prestation.idlogement=Logement.idlogement  
LEFT JOIN Disponibilite on Logement.idlogement!=Disponibilite.idlogement  
WHERE ( Adresse.ville='pékin' ) AND (Logement.prix<200 ) AND (prestation='0' ) 
AND (jour<'2014-04-01' AND jour<'2014-05-03') -- TODO


-- 3
SELECT AVG(Logement.prix) FROM Logement 

-- 4
SELECT AVG(COUNT(Logement.idLogement)) FROM Logement GROUP BY login

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
SELECT * FROM Logement 
LEFT JOIN Adresse on Logement.idAdresse = Adresse.idAdresse  
LEFT JOIN Prestation on Prestation.idlogement=Logement.idlogement  
WHERE ( Adresse.ville='moscou' ) 
AND (type='Appartement') 
AND (prestation='2' ) 
AND (Logement.idLogement NOT IN ( SELECT idLogement FROM Disponibilite WHERE (('2014-07-08' <jour AND jour<'2014-07-13'))))


--9
SELECT * FROM Logement 
LEFT JOIN Prestation on Prestation.idlogement=Logement.idlogement  
WHERE prestation='3' 
AND  (Logement.idLogement NOT IN ( SELECT idLogement FROM Disponibilite
WHERE ((current_date+1 <jour 
AND (jour<current_date+2 OR jour<current_date+3 OR jour<current_date+4 OR jour<current_date+5 OR jour<current_date+6 OR jour<current_date+7 OR jour<current_date+8 OR jour<current_date+9 OR jour<current_date+10 OR jour<current_date+11 OR jour<current_date+12 OR jour<current_date+13 OR jour<current_date+14 OR jour<current_date+14 OR jour<current_date+15 OR jour<current_date+16 OR jour<current_date+17 OR jour<current_date+18 OR jour<current_date+19 OR jour<current_date+20 OR jour<current_date+21 OR jour<current_date+22 OR jour<current_date+23 OR jour<current_date+24 OR jour<current_date+25 OR jour<current_date+26 OR jour<current_date+27 OR jour<current_date+28 OR jour<current_date+29 OR jour<current_date+30)))

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
