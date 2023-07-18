package com.fw.bo.system.config.interceptor;

import com.fw.bo.system.menu.service.SettingMenuService;
import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminMenuDTO;
import com.fw.core.dto.bo.BoAdminSessionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 메뉴 획득 인터셉터
 * @author JYoo, skayhlj@gmail.com
 */
@Slf4j
public class BoMenuInterceptor implements HandlerInterceptor {

    @Autowired
    private SettingMenuService menuService;

    @Value("${bo.session.key-name}")
    private String BO_SESSION_KEY;

    /* 권한별 메뉴 리스트 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // AJAX 요청시 메뉴 획득 제외
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }

        HttpSession session = request.getSession();
        BoAdminSessionDTO boAdminSessionDTO = (BoAdminSessionDTO)session.getAttribute(BO_SESSION_KEY);

        if(boAdminSessionDTO == null){
            request.getSession().invalidate();
            response.sendRedirect("/bo/login");
        }

        BoAdminDTO boAdminDTO = BoAdminDTO.builder().adminSeq(boAdminSessionDTO.getAdminSeq())
                                                    .orgId(boAdminSessionDTO.getOrgId())
                                                    .build();

        List<BoAdminMenuDTO> personList = menuService.selectMenuListForUser(boAdminDTO);
        if (personList.isEmpty()) {
            List<BoAdminMenuDTO> deptList = menuService.selectMenuListForDept(boAdminDTO);
            if (deptList.isEmpty()) {
                List<BoAdminMenuDTO> orgList = menuService.selectMenuListForOrg(boAdminDTO);
                request.setAttribute("menuList", orgList);
            } else {
                request.setAttribute("menuList", deptList);
            }
        } else {
            request.setAttribute("menuList", personList);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}