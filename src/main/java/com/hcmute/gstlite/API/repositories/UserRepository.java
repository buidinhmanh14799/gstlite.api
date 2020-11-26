package com.hcmute.gstlite.API.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmute.gstlite.API.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	

	public User findByEmailAndPassword(String email, String password) ;
	public User findByUsernameAndPassword(String username, String password) ;
}
