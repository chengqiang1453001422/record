package com.chengqiang.record.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chengqiang.record.entity.UserInfo;
import com.chengqiang.record.util.SessionUtil;
import com.chengqiang.record.api.UserServiceImpl;


@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserServiceImpl UserService;
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 取得token
        String token = request.getHeader("token");//HttpHeaders.AUTHORIZATION
        if("undefined".equals(token) || StringUtils.isEmpty(token)){
            token=request.getParameter("token不存在");
        }
        if ("undefined".equals(token) || StringUtils.isEmpty(token)) {
            throw new Exception("token不存在");
        }
//        if (!token.startsWith("Bearer ")) {
//            throw new NotLoginException("token格式错误");
//        }
        token = token.replace("Bearer ", "");
        UserInfo userBean = UserService.getUserBeanByToken(token);
        SessionUtil.setSession(userBean);
        if (userBean == null) {
            throw new Exception();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }
}
