package education_program.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import education_program.web.interceptor.MemberInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private MemberInterceptor memberInterceptor;	
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(memberInterceptor)
			.addPathPatterns("/**")
			
			.excludePathPatterns("/css/**")
			.excludePathPatterns("/images/**")
			.excludePathPatterns("/js/**")
			.excludePathPatterns("/vendor/**")
			.excludePathPatterns("/login")
			.excludePathPatterns("/loginProcess");
	}
}
