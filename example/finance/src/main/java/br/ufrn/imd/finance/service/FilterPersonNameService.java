package br.ufrn.imd.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

import br.ufrn.imd.springcrud.exception.ValidationException;
import br.ufrn.imd.springcrud.helper.ExceptionHelper;
import br.ufrn.imd.springcrud.service.FilterService;

import br.ufrn.imd.finance.model.dto.PersonDto;

@Service
public class FilterPersonNameService implements FilterService<PersonDto> {
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public PersonService getPeopleService() {
        return this.personService;
    }

    @Override
    public String getName() {
        return "filter_by_name";
    }

    @Override
    public void validate(Map<String, String> parameters) throws ValidationException {
        ExceptionHelper exceptionHelper = new ExceptionHelper();
        /* Check parameter existence */
        if (!parameters.containsKey("name")) {
            exceptionHelper.add("there is no parameter name");
        } else {
            if (parameters.get("name").isBlank()) {
                exceptionHelper.add("invalid parameter(name) value");
            }
        }
        /* Check error */
        if (!exceptionHelper.isEmpty()) {
            throw new ValidationException(exceptionHelper.getMessage());
        }
    }

    @Override
    public Collection<PersonDto> filter(Map<String, String> parameters) {
        return this.getPeopleService().convertToDTOList(this.personService.findPeopleByName(parameters.get("name")));
    }
}
