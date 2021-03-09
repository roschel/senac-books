package com.senacbooks.senacbooks.products.images;

import com.senacbooks.senacbooks.categories.CategoryDTO;
import com.senacbooks.senacbooks.categories.CategoryEntity;
import com.senacbooks.senacbooks.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private S3Service s3Service;

    public List<ImageDTO> findAll(){
        List<ImageEntity> list = repository.findAll();

        return list.stream().map(x -> new ImageDTO(x)).collect(Collectors.toList());
    }

    public UriDTO uploadFile(MultipartFile file, Boolean principal) {
        URL url = s3Service.uploadFile(file);
        ImageEntity img = new ImageEntity();
        img.setImgUrl(url.toString());
        img.setPrincipal(principal);
        img = repository.save(img);

        return new UriDTO(url.toString(), img.getPrincipal());
    }

    public ImageEntity getImage(Long imageId) {
        return repository.findById(imageId).orElseThrow();
    }
}
