package com.renatofsimoes.workshopmongo.config;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.renatofsimoes.workshopmongo.domain.Post;
import com.renatofsimoes.workshopmongo.domain.User;
import com.renatofsimoes.workshopmongo.dto.AuthorDTO;
import com.renatofsimoes.workshopmongo.dto.CommentDTO;
import com.renatofsimoes.workshopmongo.repository.PostRepository;
import com.renatofsimoes.workshopmongo.repository.UserRepository;


@Configuration
public class Instantiation implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.of("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com"); 
		User alex = new User(null, "Alex Green", "alex@gmail.com"); 
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, LocalDate.parse("11/03/2025", dtf), "Partiu Viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null, LocalDate.parse("12/03/2025", dtf), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", LocalDate.parse("11/03/2025", dtf), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite!", LocalDate.parse("13/03/2025", dtf), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", LocalDate.parse("15/03/2025", dtf), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

}
