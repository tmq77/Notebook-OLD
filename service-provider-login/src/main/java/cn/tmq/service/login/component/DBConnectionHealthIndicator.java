package cn.tmq.service.login.component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * 数据库连接状态健康指示器.
 *  自定义健康自检工具类,检验数据库连接状态，如果连接失败，则本微服务客户端不可用
 *  2019年3月6日 20点50分
 * @author 陶敏麒
 * @version 1.0
 */
@Component
public class DBConnectionHealthIndicator implements HealthIndicator {

	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.driver-class-name}")
	private String driverName;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	
	
	@Override
	public Health health() {
		Connection conn = null;
		try {
			Class.forName(this.driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// 数据库驱动无法加载，服务down
			return new Health.Builder(Status.DOWN).build();
		}
		try {
			conn = DriverManager.getConnection(this.url, this.username, this.password);
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new Health.Builder(Status.DOWN).build();
		}
		return new Health.Builder(Status.UP).build();
	}

	

}
