package br.ufrn.imd.springcrud.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import br.ufrn.imd.springcrud.model.AbstractModel;

@NoRepositoryBean
public interface GenericRepository<Model extends AbstractModel> extends JpaRepository<Model, Long> {
    @Override
    @Query(value = "SELECT * FROM #{#entityName} WHERE id = :id AND active = true", nativeQuery = true)
    Optional<Model> findById(Long id);

    @Override
    @Modifying
    @Query(value = "UPDATE #{#entityName} SET active=false WHERE id = :id", nativeQuery = true)
    void deleteById(Long id);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    List<Model> findAllByActiveIsTrueOrderByCreationDateDesc(Pageable pageable);
}
