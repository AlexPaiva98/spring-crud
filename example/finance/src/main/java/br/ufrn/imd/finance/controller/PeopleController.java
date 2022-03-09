package br.ufrn.imd.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.springcrud.controller.GenericController;
import br.ufrn.imd.springcrud.service.GenericService;

import br.ufrn.imd.finance.model.PeopleModel;
import br.ufrn.imd.finance.model.dto.PeopleDto;
import br.ufrn.imd.finance.service.PeopleService;

@RestController
@RequestMapping("/peoples")
public class PeopleController extends GenericController<PeopleModel, PeopleDto> {
    private PeopleService peopleService;

    @Autowired
    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    protected GenericService getService() {
        return this.peopleService;
    }
}
