package cn.royians.geekcode;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.slf4j.SLF4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author asus
 */
@SpringBootApplication
@Slf4j
public class GeekcodeApplication implements CommandLineRunner {
	private final DataSource dataSource;

	private final JdbcTemplate jdbcTemplate;

	private SLF4JLogger log;

	public GeekcodeApplication(DataSource dataSource, JdbcTemplate jdbcTemplate) {
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
	}


	public static void main(String[] args) {
		SpringApplication.run(GeekcodeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		showConnection();
		showData();
		log.info("?");
	}

	private void showConnection() throws SQLException {
		Connection connection = dataSource.getConnection();
		System.out.println(connection.toString());
		connection.close();
	}

	private void showData() {
		jdbcTemplate.queryForList("SELECT * FROM student")
				.forEach(row -> System.out.println(row.toString()));
	}
}
