package email;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Settings {

	public Settings() {

	}

	/**
	 * 
	 * @param settings
	 *            - current settings file
	 * @return true - if settings file is in correct format
	 */
	public boolean checkSettings(File settings) {

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(settings));
			String s = br.readLine();
			br.close();
			System.out.println(s);
			String[] settingsArray = s.split(",");

			if (settingsArray.length == 4) {
				return true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	public void writeToFile(String serverName,String userName,int portSpinner) {
		// Create new settings file, save it in user's documents directory and
		// write settings to
		// that file

		String OS = System.getProperty("os.name").toLowerCase();// Check's
																// user's OS

		if (OS.contains("windows")) {// If the OS is windows
			String user = System.getProperty("user.name");

			// Creates a new file path for the settings file within Documents
			// directory
			File newFile = new File("C:\\Users\\" + user
					+ "\\Documents\\settings.ini");
			newFile.getParentFile().mkdirs(); // Creates the necessary
												// directories
			try {
				newFile.createNewFile();
				System.out.println("File created");

				PrintWriter writer = new PrintWriter(newFile);
				writer.println(serverName + ","
						+ portSpinner + "," + userName
						+ "," + true);
				writer.close();
				System.out.println("File writed.");

			} catch (IOException e1) {
				// TODO Auto-generated catch block

			}

		} else if (OS.contains("mac")) {// If the OS is mac
			String user = System.getProperty("user.name");
			// String filePathStr = "/Users/" + user + "/Desktop";
			File newFile = new File("/Users/" + user + "/Desktop/settings.ini");
			newFile.getParentFile().mkdirs();
			try {
				newFile.createNewFile();
				System.out.println("File created");

				PrintWriter writer = new PrintWriter(newFile);
				writer.println(serverName + ","
						+ portSpinner + "," + userName
						+ "," + true);
				writer.close();
				System.out.println("File writed.");

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (OS.contains("nix")) {// If the OS is mac/unix
			// String user = System.getProperty("user.name");
			// String filePathStr = "/Users/" + user + "/Desktop";
			File newFile = new File("~/Desktop/settings.ini");
			newFile.getParentFile().mkdirs();
			try {
				newFile.createNewFile();
				System.out.println("File created");

				PrintWriter writer = new PrintWriter(newFile);
				writer.println(serverName+ ","
						+ portSpinner + "," + userName
						+ "," + true);
				writer.close();
				System.out.println("File writed.");

			} catch (IOException e1) {
				// TODO Auto-generated catch block

			}
		}

	}

}
