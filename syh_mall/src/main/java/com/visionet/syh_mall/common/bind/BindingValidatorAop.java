package com.visionet.syh_mall.common.bind;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.visionet.syh_mall.exception.RestException;

/**
 * aop bean Validator
 * @author xiaofb
 * @time 2017年9月21日
 */
@Component
@Aspect
public class BindingValidatorAop {

	@Pointcut("execution(* com.visionet.syh_mall.web..*.*(..))")
	public void paramValidator() {}

	@Before("paramValidator()")
	public void around(JoinPoint joinPoint) throws Throwable {
		BindingResult bindingResult = null;
		for (Object arg : joinPoint.getArgs()) {
			if (arg instanceof BindingResult) {
				bindingResult = (BindingResult) arg;
			}
		}
		if (bindingResult != null) {
			if (bindingResult.hasErrors()) {
				String errorInfo = "["
						+ bindingResult.getFieldError().getField() + "]"
						+ bindingResult.getFieldError().getDefaultMessage();
				throw new RestException(errorInfo);
			}
		}
	}
}
