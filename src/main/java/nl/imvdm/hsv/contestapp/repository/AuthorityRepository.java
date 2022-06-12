package nl.imvdm.hsv.contestapp.repository;

import nl.imvdm.hsv.contestapp.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
