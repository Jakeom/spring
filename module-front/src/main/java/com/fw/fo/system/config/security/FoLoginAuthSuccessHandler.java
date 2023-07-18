package com.fw.fo.system.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.mapper.db1.fo.FoLoginMapper;
import com.fw.core.persistence.db1.domain.TbAdminLoginLog;
import com.fw.core.persistence.db1.domain.TbApiLoginLog;
import com.fw.core.persistence.db1.dto.TbAdminLoginLogRequestDto;
import com.fw.core.persistence.db1.dto.TbApiLoginLogRequestDto;
import com.fw.core.persistence.db1.repository.TbApiLoginLogRepository;
import com.fw.core.util.IpUtil;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class FoLoginAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TbApiLoginLogRepository tbApiLoginLogRepository;
    private final FoLoginMapper foLoginMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        FoSessionDTO foSessionDTO = (FoSessionDTO) authentication.getPrincipal();

        TbApiLoginLog apiLoginLog = TbApiLoginLogRequestDto.builder()
                .id(foSessionDTO.getId())
                .resultFlag("Y")
                .accessIp(IpUtil.getClientIp())
                .createDt(LocalDateTime.now())
                .build().toEntity();

        tbApiLoginLogRepository.save(apiLoginLog);
        foLoginMapper.updateLoginInfo(foSessionDTO);

        // AJAX 요청시 제외
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.getWriter().print(mapper.registerModule(new JavaTimeModule()).writeValueAsString(ResponseVO.builder(ResponseCode.SUCCESS).build()));
            response.getWriter().flush();
        }else{
            response.sendRedirect("/");
        }

    }

}
