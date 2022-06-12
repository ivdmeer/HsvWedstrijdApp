package nl.imvdm.hsv.contestapp.repository;

import nl.imvdm.hsv.contestapp.domain.Persoon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Persoon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersoonRepository extends JpaRepository<Persoon, Long> {}
