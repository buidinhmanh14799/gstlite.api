package com.hcmute.gstlite.API.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmute.gstlite.API.entities.User;
import com.hcmute.gstlite.API.exceptions.ResourceNotFoundException;
import com.hcmute.gstlite.API.repositories.UserRepository;
@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Get all users list.
	 * 
	 * @return the list
	 */
	@GetMapping("/list")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	
	/**
	 * Get user by id
	 * 
	 * @param id the user id
	 * @return user by id
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/get/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) throws
	ResourceNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on: " + id));
		return ResponseEntity.ok().body(user);
}
	
	/**
	 * Create new user
	 * 
	 * @param user
	 * @return user
	 */
    @PostMapping("/add")
    public User create(@Validated @RequestBody User user){
        return userRepository.save(user);
    }
    
    
    /**
     * Update user
     * 
     * @param id
     * @param user detail user
     * @return user
     * @throws ResourceNotFoundException
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@PathVariable long id,@Validated @RequestBody User user) throws ResourceNotFoundException
    {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found on: "+ id));
        userToUpdate.setPassword(user.getPassword());
        return ResponseEntity.ok().body(userToUpdate);
    }
    
    /**
     * Delete user by id
     * 
     * @param id the user id
     * @return the map
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Long userId) throws Exception 
    {
	    User user = userRepository.findById(userId)
	    		.orElseThrow(() -> new ResourceNotFoundException("User not found on: " + userId));
	    userRepository.delete(user);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
    }
    
    /**
     * Check user login
     * 
     * @param username
     * @param password
     * @return user
     */
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user)
    {
        User userLogin = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(userLogin == null)
        {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok().body(userLogin);
    }
	
	
}

