package nl.imvdm.hsv.contestapp.repository;

import nl.imvdm.hsv.contestapp.domain.Vereniging;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Vereniging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerenigingRepository extends JpaRepository<Vereniging, Long> {}
