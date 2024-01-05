package com.example.taskflow.controllers;

import com.example.taskflow.dtos.request.JetonRequestDto;
import com.example.taskflow.dtos.response.JetonResponseDto;
import com.example.taskflow.entities.Jeton;
import com.example.taskflow.services.JetonServiceImp;
import com.example.taskflow.services.interfaces.JetonService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequestMapping("api/jeton")
public class JetonController {
    JetonService jetonService;

    JetonController(JetonService jetonService){this.jetonService=jetonService;}

    @GetMapping("")
    public List<JetonResponseDto> getAll(){return  this.jetonService.getAll();}

    @GetMapping("/{id}")
    public Jeton findById(@PathVariable Long id){return  this.jetonService.findById(id);}

    @PostMapping("")
    public JetonResponseDto add(@RequestBody @Valid JetonRequestDto jetonRequestDto) throws ValidationException {
        return this.jetonService.save(jetonRequestDto);
    }

    @PutMapping("/{id}")
    public JetonResponseDto update(@RequestBody @Valid JetonRequestDto jetonRequestDto, @PathVariable Long id) throws ValidationException{
        jetonRequestDto.setId(id);
        return this.jetonService.save(jetonRequestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.jetonService.delete(id);
    }
}
