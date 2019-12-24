package zeljko.ngspringblog.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import zeljko.ngspringblog.model.Post;
import zeljko.ngspringblog.model.PostDto;
import zeljko.ngspringblog.repository.PostRepository;

/**
 * PostService
 */

@Service
 public class PostService {

    @Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;

    public void createPost(PostDto postDto) {

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        // za username nam treba ime trenutno logovanog usera - to iz auth service
        String loggedUser = authService.getUsername();
        // ako je null znaci nema logovanog usera - baci exception
        if (loggedUser == null) {
            throw new IllegalArgumentException("No current logged user");
        }
        post.setUserName(loggedUser);
        post.setCreatedOn(Instant.now());
        postRepository.save(post);

    }

    public List<PostDto> getAllPost() {
        List<Post> lista = postRepository.findAll();
        // convert post u postdto - java 8 varijanta sa konverzijom mnogo objekata u
        // opet mnogo objekata ide stream-map-collect
        // ulaz u stream , map sta radimo tj.koja je funkcija i na kraju skupimo colect
        // u sta hocemo
        /*
         * lista.stream().map(this::convertPostToPostDto ).collect(Collectors.toList());
         * ili isto to na drugi nacin lista.stream().map((x) ->
         * convertPostToPostDto(x)).collect(Collectors.toList());
         */
        return lista.stream().map(this::convertPostToPostDto).collect(Collectors.toList());
    }

    public PostDto getPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        post.orElseThrow(() -> new IllegalArgumentException("Post with id : " + id + " not exist"));
        // ako ga je nasao
        
        return post.map((x) -> convertPostToPostDto(x)).get();
        
        
        
        
        
	}

    private PostDto convertPostToPostDto (Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUserName(post.getUserName());
        return postDto;
    }
} 