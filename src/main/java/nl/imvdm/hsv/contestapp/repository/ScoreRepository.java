package nl.imvdm.hsv.contestapp.repository;

import java.util.List;
import java.util.Optional;
import nl.imvdm.hsv.contestapp.domain.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Score entity.
 */
@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    default Optional<Score> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Score> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Score> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct score from Score score left join fetch score.onderdeel left join fetch score.niveau left join fetch score.deelnemer",
        countQuery = "select count(distinct score) from Score score"
    )
    Page<Score> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct score from Score score left join fetch score.onderdeel left join fetch score.niveau left join fetch score.deelnemer"
    )
    List<Score> findAllWithToOneRelationships();

    @Query(
        "select score from Score score left join fetch score.onderdeel left join fetch score.niveau left join fetch score.deelnemer where score.id =:id"
    )
    Optional<Score> findOneWithToOneRelationships(@Param("id") Long id);
}
