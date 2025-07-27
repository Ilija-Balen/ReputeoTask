package com.example.reputeo.taskReputeo.rest.api;


import com.example.reputeo.taskReputeo.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface BaseControllerApi<Dto> {

    @GetMapping("/{id}")
    Dto get(@PathVariable long id) throws ResourceNotFoundException;

    @GetMapping
    Page<Dto> getAll(@RequestParam Map<String, Object> params, Pageable pageable);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Dto create(@RequestBody Dto dto);

    @PutMapping("/{id}")
    Dto update(@PathVariable long id, @RequestBody Dto dto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable long id);
}