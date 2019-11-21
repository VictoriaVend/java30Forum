package telran.java30.account.security.filter;

import java.io.IOException;
import java.time.LocalDateTime;

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

import telran.java30.account.dao.UserAccountRepository;
@Service
@Order(20)
public class ExpDateFilter implements Filter {
	
	@Autowired
	UserAccountRepository userAccountRepository;
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		String principal =request.getUserPrincipal().getName();
	if(principal!=null&&!chekPointCut(path)) {
		if(userAccountRepository.findById(principal)
		.get().getExpDate().isBefore(LocalDateTime.now())) {
			response.sendError(403, "Password expired, change password!");
			return;
		}
	}chain.doFilter(request, response);
	}
	
	private boolean chekPointCut(String path) {
		boolean check = "/account/user/edit/password".equalsIgnoreCase(path);
		return check = check || path.startsWith("/forum/posts");
	}
	
}
