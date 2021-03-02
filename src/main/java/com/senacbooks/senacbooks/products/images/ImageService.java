package com.senacbooks.senacbooks.products.images;

import com.senacbooks.senacbooks.categories.CategoryDTO;
import com.senacbooks.senacbooks.categories.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepository repository;

    public List<ImageDTO> findAll(){
        List<ImageEntity> list = repository.findAll();

        return list.stream().map(x -> new ImageDTO(x)).collect(Collectors.toList());
    }
}
