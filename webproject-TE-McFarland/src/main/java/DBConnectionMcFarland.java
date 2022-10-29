import java.sql.Connection;
import java.sql.DriverManager;

/* Required table 
 * CREATE TABLE `expenseHeader` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`NAME` VARCHAR(255) NOT NULL,
	`COST` FLOAT NOT NULL,
	`DESC` VARCHAR(255),
	`DATE` DATE NOT NULL,
	PRIMARY KEY (`id`)
);
 */
public class DBConnectionMcFarland {
	static Connection connection = null;

	static void getDBConnection() {
		System.out.println("-------- MySQL JDBC Connection Testing ------------");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
		System.out.println("MySQL JDBC Driver Registered!");

		connection = null;
		try {
			UtilPropMcFarland.loadProperty();
			connection = DriverManager.getConnection(getURL(), getUserName(), getPassword());
		} catch (Exception e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	static String getURL() {
		String url = UtilPropMcFarland.getProp("url");
		// String url =
		// "jdbc:mysql://ec2-3-15-210-247.us-east-2.compute.amazonaws.com:3306/MyDBMcFarland0908";
		System.out.println("[DBG] URL: " + url);
		return url;
	}

	static String getUserName() {
		String usr = UtilPropMcFarland.getProp("user");
		System.out.println("[DBG] USER: " + usr);
		return usr;
	}

	static String getPassword() {
		String pwd = UtilPropMcFarland.getProp("password");
		System.out.println("[DBG] PWD: " + pwd);
		return pwd;
	}
}
