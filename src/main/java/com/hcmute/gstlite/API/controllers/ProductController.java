package com.hcmute.gstlite.API.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcmute.gstlite.API.entities.OrderItem;
import com.hcmute.gstlite.API.entities.Product;
import com.hcmute.gstlite.API.exceptions.ResourceNotFoundException;
import com.hcmute.gstlite.API.repositories.ProductRepository;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
    private ProductRepository productRepository;

	/**
	 * Get list product
	 * 
	 * @return list product
	 */
    @GetMapping("/list")
    public List<Product> getAll(){
        return productRepository.findAll();
    }

    /**
     * Get product by id
     * @param id the product id
     * @return product
     * @throws ResourceNotFoundException not found product by id
     */
    @GetMapping("/get/{id}")
    public Product getProductById(@PathVariable long id) throws ResourceNotFoundException
    {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found product "+ id));
    }

    /**
     * Create new product
     * 
     * @param product the product detail
     * @return product
     */
    @PostMapping("/add")
    public Product create(@Validated @RequestBody Product product){
        return productRepository.save(product);
    }
    
    /**
     * Update product by id
     * @param id the product id
     * @param product the product detail
     * @return product
     * @throws ResourceNotFoundException not found product by id
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> update(@PathVariable long id, @Validated @RequestBody Product product) throws ResourceNotFoundException
    {
        Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found product" + id));
        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setCategory(product.getCategory());
        productToUpdate.setProductCondition(product.getProductCondition());
        productToUpdate.setManufacturer(product.getManufacturer());
        productToUpdate.setImage(product.getImage());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setUnitInStock(product.getUnitInStock());
        productRepository.save(productToUpdate);
        return ResponseEntity.ok().body(productToUpdate);
    }

    /**
     * Delete product by id
     * @param id the product id
     * @return 
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> delete(@PathVariable long id) throws ResourceNotFoundException{
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found product" + id));
        productRepository.delete(product);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
    }
    
    /**
     * Search product by name 
     * @param productName the name of product
     * @return list product by product name
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductByName(@RequestParam("productName") String productName)
    {
        List<Product> products = productRepository.findByNameLike("%"+productName+"%");
        if(products == null)
        {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok().body(products);
    }
    
    
    @PostMapping("/checkout")
    public  ResponseEntity<Map<String, Boolean>> checkOut(@Validated @RequestBody List<OrderItem> items )
    {
    	for(int i=0;i<items.size();i++) {
    	 Product product =	productRepository.findById(items.get(i).getId()).get();
    	 int inStock = product.getUnitInStock() - items.get(i).getQuantity();
    	 product.setUnitInStock(inStock);
    	 productRepository.save(product);
    	}
    	Map<String, Boolean> response = new HashMap<>();
	    response.put("checkout", Boolean.TRUE);
	    return ResponseEntity.ok().body(response);
    }
   
    
}
