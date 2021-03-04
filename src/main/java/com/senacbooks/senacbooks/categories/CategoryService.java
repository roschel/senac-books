package com.senacbooks.senacbooks.categories;

import com.senacbooks.senacbooks.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
        Page<CategoryEntity> list = repository.findAll(pageRequest);

        return list.map(x -> new CategoryDTO(x));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<CategoryEntity> obj = repository.findById(id);
        CategoryEntity entity = obj.orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
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
