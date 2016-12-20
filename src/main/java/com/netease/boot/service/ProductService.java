package com.netease.boot.service;

import com.netease.boot.dal.EMailAddress;
import com.netease.boot.dal.Product;
import com.netease.boot.service.cache.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Created by Michael Jiang on 16-12-20.
 */
@Service
public class ProductService {

    @Resource
    private RedisService redisService;

    public void save(String code, String name, String description, String principalEMail) {
        EMailAddress eMailAddress = new EMailAddress(principalEMail);
        Product product = new Product(code, name, description, eMailAddress);
        redisService.put("product_key_" + code, product);
    }

    public Optional<Product> getProduct(String code) {
        Product product = (Product) redisService.get("product_key_"+code);
        return Optional.ofNullable(product);
    }
}
