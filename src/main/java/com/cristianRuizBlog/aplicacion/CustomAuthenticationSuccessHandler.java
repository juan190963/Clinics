package com.cristianRuizBlog.aplicacion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.mapping.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		 java.util.Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

	        for (GrantedAuthority authority : authorities) {
	            if (authority.getAuthority().equals("ROLE_ADMIN")) {
	                response.sendRedirect("/inicioU"); // Redirigir a la página de cita
	                return;
	            } else if (authority.getAuthority().equals("ROLE_ASESOR")) {
	                response.sendRedirect("/inicioC"); // Redirigir a la página de historial
	                return;
	            } else if (authority.getAuthority().equals("ROLE_DOCTOR")) {
	                response.sendRedirect("/inicioH"); // Redirigir a la página de solicitud
	                return;
	            }else if (authority.getAuthority().equals("ROLE_USER")) {
	                response.sendRedirect("/solicitud"); // Redirigir a la página de solicitud
	                return;
	            }
	            
	        }

	        // Si el rol no coincide con ninguno de los anteriores, redirigir a una página por defecto
	        response.sendRedirect("/login");
	    }
}
