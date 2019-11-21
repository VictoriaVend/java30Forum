package telran.java30.account.security.filter;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.java30.account.configuration.AccountConfiguration;
import telran.java30.account.configuration.UserAccountCredentials;
import telran.java30.account.dao.UserAccountRepository;
import telran.java30.account.model.UserAccount;

@Service
@Order(10)
public class AuthenticationFilter implements Filter {
	@Autowired
	AccountConfiguration accountConfiguration;
	@Autowired
	UserAccountRepository userAccountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		String method = request.getMethod();
		String auth = request.getHeader("Authorization");
		if (!chekPointCut(path, method)) {
			UserAccountCredentials credetials = null;
			try {
				credetials = accountConfiguration.tokenDecode(auth);
			} catch (Exception e) {
				response.sendError(401, "Header Authorization not valid");
				return;
			}
			UserAccount userAccount = userAccountRepository.findById(credetials.getLogin()).orElse(null);
			if (userAccount == null) {
				response.sendError(401, "User not found");
				return;
			}
			if (!BCrypt.checkpw(credetials.getPassword(), userAccount.getPassword())) {
				response.sendError(403, "Password incorrect");
				return;
			}
			chain.doFilter(new WrapperRequest(request, credetials.getLogin()), response);
			return;
		}

		chain.doFilter(request, response);

	}

	private boolean chekPointCut(String path, String method) {
		boolean check = "/account/user".equalsIgnoreCase(path) && "Post".equalsIgnoreCase(method);
		return check = check || path.startsWith("/forum/posts");
	}

	private class WrapperRequest extends HttpServletRequestWrapper {
		String user;

		public WrapperRequest(HttpServletRequest request, String user) {
			super(request);
			this.user = user;
		}

		@Override
		public Principal getUserPrincipal() {
			return new Principal() {
				@Override
				public String getName() {
					return user;
				}
			};
		}
	}

	@Override
	public void init(FilterConfig config) {
//настройки для фильтра
	}

	@Override
	public void destroy() {
//всё что надо сделать до уничтожения фильтра
	}
}
