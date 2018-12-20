package offline.bolivariano.repo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.bolivariano.frameworkseguridadtypes.LoginAplicacionIn;
import com.bolivariano.frameworkseguridadtypes.LoginAplicacionOut;
import com.bolivariano.frameworkseguridadtypes.LoginAplicacionTypeIn;
import com.bolivariano.frameworkseguridadtypes.LoginAplicacionTypeOut2.Campo;
import com.oracle.xmlns.sca_bloqueologinusuario.frameworkseguridad.frameworkmediator.FrameworkMediatorEp;

import redis.clients.jedis.Jedis;


//@PropertySource("classpath:application.yml")
public class LogReentryDAOImpl implements LogReentryDAO {

	private DataSource dataSource;
	
	//private JdbcTemplate jdbcTemplate;
	
	@Value("${redis.server}")
	private String redisServer;
	
	@Value("${usrFramework}")
	private String usrFramework;

	@Value("${idAplicacion}")
	private String idAplicacion;
	
    private String user;
    private String pass;
    private String url; 
    private String driver;
    
	private Jedis jedis;
    
    @SuppressWarnings("unused")
	private void parameterRedis() {
    	doLogin();
    	jedis = new Jedis(redisServer);
    	this.user=jedis.get("spring.datasource.username");
    	this.pass=jedis.get("spring.datasource.password");
    	this.url=jedis.get("spring.datasource.url");
    	this.driver=jedis.get("spring.datasource.driver");
    }
   
    @Bean
	public void setDataSource() {
    	
    	
	  }
	 
  //  @Bean
//	public void setDataSource(DataSource dataSource) {
    	public void setDataSource(DataSource dataSource) {
		/*DataSourceBuilder builder= DataSourceBuilder.create();
  	  parameterRedis();
  	  builder.url(url);
  	  builder.username(user);
  	  builder.password(pass);
  	  builder.driverClassName(driver);
  	    builder.build();
	    this.dataSource = builder.build();*/
	    
		this.dataSource = dataSource;
	}

	public List<LogReentry> select(LogReentry log) {

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String testDateString = df.format(log.getRe_fecha());
		System.out.println("fecha: " + testDateString);
		
		String sql = "SELECT * FROM bv_l_reentry" + 
		" WHERE  re_estado = '" + log.getRe_estado() +"'"+ 
		" and re_fecha between '"+testDateString+" 00:00' and '"+testDateString+" 23:59'";
		
		List<LogReentry> logs = new ArrayList<LogReentry>();
		/*System.out.println("sql: " + sql);
		try {

			jdbcTemplate = new JdbcTemplate(dataSource);

			SqlRowSet resp = jdbcTemplate.queryForRowSet(sql);
			while (resp.next()) {
				System.out.println(resp.getString(1));
				logs.add(new LogReentry(resp.getDate(1), resp.getString(2), resp.getString(3), resp.getString(4),
						resp.getString(5), resp.getString(6), resp.getString(7)));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}*/
		return logs;
	}
	
	public void doLogin() {
		jedis=null;
		jedis = new Jedis(redisServer);
		jedis.set("actualizar", "true");
		
		FrameworkMediatorEp servicio = new FrameworkMediatorEp();
		LoginAplicacionIn blrequest = new LoginAplicacionIn();
		LoginAplicacionTypeIn var = new LoginAplicacionTypeIn();
		var.setUsuario(usrFramework);
		var.setIdAplicacion(idAplicacion);
		blrequest.setLoginAplicacionIn(var);

		LoginAplicacionOut respuesta = servicio.getFrameworkPt().loginAplicacion(blrequest);
		jedis.set("spring.datasource.driver", "com.sybase.jdbc4.jdbc.SybDriver");
		jedis.set("spring.datasource.url", "jdbc:sybase:Tds:172.16.23.22:2050/cob_internet");
		for (Campo x : respuesta.getLoginAplicacionOut().getCampo()) {
			System.out.println("framework : " + x.getNombre() + "-" + x.getValue());
			switch (x.getNombre()) { 
			   case "USUARIO_OTC" :
				   jedis.set("spring.datasource.username", x.getValue());
				   break;
			   case "CLAVE_OTC" :
				   jedis.set("spring.datasource.password", x.getValue());
				   break;
				   
			default:break;
			}
			
		}
	}
}
