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
 * A Persoon.
 */
@Entity
@Table(name = "persoon")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Persoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "naam", nullable = false)
    private String naam;

    @NotNull
    @Column(name = "geboorte_datum", nullable = false)
    private LocalDate geboorteDatum;

    @OneToMany(mappedBy = "persoon")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "persoon", "team" }, allowSetters = true)
    private Set<Vereniging> verenigings = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "wedstrijds", "persoons", "teams", "score" }, allowSetters = true)
    private Deelnemer deelnemer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Persoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Persoon naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public LocalDate getGeboorteDatum() {
        return this.geboorteDatum;
    }

    public Persoon geboorteDatum(LocalDate geboorteDatum) {
        this.setGeboorteDatum(geboorteDatum);
        return this;
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public Set<Vereniging> getVerenigings() {
        return this.verenigings;
    }

    public void setVerenigings(Set<Vereniging> verenigings) {
        if (this.verenigings != null) {
            this.verenigings.forEach(i -> i.setPersoon(null));
        }
        if (verenigings != null) {
            verenigings.forEach(i -> i.setPersoon(this));
        }
        this.verenigings = verenigings;
    }

    public Persoon verenigings(Set<Vereniging> verenigings) {
        this.setVerenigings(verenigings);
        return this;
    }

    public Persoon addVereniging(Vereniging vereniging) {
        this.verenigings.add(vereniging);
        vereniging.setPersoon(this);
        return this;
    }

    public Persoon removeVereniging(Vereniging vereniging) {
        this.verenigings.remove(vereniging);
        vereniging.setPersoon(null);
        return this;
    }

    public Deelnemer getDeelnemer() {
        return this.deelnemer;
    }

    public void setDeelnemer(Deelnemer deelnemer) {
        this.deelnemer = deelnemer;
    }

    public Persoon deelnemer(Deelnemer deelnemer) {
        this.setDeelnemer(deelnemer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Persoon)) {
            return false;
        }
        return id != null && id.equals(((Persoon) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Persoon{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", geboorteDatum='" + getGeboorteDatum() + "'" +
            "}";
    }
}
