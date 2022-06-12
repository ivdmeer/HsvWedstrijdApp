package nl.imvdm.hsv.contestapp.repository;

import nl.imvdm.hsv.contestapp.domain.Wedstrijd;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Wedstrijd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WedstrijdRepository extends JpaRepository<Wedstrijd, Long> {}
