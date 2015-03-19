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

	public void writeToFile(String serverName, String userName, int portSpinner) {
		// Create new settings file, save it in user's documents directory and
		// write settings to
		// that file
		
//		String currentDirectory = new File("").getAbsolutePath();
//        currentDirectory += "\\settings.ini";
        
//        File newFile = new File(currentDirectory);
//        newFile.getParentFile().mkdirs();
		
		ClassLoader classLoader = getClass().getClassLoader();
		File settingsFile = new File(classLoader.getResource("settings.ini").getFile());
        
    	try {
			settingsFile.createNewFile();
			System.out.println("File created");

			PrintWriter writer = new PrintWriter(settingsFile);
			writer.println(serverName + "," + portSpinner + "," + userName + "," + true);
			writer.close();
			System.out.println("File writed.");

		} catch (IOException e1) {
			// TODO Auto-generated catch block

		}


	}

	public String[] settingsData(File settings) {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return settingsArray;

	}
	
	public File findSettingsFile() {
		
		ClassLoader classLoader = getClass().getClassLoader();
		File f = new File(classLoader.getResource("settings.ini").getFile());
        
        File settingsFile = null;
        
        
        
        if (f.exists() && !f.isDirectory()){
            System.out.println("Settings.ini exists");
            settingsFile = f;
        } else {
            
            System.out.println("Settings.ini doesn't exist yet");
        }

        return settingsFile;

	}

}
