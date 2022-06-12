package nl.imvdm.hsv.contestapp.repository;

import java.util.List;
import java.util.Optional;
import nl.imvdm.hsv.contestapp.domain.Persoon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Persoon entity.
 */
@Repository
public interface PersoonRepository extends JpaRepository<Persoon, Long> {
    default Optional<Persoon> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Persoon> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Persoon> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct persoon from Persoon persoon left join fetch persoon.team",
        countQuery = "select count(distinct persoon) from Persoon persoon"
    )
    Page<Persoon> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct persoon from Persoon persoon left join fetch persoon.team")
    List<Persoon> findAllWithToOneRelationships();

    @Query("select persoon from Persoon persoon left join fetch persoon.team where persoon.id =:id")
    Optional<Persoon> findOneWithToOneRelationships(@Param("id") Long id);
}
