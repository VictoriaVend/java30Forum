package telran.java30.forum.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.java30.forum.dao.ForumRepository;
import telran.java30.forum.dto.CommentRepostDto;
import telran.java30.forum.dto.MessageDto;
import telran.java30.forum.dto.PostDto;
import telran.java30.forum.dto.PostNotFoundExeption;
import telran.java30.forum.dto.PostRepostDto;
import telran.java30.forum.model.Comment;
import telran.java30.forum.model.Post;

@Service
public class ForumServiceImpl implements ForumService {
	@Autowired
	ForumRepository forumRepository;

	@Override
	public boolean addLike(String id) {
		if (forumRepository.existsById(id)) {
			return false;
		}
		Post post=forumRepository.findById(id).get();
		post.addLike();
		forumRepository.save(post);
		return true;

	}

	@Override
	public PostRepostDto addComment(MessageDto message, String id, String author) {
		Post post=forumRepository.findById(id).orElseThrow(()->new PostNotFoundExeption(id));
		Comment comment=new Comment(author,message.getMessage());
		post.addComment(comment);
		post=forumRepository.save(post);
		return postToPostRepstDto(post);
	}

	@Override
	public List<PostRepostDto> findPostByAuthor(String author) {
		forumRepository.findByAuthor(author).map(p->postToPostRepstDto(p)).collect(Collectors.toList());
		return null;
	}

	@Override
	public PostRepostDto addPost(PostDto postDto, String author) {
		Post post = new Post(postDto.getTitle(), postDto.getContent(), author);
		post.addTags(postDto.getTags());
		post = forumRepository.save(post);
		return postToPostRepstDto(post);
	}

	@Override
	public PostRepostDto findPostById(String id) {
		Post post=forumRepository.findById(id).orElseThrow(()->new PostNotFoundExeption(id));
		return postToPostRepstDto(post);
	}

	@Override
	public PostRepostDto updatePost(PostDto postDto, String id) {
		Post post=forumRepository.findById(id).orElseThrow(()->new PostNotFoundExeption(id));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setTags(postDto.getTags());
		forumRepository.save(post);
		return postToPostRepstDto(post);
	}

	@Override
	public PostRepostDto deletePost(String id) {
		Post post=forumRepository.findById(id).orElseThrow(()->new PostNotFoundExeption(id));
		forumRepository.deleteById(id);
		return postToPostRepstDto(post);
	}

	private PostRepostDto postToPostRepstDto(Post post) {
		Set<CommentRepostDto> commentset = new HashSet<>();
		post.getComments().forEach(com -> commentset.add(comentToCommentRepostDto(com)));
		List<String> taglist = new ArrayList<>();
		post.getTags().forEach(s -> taglist.add(s));

		return PostRepostDto.builder().id(post.getId()).title(post.getTitle()).content(post.getContent())
				.author(post.getAuthor()).dateCreated(post.getDateCreated()).tags(taglist).likes(post.getLikes())
				.build();
	}

	private CommentRepostDto comentToCommentRepostDto(Comment comment) {
		return CommentRepostDto.builder().user(comment.getUser()).message(comment.getMessage())
				.dateCreated(comment.getDateCreated()).likes(comment.getLikes()).build();

	}

}
