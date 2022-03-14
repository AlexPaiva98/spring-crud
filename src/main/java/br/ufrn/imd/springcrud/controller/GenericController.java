package br.ufrn.imd.springcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.ufrn.imd.springcrud.exception.EntityNotFoundException;
import br.ufrn.imd.springcrud.exception.ValidationException;
import br.ufrn.imd.springcrud.service.GenericService;
import br.ufrn.imd.springcrud.service.FilterService;

@RestController
@CrossOrigin
public abstract class GenericController<Model, Dto> {
    protected Map<String, FilterService<Dto>> filters;
    protected abstract GenericService<Model, Dto> getService();

    protected GenericController() {
        this.filters = new HashMap<>();
    }

    @Autowired(required = false)
    public void setFilters(List<FilterService<Dto>> filters) {
        this.filters = filters.stream().collect(Collectors.toMap(FilterService::getName, Function.identity()));
    }

    @GetMapping()
    public ResponseEntity<Collection<Dto>> findAll(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page) {
        return ResponseEntity.ok(this.getService().convertToDTOList(this.getService().findAll(limit, page)));
    }

    @GetMapping("{id}")
    public ResponseEntity<Dto> findById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(this.getService().convertToDto(this.getService().findById(id)));
    }

    @GetMapping("filters")
    public ResponseEntity<Collection<String>> getFilters() {
        return ResponseEntity.ok(this.filters.keySet());
    }

    @GetMapping("filters/{filter_name}")
    public ResponseEntity<Collection<Dto>> filter(@PathVariable(value = "filter_name") String filterName, @RequestParam Map<String, String> dataQuery) throws ValidationException {
        return ResponseEntity.ok(this.filters.get(filterName).filterTemplate(dataQuery));
    }

    @PostMapping()
    public ResponseEntity<Dto> save(@RequestBody Dto dto) throws ValidationException {
        return ResponseEntity.ok(this.getService().convertToDto(this.getService().save(dto)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Dto> update(@PathVariable Long id, @RequestBody Dto dto) throws ValidationException {
        return ResponseEntity.ok(this.getService().convertToDto(this.getService().update(id, dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dto> updateAll(@PathVariable Long id, @RequestBody Dto dto) throws ValidationException {
        return ResponseEntity.ok(this.getService().convertToDto(this.getService().updateAll(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws EntityNotFoundException {
        this.getService().deleteById(id);
        return ResponseEntity.ok().build();
    }
}
