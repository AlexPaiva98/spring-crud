package br.ufrn.imd.springcrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.ufrn.imd.springcrud.exception.ClientException;
import br.ufrn.imd.springcrud.exception.EntityNotFoundException;
import br.ufrn.imd.springcrud.exception.ValidationException;
import br.ufrn.imd.springcrud.helper.TypeClass;
import br.ufrn.imd.springcrud.model.AbstractModel;
import br.ufrn.imd.springcrud.model.dto.AbstractDto;
import br.ufrn.imd.springcrud.repository.GenericRepository;
import br.ufrn.imd.springcrud.util.ValidationType;

@Service
public abstract class GenericService<Model extends AbstractModel, Dto extends AbstractDto> {
    protected String entityName;
    protected ModelMapper modelMapper;

    public GenericService() {
        this.entityName = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1].getTypeName();
        this.entityName = this.entityName.substring(this.entityName.lastIndexOf(".") + 1);
    }

    public String getEntityName() {
        return this.entityName;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected abstract GenericRepository<Model> getRepository();

    public Dto convertToDto(Model entity) {
        TypeClass<Dto> instance = new TypeClass<Dto>() {};
        Dto dto = null;
        try {
            dto = modelMapper.map(entity, instance.getGenericClass());
        } catch (ClassNotFoundException classNotFoundException) {
            throw new ClientException("non-existent dto class");
        }
        return dto;
    }

    public Model convertToEntity(Dto dto) {
        TypeClass<Model> instance = new TypeClass<Model>() {};
        Model entity = null;
        try {
            entity = modelMapper.map(dto, instance.getGenericClass());
        } catch (ClassNotFoundException classNotFoundException) {
            throw new ClientException("non-existent model class");
        }
        return entity;
    }

    protected abstract Dto validate(ValidationType validationType, Dto dto) throws ValidationException;

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
        return this.getRepository().findAllByActiveIsTrueOrderByCreationDateCresc(PageRequest.of(pg, lim));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Model save(Dto dto) throws ValidationException {
        return this.getRepository().save(convertToEntity(validate(ValidationType.NEW, dto)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Model update(Long id, Dto dto) throws ValidationException {
        Dto newDTO = dto;
        newDTO.setId(id);
        return this.getRepository().save(convertToEntity(validate(ValidationType.EXISTING, newDTO)));
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

