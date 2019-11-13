package telran.java30.forum.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;


import telran.java30.forum.model.Post;

public interface ForumRepository extends MongoRepository<Post, String> {
	Stream<Post> findByAuthor(String author);
	Stream<Post> findPostsByTagsIn(List<String>tags);
	Stream<Post>findPostsByDateCreatedBetween(LocalDateTime from,LocalDateTime to);
}
