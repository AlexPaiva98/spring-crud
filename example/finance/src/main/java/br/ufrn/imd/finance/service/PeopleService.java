package br.ufrn.imd.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.springcrud.exception.EntityNotFoundException;
import br.ufrn.imd.springcrud.exception.ValidationException;
import br.ufrn.imd.springcrud.helper.ExceptionHelper;
import br.ufrn.imd.springcrud.repository.GenericRepository;
import br.ufrn.imd.springcrud.service.GenericService;
import br.ufrn.imd.springcrud.util.ValidationTypeUtil;

import br.ufrn.imd.finance.model.PeopleModel;
import br.ufrn.imd.finance.model.dto.PeopleDto;
import br.ufrn.imd.finance.repository.PeopleRepository;

@Service
public class PeopleService extends GenericService<PeopleModel, PeopleDto> {
    private PeopleRepository peopleRepository;

    @Autowired
    public void setPeopleRepository(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    protected GenericRepository<PeopleModel> getRepository() {
        return this.peopleRepository;
    }

    @Override
    protected void validate(ValidationTypeUtil validationType, PeopleDto dto) throws ValidationException {
        ExceptionHelper exceptionHelper = new ExceptionHelper();
        if (validationType == ValidationTypeUtil.EXISTING) {
            /** Check id */
            if (dto.getId() == null) {
                exceptionHelper.add("id cannot be null");
            } else if (dto.getId() < 0) {
                exceptionHelper.add("id cannot be negative");
            } else {
                try {
                    this.findById(dto.getId());
                } catch (EntityNotFoundException entityNotFoundException) {
                    exceptionHelper.add(entityNotFoundException.getMessage());
                }
            }
        }
        /** Check name */
        if (dto.getName().isEmpty()) {
            exceptionHelper.add("invalid name");
        }
        /** Check error */
        if (exceptionHelper.isEmpty()) {
            throw new ValidationException((exceptionHelper.getMessage()));
        }
    }
}
