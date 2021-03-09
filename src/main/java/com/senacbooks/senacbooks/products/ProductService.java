package com.senacbooks.senacbooks.products;

import com.senacbooks.senacbooks.categories.CategoryDTO;
import com.senacbooks.senacbooks.categories.CategoryEntity;
import com.senacbooks.senacbooks.categories.CategoryRepository;
import com.senacbooks.senacbooks.products.images.ImageDTO;
import com.senacbooks.senacbooks.products.images.ImageEntity;
import com.senacbooks.senacbooks.products.images.ImageRepository;
import com.senacbooks.senacbooks.products.images.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Long categoryId, String title, PageRequest pageRequest){
        List<CategoryEntity> categories = (categoryId == 0)? null : Arrays.asList(categoryRepository.getOne(categoryId));
        Page<ProductEntity> page = repository.find(categories,title, pageRequest);
//        repository.find(page.toList());
        return page.map(x -> new ProductDTO(x, x.getImages(), x.getCategories()));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Optional<ProductEntity> obj = repository.findById(id);
        ProductEntity entity = obj.orElseThrow();

        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto){
        ProductEntity entity = new ProductEntity();
        copyDTOToEntity(dto, entity);
        entity = repository.save(entity);

        for (ImageEntity image: entity.getImages()) {
            addImageToProduct(entity.getId(), image.getId());
        }

        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto){
        ProductEntity entity = repository.getOne(id);
        copyDTOToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public String delete(Long id) {
        Optional<ProductEntity> obj = repository.findById(id);
        ProductEntity entity = obj.orElseThrow();
        String retorno;
        if (entity.getStatus()) {
            entity.setStatus(false);
            entity = repository.save(entity);
            retorno = "Livro " + entity.getTitle() + " inativado com sucesso.";
        }else{
            entity.setStatus(true);
            entity = repository.save(entity);
            retorno = "Livro " + entity.getTitle() + " reativado com sucesso.";
        }
        return retorno;
    }

    private void copyDTOToEntity(ProductDTO dto, ProductEntity entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setQuantity(dto.getQuantity());
        entity.setTitle(dto.getTitle());
        entity.setStatus(dto.getStatus());
        entity.setRating(dto.getRating());
        entity.setPrice(dto.getPrice());
        entity.setAuthor(dto.getAuthor());
        entity.setPublisher(dto.getPublisher());
        entity.setPages(dto.getPages());
        entity.setSize(dto.getSize());
        entity.setYear(dto.getYear());
        entity.setEdition(dto.getEdition());

        entity.getImages().clear();
        for (ImageDTO imageDTO : dto.getImages()) {
            ImageEntity image = imageRepository.getOne(imageDTO.getId());
            entity.getImages().add(image);
        }

        entity.getCategories().clear();
        for (CategoryDTO categoryDTO : dto.getCategories()) {
            CategoryEntity category = categoryRepository.getOne(categoryDTO.getId());
            entity.getCategories().add(category);
        }
    }

    public ProductEntity getproduct(Long id){
        return repository.findById(id).orElseThrow();
    }

    public void addImageToProduct(Long productId, Long imageId){
        ProductEntity productEntity = getproduct(productId);
        ImageEntity imageEntity = imageService.getImage(imageId);

        imageEntity.setProduct(productEntity);
    }
}
