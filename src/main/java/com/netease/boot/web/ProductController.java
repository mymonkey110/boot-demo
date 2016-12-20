package com.netease.boot.web;

import com.netease.boot.dal.Product;
import com.netease.boot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Michael Jiang on 16-12-20.
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveProduct(@RequestParam("code") String code,
                            @RequestParam("name") String name,
                            @RequestParam("description") String description,
                            @RequestParam("email") String principalEMail) {
        productService.save(code, name, description, principalEMail);
    }

    @RequestMapping(value = "/get/{code}", method = RequestMethod.GET)
    public void getProduct(@PathVariable("code") String code) {
        Optional<Product> result = productService.getProduct(code);
        Product product = result.orElseThrow(() -> new IllegalStateException("get product:" + code + " error"));
        System.out.println(product);
    }
}
