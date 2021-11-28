package com.evan.firstspring.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "purchase")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    int purchaseId;
    @Column(name = "purchase_staff_id")
    int purchaseStaffId;
    @Column(name = "purchase_repository")
    String purchaseRepository;
    @Column(name = "purchase_product_id")
    String purchaseProductId;
    @Column(name = "purchase_quantity")
    int purchaseQuantity;
    @Column(name = "purchase_price")
    float purchasePrice;
    @Column(name = "purchase_date")
    String purchaseDate;

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getPurchaseStaffId() {
        return purchaseStaffId;
    }

    public void setPurchaseProductId(String purchaseProductId) {
        this.purchaseProductId = purchaseProductId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public String getPurchaseProductId() {
        return purchaseProductId;
    }

    public void setPurchaseRepository(String purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public String getPurchaseRepository() {
        return purchaseRepository;
    }

    public void setPurchaseStaffId(int purchaseStaffId) {
        this.purchaseStaffId = purchaseStaffId;
    }
}
