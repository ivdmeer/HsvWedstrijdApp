package nl.imvdm.hsv.contestapp.repository;

import nl.imvdm.hsv.contestapp.domain.Onderdeel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Onderdeel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnderdeelRepository extends JpaRepository<Onderdeel, Long> {}
