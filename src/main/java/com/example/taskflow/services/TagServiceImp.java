package com.example.taskflow.services;

import com.example.taskflow.dtos.TagDto;
import com.example.taskflow.entities.Tag;
import com.example.taskflow.mappers.interfaces.TagMapper;
import com.example.taskflow.repositories.TagRepository;
import com.example.taskflow.services.interfaces.TagService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImp implements TagService {
    TagRepository tagRepository;

    TagMapper tagMapper;

    TagServiceImp(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository=tagRepository;
        this.tagMapper=tagMapper;
    }

    public List<TagDto> getAll(){
        List<Tag> tags = this.tagRepository.findAll();
        List<TagDto> tagDtos = tags.stream().map(t->tagMapper.EntityToDto(t)).collect(Collectors.toList());
        return tagDtos;
    }

    public Tag findById(Long id){
        Optional<Tag> tag = this.tagRepository.findById(id);
        if(tag.isPresent()) return tag.get();
        else throw new EntityNotFoundException("Tag not found !");
    }

    public Tag findByName(String name){
        return this.tagRepository.findTagByName(name);
    }

    public TagDto save(TagDto tagDto){
        tagDto.setName(tagDto.getName().toLowerCase());
        Tag tag = tagMapper.dtoToEntity(tagDto);
        tagDto = tagMapper.EntityToDto(this.tagRepository.save(tag));
        return tagDto;
    }

    public void delete(Long id){
        Tag tag = this.findById(id);
        this.tagRepository.delete(tag);
    }
}
