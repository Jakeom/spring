package com.fw.api.system.config.aop;

import com.fw.core.dto.CommonDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * SQL Paging AOP
 * @author sjpaik
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class PagingAop {
    
    @Around("execution(* com.fw..*Controller.*(..)) && @annotation(com.fw.core.config.annotation.Paging)")
	public Object paginAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        
        CommonDTO commonDTO = null;
        
        for (Object obj : proceedingJoinPoint.getArgs()){ 
            if (obj instanceof CommonDTO) {
                commonDTO = (CommonDTO) obj;
            } 
        }
                
        int size = 0;
        if (commonDTO != null) {
            size = commonDTO.getRowSize();
            commonDTO.setStartRow((size * commonDTO.getPage()) - size);
            commonDTO.setEndRow(size);
        }

        return proceedingJoinPoint.proceed();
    }

}
