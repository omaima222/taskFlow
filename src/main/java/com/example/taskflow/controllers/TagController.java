package com.example.taskflow.controllers;

import com.example.taskflow.dtos.TagDto;
import com.example.taskflow.entities.Tag;
import com.example.taskflow.services.interfaces.TagService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tag")
public class TagController {
    TagService tagService;

    TagController(TagService tagService){this.tagService=tagService;}

    @GetMapping("")
    public List<TagDto> getAll(){return  this.tagService.getAll();}

    @GetMapping("/{id}")
    public Tag findById(@PathVariable Long id){return  this.tagService.findById(id);}

    @PostMapping("")
    public TagDto add(@RequestBody @Valid TagDto tagDto){
        return this.tagService.save(tagDto);
    }

    @PutMapping("/{id}")
    public TagDto update(@PathVariable Long id, @RequestBody @Valid TagDto tagDto){
        tagDto.setId(id);
        return this.tagService.save(tagDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.tagService.delete(id);
    }
}
