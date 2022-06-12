package nl.imvdm.hsv.contestapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import nl.imvdm.hsv.contestapp.domain.Onderdeel;
import nl.imvdm.hsv.contestapp.repository.OnderdeelRepository;
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
 * REST controller for managing {@link nl.imvdm.hsv.contestapp.domain.Onderdeel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OnderdeelResource {

    private final Logger log = LoggerFactory.getLogger(OnderdeelResource.class);

    private static final String ENTITY_NAME = "onderdeel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnderdeelRepository onderdeelRepository;

    public OnderdeelResource(OnderdeelRepository onderdeelRepository) {
        this.onderdeelRepository = onderdeelRepository;
    }

    /**
     * {@code POST  /onderdeels} : Create a new onderdeel.
     *
     * @param onderdeel the onderdeel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onderdeel, or with status {@code 400 (Bad Request)} if the onderdeel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/onderdeels")
    public ResponseEntity<Onderdeel> createOnderdeel(@Valid @RequestBody Onderdeel onderdeel) throws URISyntaxException {
        log.debug("REST request to save Onderdeel : {}", onderdeel);
        if (onderdeel.getId() != null) {
            throw new BadRequestAlertException("A new onderdeel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Onderdeel result = onderdeelRepository.save(onderdeel);
        return ResponseEntity
            .created(new URI("/api/onderdeels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /onderdeels/:id} : Updates an existing onderdeel.
     *
     * @param id the id of the onderdeel to save.
     * @param onderdeel the onderdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onderdeel,
     * or with status {@code 400 (Bad Request)} if the onderdeel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the onderdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/onderdeels/{id}")
    public ResponseEntity<Onderdeel> updateOnderdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Onderdeel onderdeel
    ) throws URISyntaxException {
        log.debug("REST request to update Onderdeel : {}, {}", id, onderdeel);
        if (onderdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onderdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onderdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Onderdeel result = onderdeelRepository.save(onderdeel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, onderdeel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /onderdeels/:id} : Partial updates given fields of an existing onderdeel, field will ignore if it is null
     *
     * @param id the id of the onderdeel to save.
     * @param onderdeel the onderdeel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onderdeel,
     * or with status {@code 400 (Bad Request)} if the onderdeel is not valid,
     * or with status {@code 404 (Not Found)} if the onderdeel is not found,
     * or with status {@code 500 (Internal Server Error)} if the onderdeel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/onderdeels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Onderdeel> partialUpdateOnderdeel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Onderdeel onderdeel
    ) throws URISyntaxException {
        log.debug("REST request to partial update Onderdeel partially : {}, {}", id, onderdeel);
        if (onderdeel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, onderdeel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!onderdeelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Onderdeel> result = onderdeelRepository
            .findById(onderdeel.getId())
            .map(existingOnderdeel -> {
                if (onderdeel.getNaam() != null) {
                    existingOnderdeel.setNaam(onderdeel.getNaam());
                }
                if (onderdeel.getOmschrijving() != null) {
                    existingOnderdeel.setOmschrijving(onderdeel.getOmschrijving());
                }

                return existingOnderdeel;
            })
            .map(onderdeelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, onderdeel.getId().toString())
        );
    }

    /**
     * {@code GET  /onderdeels} : get all the onderdeels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onderdeels in body.
     */
    @GetMapping("/onderdeels")
    public List<Onderdeel> getAllOnderdeels() {
        log.debug("REST request to get all Onderdeels");
        return onderdeelRepository.findAll();
    }

    /**
     * {@code GET  /onderdeels/:id} : get the "id" onderdeel.
     *
     * @param id the id of the onderdeel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onderdeel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/onderdeels/{id}")
    public ResponseEntity<Onderdeel> getOnderdeel(@PathVariable Long id) {
        log.debug("REST request to get Onderdeel : {}", id);
        Optional<Onderdeel> onderdeel = onderdeelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(onderdeel);
    }

    /**
     * {@code DELETE  /onderdeels/:id} : delete the "id" onderdeel.
     *
     * @param id the id of the onderdeel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/onderdeels/{id}")
    public ResponseEntity<Void> deleteOnderdeel(@PathVariable Long id) {
        log.debug("REST request to delete Onderdeel : {}", id);
        onderdeelRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
