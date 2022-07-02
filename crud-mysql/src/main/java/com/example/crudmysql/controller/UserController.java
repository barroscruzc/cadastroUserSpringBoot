package com.example.crudmysql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.crudmysql.model.User;
import com.example.crudmysql.model.UserRepository;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("unused")
@Controller // Informa que classe UserController é o nosso controller
@RequestMapping(path = "/crud")
public class UserController {

	@Autowired // Comunica com o UserRepository
	private UserRepository userRepository;

	@GetMapping(path = "/")
	public String inicio() {
		return "index";
	}

	// CRUD - Read (query 'select * from user')
	@RequestMapping(path = "/all")
	public String listaUsers(Model model) {
		model.addAttribute("user", userRepository.findAll());
		return "listar";
	}

	// CRUD - Create
	@GetMapping("/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("user", new User());
		return "formulario";
	}

	@PostMapping("/add")
	public String addUser(@RequestParam String nome, @RequestParam String email, Model model) {

		// realiza o encapsulamento dos dasos
		User u = new User();
		u.setNome(nome);
		u.setEmail(email);
		// salvar o novo Usuario no banco
		userRepository.save(u);;
		return "redirect:/crud/all";
	}


	// CRUD - update
	@GetMapping(path="/update/{id}")
	public String alterarPessoa(@PathVariable("id") Integer id, Model model) {
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent()) {
			throw new IllegalArgumentException("Cadastro inválido.");
		}
		model.addAttribute("user", userOpt.get());
		
		return "alterar";
	}
	
	@PostMapping("/update/salvar")
	public String salvarUser(@Valid @ModelAttribute("pessoa") User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "alterar";
		}
		
		userRepository.save(user);
		return "redirect:/crud/all";
	}
	
	/*@PostMapping(path="/salvar/{id}")
	public String alterar(@PathVariable ("id") Integer id, Model model) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			throw new IllegalArgumentException("Pessoa inválida");
		}
		model.addAttribute("user", userOpt.get());
		return "alterar";
	}*/

	// CRUD - Delete
	@RequestMapping(path="/delete/{id}")
	public String deleteUser(@PathVariable Integer id) {
		userRepository.deleteById(id);
		return "redirect:/crud/all";
	}
}
