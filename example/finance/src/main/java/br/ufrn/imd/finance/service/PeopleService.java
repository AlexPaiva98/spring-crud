package br.ufrn.imd.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    protected void validateDto(ValidationTypeUtil validationTypeUtil, PeopleDto dto) throws ValidationException {
        ExceptionHelper exceptionHelper = new ExceptionHelper();
        /** Check name */
        if (dto.getName().isEmpty()) {
            exceptionHelper.add("invalid name");
        }
        /** Check error */
        if (!exceptionHelper.isEmpty()) {
            throw new ValidationException(exceptionHelper.getMessage());
        }
    }
}
