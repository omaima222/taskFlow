package com.example.taskflow.controllers;

import com.example.taskflow.dtos.TagDto;
import com.example.taskflow.entities.Tag;
import com.example.taskflow.services.interfaces.TagService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequestMapping("api/tag")
public class TagController {
    TagService tagService;

    TagController(TagService tagService){this.tagService=tagService;}

    @GetMapping("")
    public List<TagDto> getAll(){return  this.tagService.getAll();}

    @PostMapping("")
    public ResponseEntity<TagDto> add (@RequestBody @Valid TagDto tagDto) throws ValidationException {
        TagDto tag = this.tagService.save(tagDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> update(@PathVariable Long id, @RequestBody @Valid TagDto tagDto){
        tagDto.setId(id);
        TagDto tag = this.tagService.save(tagDto);
        return ResponseEntity.status(HttpStatus.OK).body(tag);    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        this.tagService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Tag deleted successfully");
    }

}

