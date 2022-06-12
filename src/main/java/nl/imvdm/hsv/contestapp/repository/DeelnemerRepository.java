package nl.imvdm.hsv.contestapp.repository;

import nl.imvdm.hsv.contestapp.domain.Deelnemer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Deelnemer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeelnemerRepository extends JpaRepository<Deelnemer, Long> {}
