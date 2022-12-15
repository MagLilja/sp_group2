package se.yrgo.SPGroup2.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.yrgo.SPGroup2.domain.Product;
import se.yrgo.SPGroup2.domain.payloads.AddProductRequest;
import se.yrgo.SPGroup2.repositories.ProductRepository;
import se.yrgo.SPGroup2.services.AdminProductService;
import se.yrgo.SPGroup2.services.PhotoAlreadyExistsException;
import se.yrgo.SPGroup2.services.ProductAlreadyExistsException;

import java.util.Optional;


@RestController
@RequestMapping(value = "/admin/products")
public class AdminProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AdminProductService productService;


    @PostMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(@RequestBody AddProductRequest productRequestPayload) {

        try {
            return productService.createProduct(productRequestPayload);
        } catch (ProductAlreadyExistsException e) {
            return new ResponseEntity<>("Product with Art Nr already exists", HttpStatus.I_AM_A_TEAPOT);
        } catch (PhotoAlreadyExistsException e) {
            return new ResponseEntity<>("Photo with that filename already exists", HttpStatus.I_AM_A_TEAPOT);
        }


    }


    @PutMapping(value = "/{artNr}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Product updateProducts(@PathVariable String artNr, @RequestBody Product product) {
//        Product productToUpdate = productRepository.findByArtNum(artNr);
//        productToUpdate.setArtNum(product.getArtNum());
//        productToUpdate.setType(product.getType());
//        productToUpdate.setModel(product.getModel());
//        productToUpdate.setSize(product.getSize());
//        productToUpdate.setColor(product.getColor());
//        productToUpdate.setPrice(product.getPrice());
//        productRepository.save(productToUpdate);
        return null;
    }

    @DeleteMapping(value = "/{artNr}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Product> deleteProduct(@PathVariable String artNr) {
        Optional<Product> productToDelete = productRepository.findByArtNum(artNr);
        productToDelete.ifPresentOrElse(productRepository::delete, () -> {
            throw new ProductNotFoundException("Product not found");
        });
        return ResponseEntity.ok().build();
    }
}
