package community.andela.com.AfricanBootcampGatewayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@ServletComponentScan
@SpringBootApplication
public class AfricanBootcampGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfricanBootcampGatewayServiceApplication.class, args);
	}

	@Autowired
	TokenValidationFilter tokenValidationFilter;

	//Another way of creating Filter in Spring boot
	@Bean
	public FilterRegistrationBean<TokenValidationFilter> orderFilter() {
		FilterRegistrationBean<TokenValidationFilter> filter = new FilterRegistrationBean<>();
		filter.setName("tokenValidationFilter");
		filter.setFilter(tokenValidationFilter);
		//Assign priority
		filter.setOrder(-1);
		filter.setUrlPatterns(Arrays.asList(
				"/logout"
		));
		filter.setAsyncSupported(true);
		return filter;
	}
}
