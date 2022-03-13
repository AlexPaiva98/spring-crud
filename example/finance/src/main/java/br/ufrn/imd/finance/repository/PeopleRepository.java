package br.ufrn.imd.finance.repository;

import org.springframework.stereotype.Repository;

import br.ufrn.imd.springcrud.repository.GenericRepository;

import br.ufrn.imd.finance.model.PeopleModel;

import java.util.Optional;

@Repository
public interface PeopleRepository extends GenericRepository<PeopleModel> {

	Optional<PeopleModel> findByActiveIsFalseAndName(String name);
}
