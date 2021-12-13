package com.evan.firstspring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.evan.firstspring.bean.*;
import com.evan.firstspring.mapper.*;
import com.evan.firstspring.result.Result;
import com.evan.firstspring.view.InventoryView;
import com.evan.firstspring.view.PandianView;
import com.evan.firstspring.view.PurchaseView;
import com.evan.firstspring.view.RecordView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
    private PurchaseMapper purchaseMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private PandianMapper pandianMapper;

    @GetMapping("tuning")
    public Result tuning(@RequestParam String srcRepositoryName, @RequestParam String dstRepositoryName, @RequestParam String productName, @RequestParam int productQuantity) {
        RecordController recordController = new RecordController(inventoryMapper);
        QueryWrapper<Repository> repositoryQueryWrapper = new QueryWrapper<>();
        repositoryQueryWrapper.eq("repository_name", srcRepositoryName).or().eq("repository_name", dstRepositoryName);
        List<Repository> repositoryList = repositoryMapper.selectList(repositoryQueryWrapper);

        if (repositoryList.size() != 2) {
            return new Result(400);
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
            return new Result(400);
        }
        int productId = productList.get(0).getProductId();

        System.out.println(recordController.getInventoryMapper());

        if (recordController.GetThisInventoryNumber(productId, productQuantity, srcRepositoryId)) {
            boolean result= recordController.outInventory(productId, productQuantity, srcRepositoryId) && recordController.inInventory(productId, productQuantity, dstRepositoryId);
            if(result)
            {
                Record srcrecord = new Record();
                srcrecord.setRecordProductId(productId);
                srcrecord.setRecordQuantity(-1*productQuantity);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                srcrecord.setRecordTime(timestamp);
                srcrecord.setRecordRepositoryId(srcRepositoryId);
                recordMapper.insert(srcrecord);

                Record dstrecord = new Record();
                dstrecord.setRecordProductId(productId);
                dstrecord.setRecordQuantity(productQuantity);
                dstrecord.setRecordTime(timestamp);
                dstrecord.setRecordRepositoryId(dstRepositoryId);
                recordMapper.insert(dstrecord);

                return new Result(200);
            }else{
                return new Result(400);
            }
        }

        return new Result(400);
    }

    @GetMapping("purchase")
    public List<PurchaseView> purchase(@RequestParam String repositoryName, @RequestParam String productName, @RequestParam int productQuantity, @RequestParam String staffID,@RequestParam BigDecimal productPrice) {
        RecordController recordController = new RecordController(inventoryMapper);
        //得到product
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("product_name", productName);
        List<Product> productList = productMapper.selectList(productQueryWrapper);
        if (productList.size() == 0) {
            return allPurchase();
        }
        Product product = productList.get(0);

        //得到repository
        QueryWrapper<Repository> repositoryQueryWrapper = new QueryWrapper<>();
        repositoryQueryWrapper.eq("repository_name", repositoryName);
        List<Repository> repositoryList = repositoryMapper.selectList(repositoryQueryWrapper);
        if (repositoryList.size() == 0) {
            return allPurchase();
        }
        Repository repository = repositoryList.get(0);

        //新增进货记录
        Purchase purchase = new Purchase();
        purchase.setPurchaseStaffId(staffID);
        purchase.setPurchaseRepositoryId(repository.getRepositoryId());
        purchase.setPurchaseProductId(product.getProductId());
        purchase.setPurchaseQuantity(productQuantity);
        purchase.setPurchasePrice(productPrice);
        Date date=new Date();
        purchase.setPurchaseTime(new Timestamp(date.getTime()));
        purchaseMapper.insert(purchase);

        //新增支付记录
        Account account = new Account();
        account.setAccountAmount(productPrice.multiply(BigDecimal.valueOf(productQuantity)));
        account.setAccountTime(new Timestamp(date.getTime()));
        account.setAccountIndex(purchase.getPurchaseId());
        account.setAccountTag(false);
        accountMapper.insert(account);

        //调整库存
        recordController.inInventory(product.getProductId(),productQuantity,repository.getRepositoryId());
        Record record = new Record();
        record.setRecordProductId(product.getProductId());
        record.setRecordQuantity(productQuantity);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        record.setRecordTime(timestamp);
        record.setRecordRepositoryId(repository.getRepositoryId());
        recordMapper.insert(record);

        return allPurchase();
    }

    @GetMapping("searchpurchase")
    public List<PurchaseView> searchPurchase(@RequestParam(required = false) String repositoryName,@RequestParam(required = false) String productName){
        QueryWrapper<Purchase> purchaseQueryWrapper=new QueryWrapper<>();

        // 仓库名
        if(repositoryName!=null&&!repositoryName.equals("")) {
            QueryWrapper<Repository> repositoryQueryWrapper = new QueryWrapper<>();
            repositoryQueryWrapper.eq("repository_name", repositoryName);
            List<Repository> repositoryList = repositoryMapper.selectList(repositoryQueryWrapper);
            Integer repositoryId = null;
            if (repositoryList.size() == 1) {
                repositoryId = repositoryList.get(0).getRepositoryId();
            }
            purchaseQueryWrapper.eq("purchase_repository_id", repositoryId);
        }

        // 货品名
        if(productName!=null&&!productName.equals("")) {
            QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
            productQueryWrapper.eq("product_name", productName);
            List<Product> productList = productMapper.selectList(productQueryWrapper);
            Integer productId = null;
            if (productList.size() == 1) {
                productId = productList.get(0).getProductId();
            }
            purchaseQueryWrapper.eq("purchase_product_id", productId);
        }

        return PurchaseView.getPurchaseViewList(purchaseMapper.selectList(purchaseQueryWrapper),staffMapper,productMapper,repositoryMapper);
    }

    @GetMapping("all")
    public List<PurchaseView> allPurchase() {
        return PurchaseView.getPurchaseViewList(purchaseMapper.selectList(null),staffMapper,productMapper,repositoryMapper);
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

    /**
     * 对库存的查询
     * @param repositoryName
     * @param productName
     * @return
     */
    @GetMapping("searchinventory")
    public Map<String,Object> searchInventory(@RequestParam(required = false) String repositoryName, @RequestParam(required = false) String productName) {
        QueryWrapper<Inventory> inventoryQueryWrapper = new QueryWrapper<>();

        // 仓库名
        if(repositoryName!=null&&!repositoryName.equals("")) {
            QueryWrapper<Repository> repositoryQueryWrapper = new QueryWrapper<>();
            repositoryQueryWrapper.eq("repository_name", repositoryName);
            List<Repository> repositoryList = repositoryMapper.selectList(repositoryQueryWrapper);
            Integer repositoryId = null;
            if (repositoryList.size() == 1) {
                repositoryId = repositoryList.get(0).getRepositoryId();
            }
            inventoryQueryWrapper.eq("inventory_repository_id", repositoryId);
        }

        // 货品名
        if(productName!=null&&!productName.equals("")) {
            QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
            productQueryWrapper.eq("product_name", productName);
            List<Product> productList = productMapper.selectList(productQueryWrapper);
            Integer productId = null;
            if (productList.size() == 1) {
                productId = productList.get(0).getProductId();
            }
            inventoryQueryWrapper.eq("inventory_product_id", productId);
        }

        Map<String,Object>map=new HashMap<>();
        List<InventoryView> inventoryViewList=InventoryView.getInventoryView(inventoryMapper.selectList(inventoryQueryWrapper),productMapper,repositoryMapper,inventoryMapper);
        map.put("inventoryView",inventoryViewList);

        BigDecimal stockMoney=BigDecimal.valueOf(0);
        for(InventoryView inventoryView:inventoryViewList){
            stockMoney=stockMoney.add(inventoryView.getProductStockMoney());
        }
        map.put("stockMoney",stockMoney);

        return map;
    }

    /**
     * 对出库记录的查询
     */
    @GetMapping("searchrecord")
    public List<RecordView> seacrchRecord(@RequestParam(required = false) String repositoryName, @RequestParam(required = false) String productName){
        QueryWrapper<Record> recordQueryWrapper=new QueryWrapper<>();

        // 仓库名
        if(repositoryName!=null&&!repositoryName.equals("")) {
            QueryWrapper<Repository> repositoryQueryWrapper = new QueryWrapper<>();
            repositoryQueryWrapper.eq("repository_name", repositoryName);
            List<Repository> repositoryList = repositoryMapper.selectList(repositoryQueryWrapper);
            Integer repositoryId = null;
            if (repositoryList.size() == 1) {
                repositoryId = repositoryList.get(0).getRepositoryId();
            }
            recordQueryWrapper.eq("record_repository_id", repositoryId);
        }

        // 货品名
        if(productName!=null&&!productName.equals("")) {
            QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
            productQueryWrapper.eq("product_name", productName);
            List<Product> productList = productMapper.selectList(productQueryWrapper);
            Integer productId = null;
            if (productList.size() == 1) {
                productId = productList.get(0).getProductId();
            }
            recordQueryWrapper.eq("record_product_id", productId);
        }

        recordQueryWrapper.le("record_quantity",0);

        return RecordView.getRecordViewList(recordMapper.selectList(recordQueryWrapper),productMapper,repositoryMapper);
    }

    @GetMapping("update")
    public List<PandianView> updateStock(@RequestParam String repositoryName,@RequestParam int productId,@RequestParam int newQuantity){
        RecordController recordController = new RecordController(inventoryMapper);

        QueryWrapper<Repository> repositoryQueryWrapper = new QueryWrapper<>();
        repositoryQueryWrapper.eq("repository_name", repositoryName);
        List<Repository> repositoryList = repositoryMapper.selectList(repositoryQueryWrapper);
        Repository repository = repositoryList.get(0);

        Product product=productMapper.selectById(productId);

        QueryWrapper<Inventory> inventoryQueryWrapper=new QueryWrapper<>();
        inventoryQueryWrapper.eq("inventory_product_id",product.getProductId());
        inventoryQueryWrapper.eq("inventory_repository_id",repository.getRepositoryId());
        List<Inventory> inventoryList=inventoryMapper.selectList(inventoryQueryWrapper);

        Inventory inventory;
        if(inventoryList.size()!=0){
            inventory=inventoryList.get(0);
        }else{
            inventory=new Inventory();
            inventory.setInventoryProductId(product.getProductId());
            inventory.setInventoryQuantity(0);
            inventory.setInventoryRepositoryId(repository.getRepositoryId());
            inventoryMapper.insert(inventory);
        }
        int oldQuantity=inventory.getInventoryQuantity();

        if(oldQuantity<newQuantity){
            recordController.inInventory(product.getProductId(),newQuantity-oldQuantity,repository.getRepositoryId());
        }else{
            recordController.outInventory(product.getProductId(),oldQuantity-newQuantity,repository.getRepositoryId());
        }

        Pandian pandian =new Pandian();
        pandian.setPandianProductId(product.getProductId());
        pandian.setPandianRepositoryId(repository.getRepositoryId());
        pandian.setPandianChangeQuantity(newQuantity-oldQuantity);
        pandianMapper.insert(pandian);

        Record record = new Record();
        record.setRecordProductId(productId);
        record.setRecordQuantity(newQuantity-oldQuantity);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        record.setRecordTime(timestamp);
        record.setRecordRepositoryId(repository.getRepositoryId());
        recordMapper.insert(record);

        return getAllPandian();
    }

    /**
     * 得到所有的盘点log
     * @return
     */
    @GetMapping("allpandian")
    public List<PandianView> getAllPandian(){
        return  PandianView.getPandianView(pandianMapper.selectList(null),productMapper,repositoryMapper);
    }
}
