import java.sql.SQLException;
import java.util.Scanner;


public class InterfaceRecherche {


	public static  void printListeCritere(){
		System.out.println("\n\nChoix des criteres de recherche ");
		Interface.ligne(25);
		System.out.println("0 - Retour au menu");
		System.out.println("1 - Lieu(x)");
		System.out.println("2 - Prix");
		System.out.println("3 - Surface");
		System.out.println("4 - Nombre de piece(s)");
		System.out.println("5 - Prestation(s)");
		System.out.println("6 - Aucun");
		Interface.ligne(25);
	}


	public static void evalPrestation(String choix){
		String presta="";
		String[] decoup ;
		decoup=choix.split(" ");
		for (int i = 0; i < decoup.length; i++) {
			switch (Integer.parseInt(decoup[i])) {

			case 0:
				presta+=" Petit dejeuner";	
			case 1:
				presta+=" Dejeuner";
			case 2:
				presta+=" Dejeuner";
				break;
			case 3:
				presta+=" Dejeuner";
				break;
			case 4:	
				presta+= " visite";
				break;

			default:
				System.out.println("Erreur");
			}

		}
	}


		public static void evalChoixCrit(String choix) throws SQLException{
			String[] decoup ;
			decoup=choix.split(" ");
			Scanner sc = new Scanner(System.in);
			String lieu="";
			String prix="";
			String surface="";
			String nbPiece="";
			String prestation="";
			boolean aucun=false;

			for (int i = 0; i < decoup.length; i++) {
				switch (Integer.parseInt(decoup[i])) {

				case 0:
					Interface.printMenu();

				case 1:
					System.out.println("\n\nSpecification d'un ou plusieurs lieu(x)");
					System.out.println("Exemples: Paris, Marseilles, Lyon");
					Interface.ligne(25);
					lieu=sc.nextLine();
					break;

				case 2:
					System.out.println("\n\nSpecification Prix");
					Interface.ligne(25);
					System.out.println("Exemples: <150  >150  150");
					prix=sc.nextLine();
					break;

					
				case 3:
					System.out.println("\n\nSpecification de la Surface ");
					Interface.ligne(25);
					System.out.println("Exemples: <20  >20  20");
					surface=sc.nextLine();
					break;

				case 4:
					System.out.println("\n\nSpecification du nombre de pieces");
					Interface.ligne(25);
					System.out.println("Exemples: <2  >3  4");
					nbPiece=sc.nextLine();
					break;

				case 5:
					System.out.println("\n\nChoix des prestions parmi les propositions ");
					System.out.println("0 - Petit Dejeuner");
					System.out.println("1 - Dejeuner");
					System.out.println("2 - Diner");
					System.out.println("3 - Visites");
					Interface.ligne(25);
					prestation=sc.nextLine();
					break;

				case 6:
					aucun = true;
					Interface.connection.selectionCritere(lieu, prix, surface, nbPiece, prestation, aucun);
					break;

				default:
					System.out.println("Erreur");
				}
			}

		}

	}
