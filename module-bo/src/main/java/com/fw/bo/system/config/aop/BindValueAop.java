package com.fw.bo.system.config.aop;

import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.bo.BoAdminSessionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 값 바인딩 AOP
 * @author sjpaik
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class BindValueAop {

    @Value("${bo.session.key-name}")
    private String BO_SESSION_KEY;

    @Around("execution(* com.fw..*Controller.*(..))")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = null;
        ModelMap map = null;
        CommonDTO commonDTO = null;

        for(Object obj : proceedingJoinPoint.getArgs()) {
            if(obj instanceof CommonDTO){
                commonDTO = (CommonDTO) obj;
            } else if (obj instanceof HttpServletRequest) {
                request = (HttpServletRequest) obj;
            } else if (obj instanceof ModelMap) {
                map = (ModelMap) obj;
            }
        }

        for(Object obj : proceedingJoinPoint.getArgs()) {
            if(obj instanceof CommonDTO) {
                if (request != null && commonDTO != null) {
                    ((CommonDTO) obj).setAdminSession((BoAdminSessionDTO) request.getSession().getAttribute(BO_SESSION_KEY));
                }
            }
        }

        return proceedingJoinPoint.proceed();
	}

}
