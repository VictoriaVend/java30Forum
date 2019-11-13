package telran.java30.forum.configuration;

import org.springframework.context.annotation.Configuration;

import telran.java30.forum.service.ForumService;
import telran.java30.forum.service.ForumServiceImpl;



	@Configuration
	public class ForumConfiguration {
	public ForumService getForumService() {
		return new ForumServiceImpl();
	}
	}

