package com.senacbooks.senacbooks.products;

import com.senacbooks.senacbooks.categories.CategoryDTO;
import com.senacbooks.senacbooks.categories.CategoryEntity;
import com.senacbooks.senacbooks.categories.CategoryRepository;
import com.senacbooks.senacbooks.products.images.ImageDTO;
import com.senacbooks.senacbooks.products.images.ImageEntity;
import com.senacbooks.senacbooks.products.images.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll(){
        List<ProductEntity> list = repository.findAll();

        return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
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
        String retorno = "Livro " + entity.getTitle() + " já se encontra deletada.";
        if (entity.getStatus()) {
            entity.setStatus(false);
            entity = repository.save(entity);
            retorno = "Categoria " + entity.getTitle() + " deletado com sucesso.";
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
}
