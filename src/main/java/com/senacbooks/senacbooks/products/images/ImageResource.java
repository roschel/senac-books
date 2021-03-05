package com.senacbooks.senacbooks.products.images;

import com.senacbooks.senacbooks.categories.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/image")
    public ResponseEntity<UriDTO> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("principal") Boolean prinicipal) {
        UriDTO dto = service.uploadFile(file, prinicipal);
        return ResponseEntity.ok().body(dto);
    }
}
