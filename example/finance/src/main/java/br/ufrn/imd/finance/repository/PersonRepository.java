package br.ufrn.imd.finance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

import br.ufrn.imd.springcrud.repository.GenericRepository;

import br.ufrn.imd.finance.model.PersonModel;

@Repository
public interface PersonRepository extends GenericRepository<PersonModel> {
    @Query(value = "SELECT * FROM #{#entityName} WHERE name = :name AND active = true", nativeQuery = true)
    Set<PersonModel> findByName(String name);
}
