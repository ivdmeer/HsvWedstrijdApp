package nl.imvdm.hsv.contestapp.repository;

import java.util.List;
import java.util.Optional;
import nl.imvdm.hsv.contestapp.domain.Deelnemer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Deelnemer entity.
 */
@Repository
public interface DeelnemerRepository extends JpaRepository<Deelnemer, Long> {
    default Optional<Deelnemer> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Deelnemer> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Deelnemer> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct deelnemer from Deelnemer deelnemer left join fetch deelnemer.wedstrijd left join fetch deelnemer.persoon left join fetch deelnemer.team",
        countQuery = "select count(distinct deelnemer) from Deelnemer deelnemer"
    )
    Page<Deelnemer> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct deelnemer from Deelnemer deelnemer left join fetch deelnemer.wedstrijd left join fetch deelnemer.persoon left join fetch deelnemer.team"
    )
    List<Deelnemer> findAllWithToOneRelationships();

    @Query(
        "select deelnemer from Deelnemer deelnemer left join fetch deelnemer.wedstrijd left join fetch deelnemer.persoon left join fetch deelnemer.team where deelnemer.id =:id"
    )
    Optional<Deelnemer> findOneWithToOneRelationships(@Param("id") Long id);
}
