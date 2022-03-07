package br.ufrn.imd.springcrud.model.dto;

public class AbstractDto {
    private Long id;

    public AbstractDto() {
        this.setId(null);
    }

    public AbstractDto(Long id) {
        this.setId(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
