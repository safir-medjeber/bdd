import java.sql.SQLException;

public class InterfaceRecherche {

	public static void listeCritere() throws SQLException {
		String choix;

		Interface.enTete("Choix des criteres de recherche ");
		System.out.println("0 - Retour au menu");
		System.out.println("1 - Ville(s)");
		System.out.println("2 - Prix");
		System.out.println("3 - Type location");
		System.out.println("4 - Surface");
		System.out.println("5 - Nombre de piece(s)");
		System.out.println("6 - Prestation(s)");
		System.out.println("7 - Date");
		System.out.println("8 - Aucun");
		Interface.ligne(Interface.largeurEcran);

		choix = ReadTools.readString();
		while (testEntreeMenu(choix, 7) == false) {
			System.out.println(" ↳ Entrer une requete de la forme: 1 3 5");
			choix = ReadTools.readString();
		}
		Interface.efface();
		evalChoixCrit(choix);
	}

	public static void evalChoixCrit(String choix) throws SQLException{
		String[] decoup = choix.split(" ");	
		String lieu="";
		String prix="";
		String typeLocation="";
		String surface="";
		String nbPiece="";
		String prestation="";
		String dates="";
		boolean aucun=false;

		for (int i = 0; i < decoup.length; i++) {
			switch (Integer.parseInt(decoup[i])) {

			case 0:
				Interface.MenuPrincipal();
				break;

			case 1:
				Interface.enTete2("Specification d'une ou plusieurs ville(s)");
				System.out.println("0 - Usage");
				Interface.ligne(Interface.largeurEcran);
				lieu=ReadTools.readString();
				if(testEntreeMenu(lieu, 1)==true){
					System.out.println(" ↳ Entrer une requete de la forme: Paris, Lyon, Bordeaux ");
					lieu=ReadTools.readString();
				}
				break;

			case 2:
				Interface.enTete2("Specification du prix");
				System.out.println("0 - Usage");
				Interface.ligne(Interface.largeurEcran);

				prix=ReadTools.readString();

				while(testEntreeInterval(prix)==false){
					System.out.println(" ↳ Entrer une requete de la forme: >200 ou <300 ou =400 ou >140 <200");
					prix=ReadTools.readString();
				}
				break;

			case 3:
				Interface.enTete2("Specification du type de location");
				System.out.println("0 - Appartement");
				System.out.println("1 - Maison");
				Interface.ligne(Interface.largeurEcran);
				
				typeLocation= ReadTools.readString();

				while(testEntreeMenu(typeLocation, 2)==false){
					System.out.println(" ↳ Entrer une requete de la forme: 0 ou 1 ou 0 1");
					typeLocation= ReadTools.readString();
				}
				typeLocation = evalTypeLocation(typeLocation);
				
				
				
			case 4:
				Interface.enTete2("Specification de la surface");
				System.out.println("0 - Usage");
				Interface.ligne(Interface.largeurEcran);

				surface=ReadTools.readString();

				while(testEntreeInterval(surface)==false){
					System.out.println(" ↳ Entrer une requete de la forme: >20 ou <40 ou =45 ou >20 <50");
					surface=ReadTools.readString();
				}
				break;

			case 5:
				Interface.enTete2("Specification du nombre de pieces");
				System.out.println("0 - Usage");
				Interface.ligne(Interface.largeurEcran);

				nbPiece=ReadTools.readString();

				while(testEntreeInterval(nbPiece)==false){
					System.out.println(" ↳ Entrer une requete de la forme: >2 ou <4 ou =5 ou >5 <8");
					nbPiece=ReadTools.readString();
				}
				break;

			case 6:
				Interface.enTete2("Specification des prestations");
				System.out.println("0 - Petit Dejeuner");
				System.out.println("1 - Dejeuner");
				System.out.println("2 - Diner");
				System.out.println("3 - Visites");
				Interface.ligne(Interface.largeurEcran);
				
				prestation= ReadTools.readString();
				while(testEntreeMenu(prestation, 3)==false){
					System.out.println(" ↳ Entrer une requete de la forme: 0 3");
					prestation= ReadTools.readString();
				}
				prestation = evalPrestation(prestation);
				break;

			case 7:
				Interface.enTete2("Specification date de depart et date de retour");
				System.out.println("0 - Usage");
				Interface.ligne(Interface.largeurEcran);

				dates=ReadTools.readString();

				while(testDateFormat(dates)==false){
					System.out.println(" ↳ Entrer une requete de la forme: yyyy-MM-dd   yyyy-MM-dd");
					dates=ReadTools.readString();
				}
				break;
			case 8:
				aucun = true;
				break;

			default:
				System.out.println("Erreur swich recherche");

			}
			RechercheInBase.selectionCritere(lieu, prix, surface, typeLocation, nbPiece, prestation, dates, aucun);

		}

	}

	public static String evalTypeLocation(String s) {
		String typeLocation = "";
		String[] decoup = s.split(" ");
		for (int i = 0; i < decoup.length; i++) {
			switch (Integer.parseInt(decoup[i])) {
			case 0:
				typeLocation += "appartement";
				break;
			case 1:
				typeLocation += "maison";
			}
		}
		return typeLocation;
	}

	public static String evalPrestation(String s){
		String prestation="";
		String visites;
		String[] decoup = s.split(" ");	
		for (int i = 0; i < decoup.length; i++) {

			switch(Integer.parseInt(decoup[i])){
			case 0:
				prestation+="petit dejeuner,";
				break;
			case 1:
				prestation +="dejeuner,";
				break;
			case 2:
				prestation+="diner,";
				break;
			case 3:
				Interface.enTete2("Specification d'une ou plusieurs visites");
				System.out.println("0 - Usage");
				Interface.ligne(Interface.largeurEcran);
				visites=ReadTools.readString();
				if(testEntreeMenu(visites, 1)==true){
					System.out.println(" ↳ Entrer une requete de la forme: Louvres, Tour Eiffel, ");
					visites=ReadTools.readString();
				}
				prestation+=visites;
				break;
			}
		}

		return prestation;
	}

	public static boolean isEntier(String s) {
		try {
			int i = Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean testDateFormat(String entree) {
		try {
			String[] decoup = entree.split(" ");
			if (decoup.length == 2) {
				String d1 = decoup[0];
				String d2 = decoup[1];
				java.sql.Date.valueOf(d1);
				java.sql.Date.valueOf(d2);

				return true;
			}
		} catch (Exception e) {
			return false;

		}
		return false;
	}

	public static boolean testEntreeInterval(String entree) {
		try {
			String[] decoup = entree.split(" ");
			if (decoup.length == 1) {
				{
					if (decoup[0].charAt(0) == '='
							|| decoup[0].charAt(0) == '>'
							|| decoup[0].charAt(0) == '<') {
						if (isEntier(decoup[0].substring(1)))
							return true;
						else
							return false;
					} else
						return false;
				}
			}

			if (decoup.length == 2) {
				if (decoup[0].charAt(0) == '>' && decoup[1].charAt(0) == '<') {
					if (isEntier(decoup[0].substring(1))
							&& isEntier(decoup[0].substring(1)))
						return true;
					else
						return false;
				} else
					return false;

			}
			return false;

		} catch (Exception e) {
			return false;

		}

	}

	public static boolean testEntreeMenu(String entree, int tailleMenu) {
		String[] decoup = entree.split(" ");
		for (int i = 0; i < decoup.length; i++) {
			if (isEntier(decoup[i]) == false)
				return false;
			if (Integer.parseInt(decoup[i]) > tailleMenu)
				return false;
		}
		return true;

	}

}
