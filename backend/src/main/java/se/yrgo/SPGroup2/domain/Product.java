package se.yrgo.SPGroup2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.web.util.HtmlUtils;
import se.yrgo.SPGroup2.enums.ProductSize;
import se.yrgo.SPGroup2.enums.ProductType;
import se.yrgo.SPGroup2.validators.ProductValidator;

import java.util.List;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique=true)
    private String artNum;
    @Enumerated(EnumType.STRING)
    private ProductType type;
    private String model;
    @Enumerated(EnumType.STRING)
    private ProductSize size;
    private String color;
    private int price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Stock stock;

    @ManyToMany()
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "filename",referencedColumnName = "filename"))
    private List<Photo> photoList;

    public Product() {
    }

    public Product(String artNum, ProductType type, String model, ProductSize size, String color, int price) {
        this.artNum = artNum;
        this.type = type;
        this.model = model;
        this.size = size;
        this.color = color;
        this.price = price;
//        this.stock = new Stock(0, this);
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getArtNum() {

        return ProductValidator.validateStringInput(artNum);
    }

    public void setArtNum(String artNum) {
        this.artNum = artNum;
    }

    public ProductType getType() {
       return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getModel() {
        return ProductValidator.validateStringInput(model);
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ProductSize getSize() {
        return size;
    }

    public void setSize(ProductSize size) {
        this.size = size;
    }

    public String getColor() {
        return ProductValidator.validateStringInput(color);
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && price == product.price && Objects.equals(artNum, product.artNum) && type == product.type && Objects.equals(model, product.model) && size == product.size && Objects.equals(color, product.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, artNum, type, model, size, color, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", artNum='" + artNum + '\'' +
                ", type=" + type +
                ", model='" + model + '\'' +
                ", size=" + size +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", photoList=" + photoList +
                '}';
    }
}