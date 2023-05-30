package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
//import java.util.logging.Level;
//import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.frameworkexception.FrameException;

public class DriverFactory {

	WebDriver driver;
	OptionsManager optionManager;
	
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser").trim();

		System.out.println("browser name is : " + browserName);
		
		optionManager= new OptionsManager(prop);

		switch (browserName.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver(optionManager.getChromeOptions());
			break;
		case "edge":
			driver = new EdgeDriver(optionManager.getEdgeOptions());
			break;
		case "safari":
			driver = new SafariDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver(optionManager.getFirefoxOptions());
			break;

		default:
			System.out.println("plz pass the right browser ...." + browserName);
			throw new FrameException("NOBROWSERFOUNDEXCEPTION");
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
		return driver;

	}

	public Properties initProp() {

		// mvn clean install -Denv="qa" -- cmd line, jenkins
		// mvn clean install
		Properties prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");
		System.out.println("env name is: " + envName);

		try {
			if (envName == null) {
				System.out.println("no env is given...hence running it on QA env...");
				ip = new FileInputStream("./src/main/resources/config/qa.config.properties");

			} else {
				System.out.println("Running test cases on env: " + envName);
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/main/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/main/resources/config/dev.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/main/resources/config/stage.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/main/resources/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/main/resources/config/config.properties");
					break;

				default:
					System.out.println("plz pass the right env name...." + envName);
					throw new FrameException("NOVALIDENVGIVEN");
				}
			}
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;

	}

}