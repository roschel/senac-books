package com.senacbooks.senacbooks.products;

import com.senacbooks.senacbooks.categories.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT DISTINCT obj FROM ProductEntity obj INNER JOIN obj.categories cats WHERE " +
            "(COALESCE(:categories) IS NULL OR cats IN :categories) AND " +
            "(LOWER(obj.title) LIKE LOWER(CONCAT('%',:title,'%')) )")
    Page<ProductEntity> find(List<CategoryEntity> categories, String title, Pageable pageable);

//    @Query("SELECT obj FROM ProductEntity obj JOIN FETCH obj.categories WHERE obj IN :products")
//    List<ProductEntity> find(List<ProductEntity> products);

}
