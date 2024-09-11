package com.gunadhyasoft.cico;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Time Tango desktop application start
 * 
 * @author Manisha Abdar
 *
 */
public class Main {

	/*
	 * Launch the login or dashboard scene
	 */
	public static void main(String[] args) {

		// Example Java version string
		String javaVersion = System.getProperty("java.version");

		// Extract the major and minor version
		String majorMinorVersion = getMajorMinorVersion(javaVersion);

		Float javaV = Float.parseFloat(majorMinorVersion);
		Float java8 = 1.8f;

		List<String> list = new ArrayList<>();
		String mainJarPath = System.getProperty("user.dir") + File.separator + "TTAD Support" + File.separator
				+ "TimeTangoSetup.exe";
		
		if (Objects.equals(javaV, java8)) {
			list = Arrays.asList("java", "-jar", mainJarPath);
		} else if (javaV.intValue() > 8) {
			String javafxSdk = System.getProperty("user.dir") + File.separator + "TTAD Support" + File.separator
					+ "lib";
			list = Arrays.asList("java", "--module-path", javafxSdk, "--add-modules", "javafx.controls,javafx.fxml",
					"-jar", mainJarPath);
		} else {
			System.out.println("Java version is less then 8 or java is not installed");
		}
		String[] command = list.toArray(new String[0]);
		ProcessBuilder pb = new ProcessBuilder(command);
		pb.redirectErrorStream(true);
		Process process = null;

		try {
			process = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Script Started: " + pb.toString());

		// Capture and print output from the Python script
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getMajorMinorVersion(String version) {
		// Find the position of the second dot
		int secondDotIndex = version.indexOf('.', version.indexOf('.') + 1);

		// Extract the substring up to the second dot
		if (secondDotIndex != -1) {
			return version.substring(0, secondDotIndex);
		} else {
			return version; // In case the version string doesn't have two dots
		}
	}

}
