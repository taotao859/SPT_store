package com.evan.firstspring.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.evan.firstspring.bean.Product;
import com.evan.firstspring.mapper.ProductMapper;
import com.evan.firstspring.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductMapper productMapper;

    @PostMapping("add")
    public Result addProduct(@RequestBody Product product) {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("product_name", product.getProductName());
        List<Product> productList = productMapper.selectList(productQueryWrapper);

        if (productList.size() != 0) {
            return new Result(250);
        }

        int i = productMapper.insert(product);

        if (i > 0) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    @GetMapping("delete")
    public Result deleteProduct(@RequestBody Product product) {
        int i = productMapper.deleteById(product.getProductId());

        if (i > 0) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    @PostMapping("update")
    public Result updateProduct(@RequestBody Product product) {
        int i = productMapper.updateById(product);

        if (i > 0) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }

    @GetMapping("search")
    public List<Product> searchProduct(@RequestParam String productName) {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("product_name", productName);

        return productMapper.selectList(productQueryWrapper);
    }

    @GetMapping("all")
    public List<Product> getAllProduct() {
        return productMapper.selectList(null);
    }
}


