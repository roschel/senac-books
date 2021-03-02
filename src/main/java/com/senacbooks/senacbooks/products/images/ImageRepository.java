package com.senacbooks.senacbooks.products.images;

import com.senacbooks.senacbooks.categories.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
