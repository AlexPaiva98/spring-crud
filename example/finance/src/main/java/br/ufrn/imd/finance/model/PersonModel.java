package br.ufrn.imd.finance.model;

import javax.persistence.Entity;

import br.ufrn.imd.springcrud.model.AbstractModel;

@Entity
public class PersonModel extends AbstractModel {
    private String name;

    public PersonModel() {
        this.setName("");
    }

    public PersonModel(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
