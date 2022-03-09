package br.ufrn.imd.finance.model.dto;

import br.ufrn.imd.springcrud.model.dto.AbstractDto;

public class PeopleDto extends AbstractDto {
    private String name;

    public PeopleDto() {
        this.setName("");
    }

    public PeopleDto(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
