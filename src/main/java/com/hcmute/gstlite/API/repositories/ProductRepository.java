/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hcmute.gstlite.API.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.hcmute.gstlite.API.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	@Modifying
    public List<Product> findByNameLike(String productName);
}
