package nl.imvdm.hsv.contestapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import nl.imvdm.hsv.contestapp.domain.Wedstrijd;
import nl.imvdm.hsv.contestapp.repository.WedstrijdRepository;
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
 * REST controller for managing {@link nl.imvdm.hsv.contestapp.domain.Wedstrijd}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WedstrijdResource {

    private final Logger log = LoggerFactory.getLogger(WedstrijdResource.class);

    private static final String ENTITY_NAME = "wedstrijd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WedstrijdRepository wedstrijdRepository;

    public WedstrijdResource(WedstrijdRepository wedstrijdRepository) {
        this.wedstrijdRepository = wedstrijdRepository;
    }

    /**
     * {@code POST  /wedstrijds} : Create a new wedstrijd.
     *
     * @param wedstrijd the wedstrijd to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wedstrijd, or with status {@code 400 (Bad Request)} if the wedstrijd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wedstrijds")
    public ResponseEntity<Wedstrijd> createWedstrijd(@Valid @RequestBody Wedstrijd wedstrijd) throws URISyntaxException {
        log.debug("REST request to save Wedstrijd : {}", wedstrijd);
        if (wedstrijd.getId() != null) {
            throw new BadRequestAlertException("A new wedstrijd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Wedstrijd result = wedstrijdRepository.save(wedstrijd);
        return ResponseEntity
            .created(new URI("/api/wedstrijds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wedstrijds/:id} : Updates an existing wedstrijd.
     *
     * @param id the id of the wedstrijd to save.
     * @param wedstrijd the wedstrijd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wedstrijd,
     * or with status {@code 400 (Bad Request)} if the wedstrijd is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wedstrijd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wedstrijds/{id}")
    public ResponseEntity<Wedstrijd> updateWedstrijd(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Wedstrijd wedstrijd
    ) throws URISyntaxException {
        log.debug("REST request to update Wedstrijd : {}, {}", id, wedstrijd);
        if (wedstrijd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wedstrijd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wedstrijdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Wedstrijd result = wedstrijdRepository.save(wedstrijd);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wedstrijd.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /wedstrijds/:id} : Partial updates given fields of an existing wedstrijd, field will ignore if it is null
     *
     * @param id the id of the wedstrijd to save.
     * @param wedstrijd the wedstrijd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wedstrijd,
     * or with status {@code 400 (Bad Request)} if the wedstrijd is not valid,
     * or with status {@code 404 (Not Found)} if the wedstrijd is not found,
     * or with status {@code 500 (Internal Server Error)} if the wedstrijd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/wedstrijds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Wedstrijd> partialUpdateWedstrijd(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Wedstrijd wedstrijd
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wedstrijd partially : {}, {}", id, wedstrijd);
        if (wedstrijd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wedstrijd.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wedstrijdRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Wedstrijd> result = wedstrijdRepository
            .findById(wedstrijd.getId())
            .map(existingWedstrijd -> {
                if (wedstrijd.getNaam() != null) {
                    existingWedstrijd.setNaam(wedstrijd.getNaam());
                }
                if (wedstrijd.getOmschrijving() != null) {
                    existingWedstrijd.setOmschrijving(wedstrijd.getOmschrijving());
                }
                if (wedstrijd.getDatum() != null) {
                    existingWedstrijd.setDatum(wedstrijd.getDatum());
                }

                return existingWedstrijd;
            })
            .map(wedstrijdRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wedstrijd.getId().toString())
        );
    }

    /**
     * {@code GET  /wedstrijds} : get all the wedstrijds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wedstrijds in body.
     */
    @GetMapping("/wedstrijds")
    public List<Wedstrijd> getAllWedstrijds() {
        log.debug("REST request to get all Wedstrijds");
        return wedstrijdRepository.findAll();
    }

    /**
     * {@code GET  /wedstrijds/:id} : get the "id" wedstrijd.
     *
     * @param id the id of the wedstrijd to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wedstrijd, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wedstrijds/{id}")
    public ResponseEntity<Wedstrijd> getWedstrijd(@PathVariable Long id) {
        log.debug("REST request to get Wedstrijd : {}", id);
        Optional<Wedstrijd> wedstrijd = wedstrijdRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wedstrijd);
    }

    /**
     * {@code DELETE  /wedstrijds/:id} : delete the "id" wedstrijd.
     *
     * @param id the id of the wedstrijd to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wedstrijds/{id}")
    public ResponseEntity<Void> deleteWedstrijd(@PathVariable Long id) {
        log.debug("REST request to delete Wedstrijd : {}", id);
        wedstrijdRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
