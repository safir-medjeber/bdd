public class PasswordField {

	/**
	 * @param prompt
	 *            The prompt to display to the user
	 * @return The password as entered by the user
	 */
	public static String readPassword(String prompt) {
		String password = "";
		try {
			if(System.console() == null)
				System.err.println("A faire sur le terminal");
			else
				password = new String(System.console().readPassword("%s", prompt));
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return password;
	}

}
