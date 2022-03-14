package br.ufrn.imd.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.springcrud.controller.GenericController;
import br.ufrn.imd.springcrud.service.GenericService;

import br.ufrn.imd.finance.model.PersonModel;
import br.ufrn.imd.finance.model.dto.PersonDto;
import br.ufrn.imd.finance.service.PersonService;

@RestController
@RequestMapping("/people")
public class PersonController extends GenericController<PersonModel, PersonDto> {
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    protected GenericService<PersonModel, PersonDto> getService() {
        return this.personService;
    }
}
