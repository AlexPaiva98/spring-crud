package br.ufrn.imd.springcrud.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.ufrn.imd.springcrud.exception.EntityNotFoundException;
import br.ufrn.imd.springcrud.exception.ValidationException;
import br.ufrn.imd.springcrud.model.AbstractModel;
import br.ufrn.imd.springcrud.model.dto.AbstractDto;
import br.ufrn.imd.springcrud.repository.GenericRepository;
import br.ufrn.imd.springcrud.util.ValidationType;

@Service
public abstract class GenericService<PK extends Serializable, Model extends AbstractModel<PK>, Dto extends AbstractDto<PK>> {
    protected String entityName;

    public GenericService() {
        this.entityName = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1].getTypeName();
        this.entityName = this.entityName.substring(this.entityName.lastIndexOf(".") + 1);
    }

    public String getEntityName() {
        return this.entityName;
    }

    public abstract Dto convertToDto(Model entity);

    public abstract Model convertToEntity(Dto dto);

    public abstract Dto validate(ValidationType validationType, Dto dto) throws ValidationException;

    protected abstract GenericRepository<PK, Model> getRepository();

    public Collection<Dto> convertToDTOList(Collection<Model> entities) {
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Collection<Model> convertToEntityList(Collection<Dto> dtos) {
        return dtos.stream().map(this::convertToEntity).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Model findById(PK id) throws EntityNotFoundException {
        Optional<Model> entity = this.getRepository().findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException(this.getEntityName(), id.toString());
        }
        return entity.get();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Model> findAll(Integer lim, Integer pg) {
        return this.getRepository().findAllByActiveIsTrueOrderByCreationDateCresc(PageRequest.of(pg, lim));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Model save(Dto dto) throws ValidationException {
        return this.getRepository().save(convertToEntity(validate(ValidationType.NEW, dto)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Model update(PK id, Dto dto) throws ValidationException {
        Dto newDTO = dto;
        newDTO.setId(id);
        return this.getRepository().save(convertToEntity(validate(ValidationType.EXISTING, newDTO)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(PK id) throws EntityNotFoundException {
        Optional<Model> entity = getRepository().findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException(this.getEntityName(), id.toString());
        } else {
            this.getRepository().deleteById(id);
        }
    }
}

