package com.my.blog.handler;

import com.alibaba.fastjson.JSON;
import com.my.blog.domain.ResponseResult;
import com.my.blog.enums.AppHttpCodeEnum;
import com.my.blog.utils.WebUtils;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.config
 * @className: AccessDeniedHandlerImpl
 * @author: LYH
 * @description: TODO
 * @date: 2023/5/18 21:06
 * @version: 1.0
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        ResponseResult result =
                ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
