package com.evan.firstspring.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.Product;
import com.evan.firstspring.mapper.ProductMapper;
import com.evan.firstspring.result.Result;
import com.evan.firstspring.view.ProductView;
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
    public Result deleteProduct(@RequestParam int productId) {
        int i = productMapper.deleteById(productId);

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
    public List<ProductView> searchProduct(@RequestParam String productName) {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("product_name", productName);

        return ProductView.getProductViewList(productMapper.selectList(productQueryWrapper),productMapper);
    }

    @GetMapping("all")
    public List<ProductView> getAllProduct() {
        return ProductView.getProductViewList(productMapper.selectList(null),productMapper);
    }

    @PostMapping("get")
    public Product getSpecificProduct(@RequestParam int productId){
        return productMapper.selectById(productId);
    }
}


