package br.ufrn.imd.springcrud.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

import br.ufrn.imd.springcrud.exception.ValidationException;

@Service
public interface FilterService<Dto> {
    String getName();
    @Transactional
    void validate(Map<String, String> parameters) throws ValidationException;
    @Transactional
    Collection<Dto> filter(Map<String, String> parameters);
    default Collection<Dto> filterTemplate(Map<String, String> parameters) throws ValidationException {
        this.validate(parameters);
        return this.filter(parameters);
    }
}
