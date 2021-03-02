package com.senacbooks.senacbooks.categories;

import com.senacbooks.senacbooks.products.ProductDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Boolean status;

    private Set<ProductDTO> products = new HashSet<>();

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name, Boolean status) {
        this.id = id;
        this.name = name;
        this.status=status;
    }

    public CategoryDTO(CategoryEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.status = entity.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
