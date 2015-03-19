package email;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Class to deal with Settings File
 * @author TMH
 *
 */
public class Settings {
	/**
	 * Empty constructor to allow usage of settings methods.
	 */
	public Settings() {

	}

	/**
	 * Checks if settings file is in valid form and if it exists and then splits into array
	 * @param settings - current settings file
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
	/**
	 * Writes settings file to disk
	 * @param serverName - Name of server
	 * @param userName - Email Address
	 * @param portSpinner - value of port
	 * @throws IOException 
	 */
	public void writeToFile(String serverName, String userName, int portSpinner) throws IOException {
		//Create new settings file, save it in user's documents directory and write settings to that file
		//Checks user's OS
		String OS = System.getProperty("os.name").toLowerCase();
		String user = System.getProperty("user.name");
		File newFile = null;
			if(OS.contains("windows")){
				newFile = new File("C:\\Users\\" + user + "\\Documents\\settings.ini");
			}else if(OS.contains("mac")){
				newFile = new File("/Users/" + user + "/Desktop/settings.ini");
			}else if(OS.contains("nix")){
				newFile = new File("~/Desktop/settings.ini");
			}
			//Creates necessary directory
			newFile.getParentFile().mkdirs();
			newFile.createNewFile();
			PrintWriter writer = new PrintWriter(newFile);
			writer.println(serverName + "," + portSpinner + "," + userName + "," + true);
			writer.close();			
			
	}
	
	/**
	 * 
	 * @param settings - settings file containing Email settings
	 * @return String Array of email settings
	 */
	public String[] readSettingsData(File settings) {
		BufferedReader br;
		String[] settingsArray = {};
		try {
			br = new BufferedReader(new FileReader(settings));
			String s = br.readLine();
			br.close();
			System.out.println(s);
			settingsArray = s.split(",");
			return settingsArray;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return settingsArray;

	}
	/**
	 * Find settings file from user's desktop
	 * @return Settings file containing email settings
	 */
	public File findSettingsFile() {
		//Searches desktop for settings file 
		//Returns file if it exists
        File settings = null;
        String OS = System.getProperty("os.name").toLowerCase();
        String user = System.getProperty("user.name");
        String filePathStr = "";
        if(OS.contains("windows")){
        	filePathStr = "C:\\Users\\" + user + "\\Documents"+"\\settings.ini";
		}else if(OS.contains("mac")){
			filePathStr = "/Users/" + user + "/Desktop"+"/settings.ini";
		}else if(OS.contains("nix")){
			filePathStr = "~/Desktop"+"/settings.ini";
		}
        File f = new File(filePathStr);

        if (f.exists() && !f.isDirectory()) {
            settings = f;
        }
        return settings;    
     }
	

}
