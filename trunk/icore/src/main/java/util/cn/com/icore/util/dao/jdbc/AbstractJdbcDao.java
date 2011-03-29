package cn.com.icore.util.dao.jdbc;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public  class AbstractJdbcDao{
	
	private JdbcTemplate jdbcTemplate;
	protected Log logger = LogFactory.getLog(AbstractJdbcDao.class);
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	

}
