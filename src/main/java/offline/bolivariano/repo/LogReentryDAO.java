package offline.bolivariano.repo;

import java.util.List;

public interface LogReentryDAO {

	List<LogReentry> select(LogReentry log);
	
}
