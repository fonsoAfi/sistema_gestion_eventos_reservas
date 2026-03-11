package ifw.daw.sger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class SgerApplication {

	public static void main(String[] args) {
		
		Driver driver;
		try {
			driver = DriverManager.getDriver("jdbc:mysql://mysql:3306/sistema_gestion_eventos_reservas");
			System.out.println("Driver: " + driver.getClass().getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SpringApplication.run(SgerApplication.class, args);
	}

}
