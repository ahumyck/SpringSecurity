package com.example.securingweb.security.jwt;

import com.example.securingweb.security.userdetails.CustomUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JavaWebTokenFilter extends GenericFilterBean {

	@Autowired
	private JavaWebTokenService javaWebTokenService;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	public static final String AUTHORIZATION = "Authorization";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


		chain.doFilter(request, response);
	}

	private String getTokenFromRequest() {
		return "";
	}

}
