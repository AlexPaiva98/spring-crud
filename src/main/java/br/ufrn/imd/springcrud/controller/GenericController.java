package br.ufrn.imd.springcrud.controller;

import br.ufrn.imd.springcrud.model.AbstractModel;
import br.ufrn.imd.springcrud.model.dto.AbstractDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

import br.ufrn.imd.springcrud.exception.EntityNotFoundException;
import br.ufrn.imd.springcrud.exception.ValidationException;
import br.ufrn.imd.springcrud.service.GenericService;

@CrossOrigin
public abstract class GenericController<Model extends AbstractModel, Dto extends AbstractDto> {
    protected abstract GenericService<Model, Dto> getService();

    @GetMapping
    public ResponseEntity<Collection<Dto>> findAll(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page) {
        return ResponseEntity.ok(this.getService().convertToDTOList(this.getService().findAll(limit, page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dto> findById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(this.getService().convertToDto(this.getService().findById(id)));
    }

    @PostMapping
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
    public ResponseEntity<Dto> delete(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(this.getService().convertToDto(this.getService().deleteById(id)));
    }
}
