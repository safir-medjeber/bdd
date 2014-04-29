import java.sql.SQLException;
import java.util.Scanner;

public class Interface {
	static Scanner in = new Scanner(System.in);
	static ConnectionBase connection;

	public static void main(String[] args) {
		int choix;
		Scanner sc  = new Scanner(System.in);
		if (args.length != 1)
			usage();

		try {
			String password = PasswordField.readPassword("Entrer votre mot de passe pour vous connecter a Postgres: ");
			connection = new ConnectionBase(args[0], password);

			printMenu();

			choix = sc.nextInt();
			evalMenu(choix);

		} catch (SQLException e) {
			System.out.println(e);
		}
	}



	public static void printMenu() {
		System.out.println("Veuillez entrez votre choix : ");
		System.out.println("------------------------------");
		System.out.println("0 - Fin");
		System.out.println("1 - Effectuer une recherche");
		System.out.println("2 - Se connecter");
		System.out.println("3 - S'inscrire");
		System.out.println("------------------------------");
	}

	public static void evalMenu(int choice) throws SQLException {
		Scanner sc = new Scanner(System.in);
		switch (choice) {
		case 0:
			System.exit(0);
			break;
		case 1:
			InterfaceRecherche.printListeCritere();
			String choixCrit = sc.nextLine();
			InterfaceRecherche.evalChoixCrit(choixCrit);
			break;
			
		case 2:
			System.out.print("login: ");
			String login =sc.nextLine();
			String password = PasswordField.readPassword("password for "
					+ login + ": ");
			//			password = "QCX87SQJ7WX"; //TODO
			if(connection.connecteCompte(login, password))
				System.out.println("Vous etes connecte(e)");
			else
				System.out.println("Erreur dans l'identifiant ou le mot de passe");
			break;
		case 3:
			InterfaceInscription.getPerson();
			System.out.println("Inscription ...");
			break;
		default:
			System.out.println("Erreur");
		}
	}



	
	

	
	
	
	public static void usage() {
		System.out.println("Veuillez entrer votre nom identifiant pour Postgres.");
		System.out.println("usage : java Interface <nomUtilisateur>");
		System.exit(1);
	}

	
	
	public static void ligne(){		
		System.out.println("-----------------------------------------");
	}
	
	

	
}
