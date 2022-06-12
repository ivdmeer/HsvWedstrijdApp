package nl.imvdm.hsv.contestapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import nl.imvdm.hsv.contestapp.domain.Persoon;
import nl.imvdm.hsv.contestapp.repository.PersoonRepository;
import nl.imvdm.hsv.contestapp.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link nl.imvdm.hsv.contestapp.domain.Persoon}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PersoonResource {

    private final Logger log = LoggerFactory.getLogger(PersoonResource.class);

    private static final String ENTITY_NAME = "persoon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersoonRepository persoonRepository;

    public PersoonResource(PersoonRepository persoonRepository) {
        this.persoonRepository = persoonRepository;
    }

    /**
     * {@code POST  /persoons} : Create a new persoon.
     *
     * @param persoon the persoon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new persoon, or with status {@code 400 (Bad Request)} if the persoon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/persoons")
    public ResponseEntity<Persoon> createPersoon(@Valid @RequestBody Persoon persoon) throws URISyntaxException {
        log.debug("REST request to save Persoon : {}", persoon);
        if (persoon.getId() != null) {
            throw new BadRequestAlertException("A new persoon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Persoon result = persoonRepository.save(persoon);
        return ResponseEntity
            .created(new URI("/api/persoons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /persoons/:id} : Updates an existing persoon.
     *
     * @param id the id of the persoon to save.
     * @param persoon the persoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated persoon,
     * or with status {@code 400 (Bad Request)} if the persoon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the persoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/persoons/{id}")
    public ResponseEntity<Persoon> updatePersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Persoon persoon
    ) throws URISyntaxException {
        log.debug("REST request to update Persoon : {}, {}", id, persoon);
        if (persoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, persoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!persoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Persoon result = persoonRepository.save(persoon);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, persoon.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /persoons/:id} : Partial updates given fields of an existing persoon, field will ignore if it is null
     *
     * @param id the id of the persoon to save.
     * @param persoon the persoon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated persoon,
     * or with status {@code 400 (Bad Request)} if the persoon is not valid,
     * or with status {@code 404 (Not Found)} if the persoon is not found,
     * or with status {@code 500 (Internal Server Error)} if the persoon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/persoons/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Persoon> partialUpdatePersoon(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Persoon persoon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Persoon partially : {}, {}", id, persoon);
        if (persoon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, persoon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!persoonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Persoon> result = persoonRepository
            .findById(persoon.getId())
            .map(existingPersoon -> {
                if (persoon.getNaam() != null) {
                    existingPersoon.setNaam(persoon.getNaam());
                }
                if (persoon.getGeboorteDatum() != null) {
                    existingPersoon.setGeboorteDatum(persoon.getGeboorteDatum());
                }

                return existingPersoon;
            })
            .map(persoonRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, persoon.getId().toString())
        );
    }

    /**
     * {@code GET  /persoons} : get all the persoons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of persoons in body.
     */
    @GetMapping("/persoons")
    public List<Persoon> getAllPersoons() {
        log.debug("REST request to get all Persoons");
        return persoonRepository.findAll();
    }

    /**
     * {@code GET  /persoons/:id} : get the "id" persoon.
     *
     * @param id the id of the persoon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the persoon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/persoons/{id}")
    public ResponseEntity<Persoon> getPersoon(@PathVariable Long id) {
        log.debug("REST request to get Persoon : {}", id);
        Optional<Persoon> persoon = persoonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(persoon);
    }

    /**
     * {@code DELETE  /persoons/:id} : delete the "id" persoon.
     *
     * @param id the id of the persoon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/persoons/{id}")
    public ResponseEntity<Void> deletePersoon(@PathVariable Long id) {
        log.debug("REST request to delete Persoon : {}", id);
        persoonRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
