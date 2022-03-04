package br.ufrn.imd.springcrud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Collection;

import br.ufrn.imd.springcrud.exception.EntityNotFoundException;
import br.ufrn.imd.springcrud.exception.ValidationException;
import br.ufrn.imd.springcrud.model.AbstractModel;
import br.ufrn.imd.springcrud.model.dto.AbstractDto;
import br.ufrn.imd.springcrud.service.GenericService;

public abstract class GenericController<PK extends Serializable, Model extends AbstractModel<PK>, Dto extends AbstractDto<PK>> {
    protected abstract GenericService<PK, Model, Dto> getService();

    @GetMapping
    public ResponseEntity<Collection<Dto>> findAll(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page) {
        return ResponseEntity.ok(this.getService().convertToDTOList(this.getService().findAll(limit, page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dto> findById(@PathVariable PK id) throws EntityNotFoundException {
        return ResponseEntity.ok(this.getService().convertToDto(this.getService().findById(id)));
    }

    @PostMapping
    public ResponseEntity<Dto> save(@RequestBody Dto dto) throws ValidationException {
        return ResponseEntity.ok(this.getService().convertToDto(this.getService().save(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dto> update(@PathVariable PK id, @RequestBody Dto dto) throws ValidationException {
        return ResponseEntity.ok(this.getService().convertToDto(this.getService().update(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable PK id) throws EntityNotFoundException {
        getService().deleteById(id);
        return ResponseEntity.ok().build();
    }
}
