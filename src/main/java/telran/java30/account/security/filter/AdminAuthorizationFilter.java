package telran.java30.account.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.java30.account.configuration.AccountConfiguration;

@Service
@Order(30)
public class AdminAuthorizationFilter implements Filter {
	@Autowired
	AccountConfiguration accountConfiguration;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();

		if (chekPoint(path)) {
			if(!request.getUserPrincipal().getName().equals(accountConfiguration.getLoginAdmin())) {
			response.sendError(401, "No access!");
			return;
		}}
		chain.doFilter(request, response);
	}

	private boolean chekPoint(String path) {
		return path.startsWith("/account/admin/edit/");

	}
}
