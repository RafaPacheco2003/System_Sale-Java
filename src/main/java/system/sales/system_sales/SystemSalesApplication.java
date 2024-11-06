package system.sales.system_sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("system.sales.system_sales.Entity")
public class SystemSalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemSalesApplication.class, args);
	}

}
