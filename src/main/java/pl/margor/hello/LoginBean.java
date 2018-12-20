package pl.margor.hello;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bolivariano.frameworkseguridadtypes.LoginAplicacionIn;
import com.bolivariano.frameworkseguridadtypes.LoginAplicacionOut;
import com.bolivariano.frameworkseguridadtypes.LoginAplicacionTypeIn;
import com.bolivariano.frameworkseguridadtypes.LoginAplicacionTypeOut2.Campo;
import com.oracle.xmlns.sca_bloqueologinusuario.frameworkseguridad.frameworkmediator.FrameworkMediatorEp;

import redis.clients.jedis.Jedis;

@Component("loginBean")
@Scope("session")
@Configuration
@PropertySource("classpath:application.yml")
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private RestTemplate restTemplate;

	//@Autowired
//	SessionBean sessionBean;

	@Value("${usrFramework}")
	private String usrFramework;

	@Value("${idAplicacion}")
	private String idAplicacion;

	@Value("${redis.server}")
	private String redisServer;

	@Value("${redis.keyNameModoArbitro}")
	private String keyNameModoArbitro;

	@Value("${reentry.server}")
	private String reentryserver;

	@Value("${reentry.port}")
	private String reentryport;

	private Jedis jedis;

	public void on() {
		jedis = null;
		try {

			jedis = new Jedis(redisServer);
			jedis.set(keyNameModoArbitro, "online");
			restTemplate.getForObject("http://" + reentryserver + ":" + reentryport + "/enableListener", String.class);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "MODO ONLINE ACTIVADO", "SE SUBE EL REENTRY"));

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AL CAMBIAR MODO ONLINE!", ""));

		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void off() {
		jedis = null;
		try {

			jedis = new Jedis(redisServer);
			jedis.set(keyNameModoArbitro, "offline");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "MODO OFFLINE ACTIVADO", ""));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AL CAMBIAR A MODO OFFLINE !", ""));
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void status() {
		jedis = null;
		try {

			jedis = new Jedis(redisServer);
			String modo = jedis.get(keyNameModoArbitro);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "ESTADO ACTUAL : ", modo.toUpperCase()));
			String actualizar = "";
			actualizar = jedis.get("actualizar");

			if (actualizar == null) {
				doLogin();
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
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
