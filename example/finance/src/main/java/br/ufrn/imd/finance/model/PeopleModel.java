package br.ufrn.imd.finance.model;

import javax.persistence.Entity;

import br.ufrn.imd.springcrud.model.AbstractModel;

@Entity
public class PeopleModel extends AbstractModel {
    private String name;

    public PeopleModel() {
        this.setName("");
    }

    public PeopleModel(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
