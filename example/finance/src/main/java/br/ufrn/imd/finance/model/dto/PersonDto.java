package br.ufrn.imd.finance.model.dto;

import br.ufrn.imd.springcrud.model.dto.AbstractDto;

public class PersonDto extends AbstractDto {
    private String name;

    public PersonDto() {
        this.setName("");
    }

    public PersonDto(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
