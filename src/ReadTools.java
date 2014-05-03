import java.util.Calendar;
import java.sql.Date;
import java.util.Scanner;

public class ReadTools {
	static Scanner in = new Scanner(System.in);

	public static String readString() {
		try {
			return in.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int readInt() {
		return readInt(false);
	}

	public static int readEmptyInt() {
		return readInt(true);
	}

	private static int readInt(boolean empty) {
		try {
			String s = in.nextLine();
			if (empty && s.length() == 0)
				return -1;
			return Integer.parseInt(s);
		} catch (Exception e) {
			System.out.print(" ↳ Entrez un nombre: ");
			return readInt(empty);
		}
	}

	public static float readFloat() {
		return readFloat(false);
	}

	public static float readEmptyFloat() {
		return readFloat(true);
	}

	private static float readFloat(boolean empty) {
		try {
			String s = in.nextLine();
			if (empty && s.length() == 0)
				return -1;
			return Float.parseFloat(s);
		} catch (Exception e) {
			System.out.print(" ↳ Entrez un nombre: ");
			return readFloat(empty);
		}
	}

	public static Date readDate() {
		return readDate(false);
	}

	public static Date readEmptyDate() {
		return readDate(true);
	}

	private static Date readDate(boolean empty) {
		try {
			Calendar cal = Calendar.getInstance();
			String s;
			String[] tab;
			System.out.println("Entrer une date : (JJ/MM/AA)");
			s = readString();
			if (empty && s.length() == 0)
				return null;
			tab = s.split("/");
			cal.set(Calendar.YEAR, Integer.parseInt(tab[2]));
			cal.set(Calendar.MONTH, Integer.parseInt(tab[1]));
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tab[0]) + 2000);

			return new Date(cal.getTime().getTime());
		} catch (Exception e) {
			System.out.println("Recommencer");
			return readDate(empty);
		}
	}
}
