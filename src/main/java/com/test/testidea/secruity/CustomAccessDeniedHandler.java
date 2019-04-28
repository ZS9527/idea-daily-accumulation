package com.test.testidea.secruity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.testidea.basic.Result;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 无权访问异常处理
 *
 * @author
 * @version 0.1
 * @date 2019/3/26 16:40
 */

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().println(mapper.writeValueAsString(
            Result.no(HttpStatus.FORBIDDEN.value(), e.getMessage())));
    }

}
