package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.*;
import com.evan.firstspring.mapper.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/repositorymanange")
@CrossOrigin
public class RepositoryManagerController {
    @Autowired
    private RepositoryMapper repositoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RecordController recordController;
    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private InventoryMapper inventoryMapper;

    @PostMapping("tuning")
    public boolean tuning(@RequestParam String srcRepositoryName, @RequestParam String dstRepositoryName, @RequestParam String productName, @RequestParam int productQuantity) {
        QueryWrapper<Repository> repositoryQueryWrapper = new QueryWrapper<>();
        repositoryQueryWrapper.eq("repository_name", srcRepositoryName).or().eq("repository_name", dstRepositoryName);
        List<Repository> repositoryList = repositoryMapper.selectList(repositoryQueryWrapper);

        if (repositoryList.size() != 2) {
            return false;
        }

        int srcRepositoryId;
        int dstRepositoryId;
        if (repositoryList.get(0).getRepositoryName().equals(srcRepositoryName)) {
            srcRepositoryId = repositoryList.get(0).getRepositoryId();
            dstRepositoryId = repositoryList.get(1).getRepositoryId();
        } else {
            srcRepositoryId = repositoryList.get(1).getRepositoryId();
            dstRepositoryId = repositoryList.get(0).getRepositoryId();
        }

        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("product_name", productName);
        List<Product> productList = productMapper.selectList(productQueryWrapper);
        if (productList.size() == 0) {
            return false;
        }
        int productId = productList.get(0).getProductId();

        if (recordController.GetThisInventoryNumber(productId, productQuantity, srcRepositoryId)) {
            return recordController.outInventory(productId, productQuantity, srcRepositoryId) && recordController.inInventory(productId, productQuantity, dstRepositoryId);
        }

        return false;
    }

    @PostMapping("purchase")
    public boolean purchase(@RequestParam String repositoryName, @RequestParam String productName, @RequestParam int productQuantity, @RequestParam String staffID) {
        //得到product
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("product_name", productName);
        List<Product> productList = productMapper.selectList(productQueryWrapper);
        if (productList.size() == 0) {
            return false;
        }
        Product product = productList.get(0);

        //得到repository
        QueryWrapper<Repository> repositoryQueryWrapper = new QueryWrapper<>();
        repositoryQueryWrapper.eq("repository_name", repositoryName);
        List<Repository> repositoryList = repositoryMapper.selectList(repositoryQueryWrapper);
        if (repositoryList.size() == 0) {
            return false;
        }
        Repository repository = repositoryList.get(0);

        //新增进货记录
        Purchase purchase = new Purchase();
        purchase.setPurchaseStaffId(staffID);
        purchase.setPurchaseRepositoryId(repository.getRepositoryId());
        purchase.setPurchaseProductId(product.getProductId());
        purchase.setPurchaseQuantity(productQuantity);
        purchase.setPurchasePrice(product.getProductCost());
        purchase.setPurchaseTime(new DateTime());
        purchaseMapper.insert(purchase);

        //新增支付记录
        Account account = new Account();
        account.setAccountAmount(product.getProductCost().multiply(BigDecimal.valueOf(productQuantity)));
        account.setAccountTime(new DateTime());
        account.setAccountIndex(purchase.getPurchaseId());
        account.setAccountTag(false);
        accountMapper.insert(account);

        return recordController.inInventory(product.getProductId(), productQuantity, repository.getRepositoryId());
    }

    @PostMapping("all")
    public List<Purchase> allPurchase() {
        return purchaseMapper.selectList(null);
    }

    @PostMapping("options")
    public Map<String, Object> options() {
        Map<String, Object> objectMap = new HashMap<>();
        List<Repository> repositoryList = repositoryMapper.selectList(null);
        Set<String> repositoryNameSet = new HashSet<>();
        for (Repository repository:repositoryList){
            repositoryNameSet.add(repository.getRepositoryName());
        }
        List<String> repository = new ArrayList<>(repositoryNameSet);
        objectMap.put("repositoryNameList", repository);

        List<Product> productList = productMapper.selectList(null);
        Set<String> productNameSet = new HashSet<>();
        for (Product product:productList){
            productNameSet.add(product.getProductName());
        }
        List<String> productNameList = new ArrayList<>(productNameSet);
        objectMap.put("productNameList", productNameList);
        return objectMap;
    }

    @PostMapping("search")
    public List<Inventory> search(@RequestParam(required = false) String repositoryName, @RequestParam(required = false) String productName) {
        QueryWrapper<Inventory> inventoryQueryWrapper = new QueryWrapper<>();

        // 仓库名
        QueryWrapper<Repository> repositoryQueryWrapper = new QueryWrapper<>();
        repositoryQueryWrapper.eq("repository_name", repositoryName);
        List<Repository> repositoryList = repositoryMapper.selectList(repositoryQueryWrapper);
        Integer repositoryId = null;
        if(repositoryList.size() == 1){
            repositoryId = repositoryList.get(0).getRepositoryId();
        }
        inventoryQueryWrapper.eq("inventory_repository_id", repositoryId);

        // 货品名
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("product_name", productName);
        List<Product> productList = productMapper.selectList(productQueryWrapper);
        Integer productId = null;
        if(productList.size() == 1){
            productId = productList.get(0).getProductId();
        }
        inventoryQueryWrapper.eq("inventory_product_id", productId);

        return inventoryMapper.selectList(inventoryQueryWrapper);
    }
}
