package com.senacbooks.senacbooks.categories;

import com.senacbooks.senacbooks.products.ProductDTO;
import com.senacbooks.senacbooks.products.ProductEntity;
import com.senacbooks.senacbooks.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<CategoryEntity> list = repository.findAll();

        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<CategoryEntity> obj = repository.findById(id);
        CategoryEntity entity = obj.orElseThrow();
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        copyDTOToEntity(dto, entity);
        entity = repository.save(entity);

        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        CategoryEntity entity = repository.getOne(id);
        copyDTOToEntity(dto, entity);
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public String delete(Long id) {
        Optional<CategoryEntity> obj = repository.findById(id);
        CategoryEntity entity = obj.orElseThrow();
        String retorno = "Categoria " + entity.getName() + " já se encontra deletada.";
        if (entity.getStatus()) {
            entity.setStatus(false);
            entity = repository.save(entity);
            retorno = "Categoria " + entity.getName() + " deletado com sucesso.";
        }
        return retorno;
    }

    private void copyDTOToEntity(CategoryDTO dto, CategoryEntity entity) {
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
    }

}
