package com.renatofsimoes.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renatofsimoes.workshopmongo.domain.User;
import com.renatofsimoes.workshopmongo.dto.UserDTO;
import com.renatofsimoes.workshopmongo.repository.UserRepository;
import com.renatofsimoes.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findById(String id) { 
		 Optional<User> obj = userRepository.findById(id); 
		 return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado")); 
	}
	
	public User insert(User obj) {
		return userRepository.insert(obj);
	}
	
	public void delete (String id) {
		findById(id); //caso não encontrar o id dado já lançara a exceção
		userRepository.deleteById(id);
	}
	
	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
