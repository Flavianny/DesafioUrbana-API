package com.projeto.desafiourbana.cors;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

	public List<String> originsPermitidas = Arrays.asList("http://localhost:4200", "http://localhost:8100",
			"https://10.6.188.43", "http://192.168.0.101:4200");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String originPermitida = "*";

		if (getOriginsPermitidas().contains(req.getHeader("Origin"))) {
			for (String origin : getOriginsPermitidas()) {
				if (origin.equalsIgnoreCase(req.getHeader("Origin"))) {
					originPermitida = origin;
				}
			}
		}

		resp.setHeader("Access-Control-Allow-Origin", originPermitida);
		resp.setHeader("Access-Control-Allow-Credentials", "true");

		if (req.getMethod().equals("OPTIONS") && req.getHeader("Origin").equals(originPermitida)) {

			resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, PATCH, DELETE, OPTIONS");
			resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			resp.setHeader("Access-Control-Max-Age", "3600");

			resp.setStatus(HttpServletResponse.SC_OK);

		} else {
			chain.doFilter(req, resp);
		}

	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	public List<String> getOriginsPermitidas() {
		return originsPermitidas;
	}

	public void setOriginsPermitidas(List<String> originsPermitidas) {
		this.originsPermitidas = originsPermitidas;
	}
	
	

}