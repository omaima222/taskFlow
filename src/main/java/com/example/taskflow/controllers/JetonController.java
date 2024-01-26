package com.example.taskflow.controllers;

import com.example.taskflow.dtos.IdsRequest;
import com.example.taskflow.dtos.request.JetonRequestDto;
import com.example.taskflow.dtos.response.JetonResponseDto;
import com.example.taskflow.entities.Jeton;
import com.example.taskflow.services.JetonServiceImp;
import com.example.taskflow.services.interfaces.JetonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("")
    public ResponseEntity<JetonResponseDto> add(@RequestBody @Valid JetonRequestDto jetonRequestDto) throws ValidationException {
        JetonResponseDto jetonResponseDto = this.jetonService.save(jetonRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(jetonResponseDto);    }

    @PutMapping("/{id}")
    public ResponseEntity<JetonResponseDto> update(@RequestBody @Valid JetonRequestDto jetonRequestDto, @PathVariable Long id) throws ValidationException{
        jetonRequestDto.setId(id);
        JetonResponseDto jetonResponseDto = this.jetonService.save(jetonRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(jetonResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        this.jetonService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Jeton deleted successfully");
    }

    @PostMapping("accept/{id}")
    public ResponseEntity<String> accept(@PathVariable Long id, @RequestBody @Valid IdsRequest idsRequest) throws ValidationException{
        this.jetonService.handleDemand(id, idsRequest.getUser_id(), idsRequest.getTo_user_id(),  "accept");
        return ResponseEntity.status(HttpStatus.OK).body("Jeton accepted successfully");
    }

    @PostMapping("decline/{id}")
    public ResponseEntity<String> decline(@PathVariable Long id, @RequestBody @Valid IdsRequest idsRequest) throws ValidationException{
        this.jetonService.handleDemand(id, idsRequest.getUser_id(), null, "decline");
        return ResponseEntity.status(HttpStatus.OK).body("Jeton declined successfully");
    }

}
