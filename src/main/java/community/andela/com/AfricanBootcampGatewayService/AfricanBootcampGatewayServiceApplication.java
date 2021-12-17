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
	TokenFilter tokenFilter;

	//Another way of creating Filter in Spring boot
	@Bean
	public FilterRegistrationBean<TokenFilter> orderFilter() {
		FilterRegistrationBean<TokenFilter> filter = new FilterRegistrationBean<>();
		filter.setName("tokenFilter");
		filter.setFilter(tokenFilter);
		//Assign priority
		filter.setOrder(-1);
		filter.setUrlPatterns(Arrays.asList("/*"));
		filter.setAsyncSupported(true);
		return filter;
	}
}
