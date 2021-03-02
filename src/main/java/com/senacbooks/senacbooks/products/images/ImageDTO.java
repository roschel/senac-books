package com.senacbooks.senacbooks.products.images;

import com.senacbooks.senacbooks.products.ProductDTO;
import com.senacbooks.senacbooks.products.ProductEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class ImageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String imgUrl;

    private Set<ProductDTO> products = new HashSet<>();

    public ImageDTO() {
    }

    public ImageDTO(Long id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }

    public ImageDTO(ImageEntity entity){
        this.id = entity.getId();
        this.imgUrl = entity.getImgUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

//    public Set<ProductDTO> getProducts() {
//        return products;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageDTO that = (ImageDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
