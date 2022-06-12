package nl.imvdm.hsv.contestapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
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

    @ManyToOne
    private Vereniging team;

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

    public Vereniging getTeam() {
        return this.team;
    }

    public void setTeam(Vereniging vereniging) {
        this.team = vereniging;
    }

    public Persoon team(Vereniging vereniging) {
        this.setTeam(vereniging);
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
