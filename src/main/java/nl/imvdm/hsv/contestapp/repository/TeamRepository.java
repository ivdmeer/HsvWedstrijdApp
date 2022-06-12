package nl.imvdm.hsv.contestapp.repository;

import java.util.List;
import java.util.Optional;
import nl.imvdm.hsv.contestapp.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Team entity.
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    default Optional<Team> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Team> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Team> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct team from Team team left join fetch team.vereniging",
        countQuery = "select count(distinct team) from Team team"
    )
    Page<Team> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct team from Team team left join fetch team.vereniging")
    List<Team> findAllWithToOneRelationships();

    @Query("select team from Team team left join fetch team.vereniging where team.id =:id")
    Optional<Team> findOneWithToOneRelationships(@Param("id") Long id);
}
