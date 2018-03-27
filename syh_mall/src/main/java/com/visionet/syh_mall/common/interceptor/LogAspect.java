package com.visionet.syh_mall.common.interceptor;

import java.util.Arrays;
import java.util.Collection;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.visionet.syh_mall.entity.logs.Logs;
import com.visionet.syh_mall.repository.logs.LogsRepository;
import com.visionet.syh_mall.web.BaseController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
@Aspect
public class LogAspect extends BaseController {

	//	private static Logger log = LoggerFactory.getLogger(LogAspect.class);
	@Autowired
	private LogsRepository logDao;

	/**
	 * before
	 *
	 * @param jp
	 * @param rl
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Before("within(com.visionet.syh_mall.web..*) && @annotation(rl)")
	public void before(JoinPoint jp, Log rl) throws IllegalArgumentException, IllegalAccessException {
		Object[] args = jp.getArgs();// 获取目标方法体参数
		String params = Arrays.toString(args);
		String signature = jp.getSignature().toString(); // 获取目标方法签名
		String modelName = rl.model(); // 根据类名获取所属的模块

		Logs logs = new Logs();
		logs.setOperatingModel(modelName);
		logs.setOperatingName(rl.name());
		logs.setOperatingPersonnel(getCurrentUserName());
		logs.setOperatingParams(params);
		logs.setOperatingSignature(signature);
		logDao.save(logs);
	}

	/**
	 * success 标注该方法体为后置通知，当目标方法执行成功后执行该方法体
	 *
	 * @param jp
	 * @param rl
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	/*@SuppressWarnings("unused")
	@AfterReturning("within(com.visionet.syh_mall.web..*) && @annotation(rl)")
	public void addLogSuccess(JoinPoint jp, Log rl) throws IllegalArgumentException, IllegalAccessException {
		Object[] parames = jp.getArgs();// 获取目标方法体参数 String[]
		String className = jp.getTarget().getClass().toString();// 获取目标类名,com.visionet.project.app.web.user.UserController
		className = className.substring(className.indexOf("com"));// com.visionet.project.app.web.user.UserController
		String signature = jp.getSignature().toString(); // 获取目标方法签名
		String methodName = signature.substring(signature.lastIndexOf(".") + 1, signature.indexOf("("));// 进入的方法名addNew()
		String modelName = rl.model(); // 根据类名获取所属的模块xx管理
		// String ziduan = rl.field();// 用户帐号,user,user_account
		String params = null; // 要打印的内容
		// params = (cast(parames, ziduan));

		log.info("用户名:" + super.getCurrentUserName() + "||进行的操作是" + rl.name() + "||所属模块：" + modelName + "||进入"
				+ signature + ",操作成功，参数是" + params);

	}
*/
	/**
	 * @param parames
	 * @param field
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
/*	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String cast(Object[] parames, String field) throws IllegalArgumentException, IllegalAccessException {
		StringBuffer buf = new StringBuffer();
		// 将参数转换成具体的对象
		Class<? extends Object> cc = parames[0].getClass();
		Object obj = parames[0];
		Field[] fields = cc.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
		}
		// 把 "用户帐号,user,user_name" 分割成数组
		String[] list = field.split(",");
		/// 如果参数类型是list<String>
		if (list[1].equals("List<String>")) {
			buf.append(list[0] + ":" + ((List) obj).get(0));
			return buf.toString();
		}
		if (list[1].equals("Map")) {
			buf.append(list[0] + ":" + ((Map<String, Object>) obj).get(list[2] + "") + "");
			return buf.toString();
		}
		if (list[1].equals("EXCEL")) { // 直接输出提示信息 ,无参数解析
			buf.append(list[0] + "");
			return buf.toString();
		}
		if (list[1].equals("Text")) { // 直接输出提示信息 ,无参数解析
			buf.append(list[0] + "");
			return buf.toString();
		}
		// 修改 ,增加操作
		// 输出每个字段和字段的属性
		for (Field f : fields) {
			String field1 = f.toString().substring(f.toString().lastIndexOf(".") + 1);// 该字段的名称
			if (list[2].equals(field1)) {// 只输出规定的字段
				buf.append(list[0] + ":" + f.get(obj));
			}
			// 打印属性和参数System.out.println("p1."+field+" --> "+f.get(obj));
		}
		return buf.toString();
	}
*/
	/**
	 * Eexception 标注该方法体为异常通知，当目标方法出现异常时，执行该方法体
	 *
	 * @param jp
	 * @param rl
	 * @param ex
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	/*@SuppressWarnings("unused")
	@AfterThrowing(pointcut = "within(com.visionet.syh_mall.web..*) && @annotation(rl)", throwing = "ex")
	public void addLog(JoinPoint jp, Log rl, Exception ex) throws IllegalArgumentException, IllegalAccessException {

		Object[] parames = jp.getArgs();// 获取目标方法体参数
		// String params = parseParames(parames); // 解析目标方法体的参数
		String className = jp.getTarget().getClass().toString();// 获取目标类名
		className = className.substring(className.indexOf("com"));
		String signature = jp.getSignature().toString(); // 获取目标方法签名
		String methodName = signature.substring(signature.lastIndexOf(".") + 1, signature.indexOf("("));
		String modelName = rl.model(); // 根据类名获取所属的模块
		// String ziduan = rl.field();// 用户帐号,user,user_account
		String params = null;
		// params = (cast(parames, ziduan));

		
		 * adminOperateLogService.addAdminOperateLog( "进行的操作是" + rl.name() +
		 * "||进入：" + methodName + "方法" + "||参数：" + params + "||异常信息：" +
		 * ex.getMessage(), super.getCurrentUserId(), modelName, 0);
		 
		// adminOperateLogService.addAdminOperateLog(rl.name() + "[" + params +
		// "]", super.getCurrentUserId(), modelName,
		// 0);
		log.error("用户名:" + super.getCurrentUserName() + "||进行的操作是" + rl.name() + "||所属模块：" + modelName + "||进入"
				+ signature + ",操作失败，异常是" + ex.getMessage());
	}*/

	/**
	 * 解析方法参数
	 *
	 * @param parames
	 *            方法参数
	 * @return 解析后的方法参数
	 */
	@SuppressWarnings("unused")
	private String parseParames(Object[] parames) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < parames.length; i++) {
			if (parames[i] instanceof Object[] || parames[i] instanceof Collection) {
				JSONArray json = JSONArray.fromObject(parames[i]);
				if (i == parames.length - 1) {
					sb.append(json.toString());
				} else {
					sb.append(json.toString() + ",");
				}
			} else if (parames[i] instanceof String) {
				sb.append(parames[i]);
			} else {
				JSONObject json = JSONObject.fromObject(parames[i]);
				if (i == parames.length - 1) {
					sb.append(json.toString());
				} else {
					sb.append(json.toString() + ",");
				}
			}
		}
		String params = sb.toString();
		params = params.replaceAll("(\"\\w+\":\"\",)", "");
		params = params.replaceAll("(,\"\\w+\":\"\")", "");
		return params;
	}

}
