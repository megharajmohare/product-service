package com.target.productservice;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable int id) {

        Product product = new Product();
        product.setId(id);

        ResponseEntity<JsonNode> productInfo = restTemplate.getForEntity("https://redsky.target.com/v2/pdp/tcin/" + id + "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics", JsonNode.class);
        if (productInfo != null && productInfo.getBody() != null) {
            product.setName(productInfo.getBody().get("product").get("item").get("product_description").get("title").asText());
        }

        Price price = restTemplate.getForObject("http://localhost:8082/product/" + id + "/price", Price.class);
        if (price != null) {
            CurrentPrice currentPrice = new CurrentPrice();
            currentPrice.setCurrency_code(price.getCurrency_code());
            currentPrice.setValue(price.getValue());
            product.setCurrent_price(currentPrice);
        }
        return product;
    }

    @PostMapping("/{id}")
    public String addProduct(@PathVariable int id, @RequestBody Product product) {
        Price price = new Price();
        price.setCurrency_code(product.getCurrent_price().getCurrency_code());
        price.setProductId(product.getId());
        price.setValue(product.getCurrent_price().getValue());
        restTemplate.postForObject("http://localhost:8082/product/" + id + "/price", price, Price.class);
        return "Product's price is updated successfully";
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable int id, @RequestBody Product product) {
        Price price = new Price();
        price.setCurrency_code(product.getCurrent_price().getCurrency_code());
        price.setProductId(product.getId());
        price.setValue(product.getCurrent_price().getValue());
        restTemplate.postForObject("http://localhost:8082/product/" + id + "/price", price, Price.class);
        return "Product's price is updated successfully";
    }
}
