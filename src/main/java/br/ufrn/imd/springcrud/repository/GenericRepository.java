package br.ufrn.imd.springcrud.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import br.ufrn.imd.springcrud.model.AbstractModel;

@NoRepositoryBean
public interface GenericRepository<PK extends Serializable, Model extends AbstractModel<PK>>
        extends JpaRepository<Model, PK> {

    @Override
    @Query(value = "SELECT * FROM #{#entityName} WHERE id = :id AND active = true", nativeQuery = true)
    Optional<Model> findById(PK id);

    @Override
    @Modifying
    @Query(value = "UPDATE #{#entityName} SET active=false WHERE id = :id", nativeQuery = true)
    void deleteById(PK id);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    List<Model> findAllByActiveIsTrueOrderByCreationDateCresc(Pageable pageable);
}
