-- 1
SELECT * FROM Logement LEFT JOIN Adresse ON Adresse.idAdresse=Logement.idAdresse 
LEFT JOIN Disponibilite ON Disponibilite.idLgogement != Logement.idLogement
WHERE Logement.type = '0' AND ville='paris'
      AND '2015-04-4'<jour AND jour<'2015-04-08' ;

-- 2


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

-- 8


-- 9


-- 10


-- 11


-- 12


