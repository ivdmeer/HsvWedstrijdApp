package nl.imvdm.hsv.contestapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Wedstrijd.
 */
@Entity
@Table(name = "wedstrijd")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Wedstrijd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "naam", nullable = false)
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @NotNull
    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @OneToMany(mappedBy = "wedstrijd")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "wedstrijd", "persoon", "team", "scores" }, allowSetters = true)
    private Set<Deelnemer> deelnemers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wedstrijd id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Wedstrijd naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Wedstrijd omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Wedstrijd datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Set<Deelnemer> getDeelnemers() {
        return this.deelnemers;
    }

    public void setDeelnemers(Set<Deelnemer> deelnemers) {
        if (this.deelnemers != null) {
            this.deelnemers.forEach(i -> i.setWedstrijd(null));
        }
        if (deelnemers != null) {
            deelnemers.forEach(i -> i.setWedstrijd(this));
        }
        this.deelnemers = deelnemers;
    }

    public Wedstrijd deelnemers(Set<Deelnemer> deelnemers) {
        this.setDeelnemers(deelnemers);
        return this;
    }

    public Wedstrijd addDeelnemer(Deelnemer deelnemer) {
        this.deelnemers.add(deelnemer);
        deelnemer.setWedstrijd(this);
        return this;
    }

    public Wedstrijd removeDeelnemer(Deelnemer deelnemer) {
        this.deelnemers.remove(deelnemer);
        deelnemer.setWedstrijd(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wedstrijd)) {
            return false;
        }
        return id != null && id.equals(((Wedstrijd) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wedstrijd{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}
