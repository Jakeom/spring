package com.fw.fo.system.config.aop;

import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoCommonDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.mapper.db1.fo.FoChatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
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

    private final FoChatMapper foChatMapper;

    @Around("execution(* com.fw..*Controller.*(..))")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        HttpServletRequest request = null;
        ModelMap map = null;
        PushHistDTO pushHistDTO = new PushHistDTO();

        for(Object obj : proceedingJoinPoint.getArgs()) {
            if(obj instanceof CommonDTO){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if(!Objects.isNull(authentication)) {
                    if(!(authentication.getPrincipal() instanceof String)){
                        ((CommonDTO) obj).setFrontSession((FoSessionDTO) authentication.getPrincipal());
                        pushHistDTO.setFrontSession((FoSessionDTO) authentication.getPrincipal());
                    }
                }
            } else if (obj instanceof HttpServletRequest) {
                request = (HttpServletRequest) obj;
            } else if (obj instanceof ModelMap) {
                map = (ModelMap) obj;
            }
        }

        if(!Objects.isNull(map)) {
            map.addAttribute("alarmCnt", foChatMapper.selectAlarmCntIncludeChat(pushHistDTO));
        }

        return proceedingJoinPoint.proceed();
	}
}
