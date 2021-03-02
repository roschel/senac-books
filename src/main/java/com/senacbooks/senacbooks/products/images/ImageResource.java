package com.senacbooks.senacbooks.products.images;

import com.senacbooks.senacbooks.categories.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/images")
public class ImageResource {

    @Autowired
    private ImageService service;

    @GetMapping
    public ResponseEntity<List<ImageDTO>> findAll(){
        List<ImageDTO> list = service.findAll();

        return ResponseEntity.ok().body(list);
    }
}
