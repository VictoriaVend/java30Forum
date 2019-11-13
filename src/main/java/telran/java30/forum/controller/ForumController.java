package telran.java30.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.java30.forum.dto.DatesDto;
import telran.java30.forum.dto.MessageDto;
import telran.java30.forum.dto.PostDto;
import telran.java30.forum.dto.PostRepostDto;
import telran.java30.forum.service.ForumService;


@RequestMapping(ForumURL.FORUM)
@RestController
public class ForumController {
	@Autowired
	ForumService forumService;

	@PostMapping(ForumURL.POSTS + ForumURL.TAGS )
	public List<PostRepostDto> findPostsByTags(@RequestBody List<String> tags) {
		return forumService.findPostsByTags(tags);

	}
	@PostMapping(ForumURL.POSTS+ForumURL.DATES)
	public List<PostRepostDto>  findPostsByDates(@RequestBody DatesDto dateDto){
		return forumService.findPostsByDates(dateDto);
		
	}

	@PostMapping(ForumURL.POST + "/{author}")
	public PostRepostDto addPost(@PathVariable String author, @RequestBody PostDto postDto) {
		return forumService.addPost(postDto, author);
	}

	@PutMapping(ForumURL.POST + "/{id}" + ForumURL.LIKE)
	public boolean addLike(@PathVariable String id) {
		return forumService.addLike(id);
	}

	@PutMapping(ForumURL.POST + "/{id}" + ForumURL.COMMENT + "/{author}")
	public PostRepostDto addComment(@RequestBody MessageDto message, @PathVariable String id,
			@PathVariable String author) {
		return forumService.addComment(message, id, author);
	}

	@GetMapping(ForumURL.POSTS + ForumURL.AUTHOR + "/{author}")
	public List<PostRepostDto> findPostByAuthor(@PathVariable String author) {
		return forumService.findPostByAuthor(author);
	}

	@GetMapping(ForumURL.POST + "/{id}")
	public PostRepostDto findPostById(@PathVariable String id) {
		return forumService.findPostById(id);
	}

	@DeleteMapping(ForumURL.POST + "/{id}")
	public PostRepostDto deletePost(@PathVariable String id) {
		return forumService.deletePost(id);
	}

	@PutMapping(ForumURL.FORUM + ForumURL.POST + "/{id}")
	public PostRepostDto updatePost(@PathVariable String id, @RequestBody PostDto postDto) {
		return forumService.updatePost(postDto, id);
	}
}
