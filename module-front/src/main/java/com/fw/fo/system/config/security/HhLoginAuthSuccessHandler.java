package com.fw.fo.system.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.hh.HhMyProfileDTO;
import com.fw.core.mapper.db1.fo.FoLoginMapper;
import com.fw.core.mapper.db1.hh.HhMyProfileMapper;
import com.fw.core.persistence.db1.domain.TbApiLoginLog;
import com.fw.core.persistence.db1.dto.TbApiLoginLogRequestDto;
import com.fw.core.persistence.db1.repository.TbApiLoginLogRepository;
import com.fw.core.util.IpUtil;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class HhLoginAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TbApiLoginLogRepository tbApiLoginLogRepository;
    private final HhMyProfileMapper hhMyprofileMapper;
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
        
		HhMyProfileDTO hhMyprofileDTO = new HhMyProfileDTO();
		hhMyprofileDTO.setAccessIp(IpUtil.getClientIp());
		hhMyprofileDTO.setUserAgent(request.getHeader("user-agent"));
		hhMyprofileDTO.setMemberId((foSessionDTO.getId()));
		hhMyprofileDTO.setUseType("LOGIN");

		hhMyprofileMapper.insertHist(hhMyprofileDTO);
        foLoginMapper.updateLoginInfo(foSessionDTO);

        tbApiLoginLogRepository.save(apiLoginLog);
        // AJAX 요청시 제외
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.getWriter().print(mapper.registerModule(new JavaTimeModule()).writeValueAsString(ResponseVO.builder(ResponseCode.SUCCESS).build()));
            response.getWriter().flush();
        }else{
            response.sendRedirect("/hh");
        }
    }

}
