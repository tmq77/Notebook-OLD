package cn.tmq.service.login.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;

/**
 * DB健康检查处理器.
   *  处理器会将应用的健康状态保存在内存中，一旦状态发生改变，就会重新向服务器进行注册，
   *  其他的客户端将不会拿到这些不可用的实例。
 * @author 陶敏麒
 *
 */
@Component
public class DBHealthCheckHandler implements HealthCheckHandler {
	/**
	 * 注入本服务中的健康指示器，获取健康状态.
	 */
	@Autowired
	private DBConnectionHealthIndicator indicator;
	
	@Override
	public InstanceStatus getStatus(InstanceStatus currentStatus) {
		// 获取健康状态.
		Status status = this.indicator.health().getStatus();
		if (Status.UP.equals(status)) {
			// 服务状态正常
			System.out.println("数据库连接正常,当前服务状态Up.");
			return InstanceStatus.UP;
		} else {
			System.out.println("数据库连接异常,当前服务状态down.");
			return InstanceStatus.DOWN;
		}
	}

}
