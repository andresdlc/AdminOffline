package pl.margor;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ServletContextAware;

@SpringBootApplication
//@ImportResource("classpath:camel-config.xml")
public class PrimeFacesApp  implements ServletContextAware {

//	@Value("${trustStore.path}")
//	private String trustStorePath;

//	@Value("${trustStore.password}")
//	private String trustStorePassword;

	public static void main(String[] args) {
		SpringApplication.run(PrimeFacesApp.class, args);

	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
	}

	@Bean
	public ServletRegistrationBean facesServletRegistraiton() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new FacesServlet(),
				new String[] { "*.jsf" });
		registration.setName("Faces Servlet");
		registration.setLoadOnStartup(1);
		return registration;
	}



	@Bean
	public RestTemplate geRestTemplate() {
		return new RestTemplate();
	}

/*	@Bean
	public ServletContextInitializer servletContextInitializer() {
		return servletContext -> {
			servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
			servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.UPLOADER", "commons");
		};
	}

	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		return new ServletListenerRegistrationBean<>(new ConfigureListener());
	}

	

	@Bean
	public static CustomScopeConfigurer customScopeConfigurer() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		Map<String, Object> scopes = new HashMap<>();
		scopes.put("view", new ViewScope());
		configurer.setScopes(scopes);
		return configurer;
	}
*/
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(PrimeFacesApp.class)
	              .properties("redis.server=172.16.30.143")
	              .properties("redis.keyNameModoArbitro=modo")
	              .properties("reentry.server=172.16.30.143")
	              .properties("reentry.port=172.16.30.143")
	              .properties("spring.datasource.url=jdbc:sybase:Tds:127.0.0.1:2050/dummy")
                  .properties("spring.datasource.username=dummy")
 	              .properties("spring.datasource.password=111231")
	              .properties("spring.datasource.driver-class-name=com.sybase.jdbc4.jdbc.SybDriver");
	}
	
	/*@Bean
	InitializingBean setSystemProperties() {
		return () -> {
			try {
				System.setProperty("javax.net.ssl.trustStore", trustStorePath);
				System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

			} catch (ClassCastException e) {
				System.out.println("Error al setear propiedades");

			}
		};
	}*/

	
	/*@Bean
	public FilterRegistrationBean facesUploadFilterRegistration() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(new FileUploadFilter(),
				facesServletRegistraiton());
		registrationBean.setName("PrimeFaces FileUpload Filter");
		registrationBean.addUrlPatterns("/*");
		registrationBean.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST);
		return registrationBean;
	}*/
}
