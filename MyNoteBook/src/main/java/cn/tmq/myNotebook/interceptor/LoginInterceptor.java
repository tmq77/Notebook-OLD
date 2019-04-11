package cn.tmq.myNotebook.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.tmq.myNotebook.commonUtils.CommonUtils;
import cn.tmq.myNotebook.constants.Constants;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("------------------------------进入登录拦截器----------------------------------");
		HttpSession session = request.getSession();
		if (CommonUtils.checkNullOrEmpty(session.getAttribute(Constants.SESSION_USER))) {
			response.sendRedirect(Constants.INTERCEPTOR_VIEW);
			System.out.println(request.getRequestURI());
			System.out.println("------------------------------验证失败----------------------------------");
			return false;
		} else {
			System.out.println("------------------------------通过验证----------------------------------");
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

}
