package com.nsaasystems.inventory_sales_management_system.config;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.nsaasystems.inventory_sales_management_system.entity.Role;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RoleBasedAccessInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (!(handler instanceof HandlerMethod handlerMethod)) {
			return true; // Not a method = allow
		}

		RoleAllowed roleAllowed = handlerMethod.getMethodAnnotation(RoleAllowed.class);
		if (roleAllowed == null) {
			return true; // No role restriction
		}

		String roleHeader = request.getHeader("X-User-Role");
		if (roleHeader == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Missing role header");
			return false;
		}

		try {
			Role userRole = Role.valueOf(roleHeader);
			for (Role allowed : roleAllowed.value()) {
				if (userRole == allowed) {
					return true;
				}
			}
		} catch (IllegalArgumentException ex) {
			// Invalid role
		}

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter().write("Access denied for role: " + roleHeader);
		return false;
	}
}
