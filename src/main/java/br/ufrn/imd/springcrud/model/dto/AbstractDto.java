package br.ufrn.imd.springcrud.model.dto;

import java.io.Serializable;

public class AbstractDto<PK extends Serializable> {
    private PK id;

    public AbstractDto() {
        this.setId(null);
    }

    public AbstractDto(PK id) {
        this.setId(id);
    }

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }
}
