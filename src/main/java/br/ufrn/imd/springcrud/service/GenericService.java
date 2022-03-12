package br.ufrn.imd.springcrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.ufrn.imd.springcrud.model.dto.AbstractDto;
import br.ufrn.imd.springcrud.exception.EntityNotFoundException;
import br.ufrn.imd.springcrud.exception.ValidationException;
import br.ufrn.imd.springcrud.repository.GenericRepository;
import br.ufrn.imd.springcrud.util.ValidationTypeUtil;

public abstract class GenericService<Model, Dto> {
    protected Type modelType;
    protected Type dtoType;
    protected String entityName;
    protected ModelMapper modelMapper;

    public GenericService() {
        this.modelType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.dtoType = (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
        this.entityName = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1].getTypeName();
        this.entityName = this.entityName.substring(this.entityName.lastIndexOf(".") + 1);
    }

    @Autowired
    protected void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected String getEntityName() {
        return this.entityName;
    }

    protected abstract GenericRepository<Model> getRepository();

    protected abstract void validateDto(ValidationTypeUtil validationTypeUtil, Dto dto) throws ValidationException;

    public Dto convertToDto(Model entity) {
        return this.modelMapper.map(entity, this.dtoType);
    }

    public Model convertToEntity(Dto dto) {
        return this.modelMapper.map(dto, this.modelType);
    }

    protected void validateId(Long id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("id cannot be null");
        } else if (id < 0) {
            throw new ValidationException("id cannot be negative");
        } else {
            try {
                this.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                throw new ValidationException(entityNotFoundException.getMessage());
            }
        }
    }

    protected void validateNonExistentOfIdInDto(Dto dto) throws ValidationException {
        if (((AbstractDto)dto).getId() != null) {
            throw new ValidationException("id cannot be sent");
        }
    }

    public Collection<Dto> convertToDTOList(Collection<Model> entities) {
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Collection<Model> convertToEntityList(Collection<Dto> dtos) {
        return dtos.stream().map(this::convertToEntity).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Model findById(Long id) throws EntityNotFoundException {
        Optional<Model> entity = this.getRepository().findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException(this.getEntityName(), id.toString());
        }
        return entity.get();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Model> findAll(Integer lim, Integer pg) {
        return this.getRepository().findAllByActiveIsTrueOrderByCreationDateDesc(PageRequest.of(pg, lim));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Model save(Dto dto) throws ValidationException {
        this.validateNonExistentOfIdInDto(dto);
        this.validateDto(ValidationTypeUtil.CREATE, dto);
        return this.getRepository().save(this.convertToEntity(dto));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Model update(Long id, Dto dto) throws ValidationException {
        this.validateId(id);
        this.validateDto(ValidationTypeUtil.UPDATE, dto);
        return this.getRepository().save(this.convertToEntity(dto));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Model updateAll(Long id, Dto dto) throws ValidationException {
        this.validateId(id);
        this.validateDto(ValidationTypeUtil.UPDATE_ALL, dto);
        return this.getRepository().save(this.convertToEntity(dto));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) throws EntityNotFoundException {
        Optional<Model> entity = getRepository().findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException(this.getEntityName(), id.toString());
        } else {
            this.getRepository().deleteById(id);
        }
    }
}
