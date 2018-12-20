package pl.margor.hello;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import offline.bolivariano.repo.LogReentry;
import offline.bolivariano.repo.LogReentryDAOImpl;

@Component("logsBean")
//@Scope("view")
public class ConsultaLogsReentryBean implements Serializable {
	
	private static final long serialVersionUID = -855303330205428112L;
	private LogReentry logReentryFilter;
	
	@Autowired(required=true)
	LogReentryDAOImpl logReentryDAOImpl;

	public ConsultaLogsReentryBean() {
		logReentryFilter = new LogReentry(); 
	}

	public LogReentry getLogReentryFilter() {
		return logReentryFilter;
	}

	public void setLogReentryFilter(LogReentry logReentryFilter) {
		this.logReentryFilter = logReentryFilter;
	}

	private LogReentry logSelected;
	
	public LogReentry getLogSelected() {
		return logSelected;
	}

	public void setLogSelected(LogReentry logSelected) {
		this.logSelected = logSelected;
	}


	//private final LogReentryService logReentryService=new LogReentryService();

	
	//@Autowired
	public List<LogReentry> obtenerLogsReentry() {

	
		List<LogReentry> result=null;
		System.out.println("ENTRO"+ logReentryFilter.getRe_fecha()+ ""+logReentryFilter.getRe_estado());
		if(logReentryFilter.getRe_fecha()!=null && logReentryFilter.getRe_estado()!=null ) {
			result= logReentryDAOImpl.select(logReentryFilter);}
		return result;

	}

  
	/*public void prevPage() throws IOException {
		// redirector.redirect("index.jsf");
	}*/

}
