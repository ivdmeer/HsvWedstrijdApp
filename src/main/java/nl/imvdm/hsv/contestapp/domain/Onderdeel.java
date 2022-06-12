package nl.imvdm.hsv.contestapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Onderdeel.
 */
@Entity
@Table(name = "onderdeel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Onderdeel implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "onderdeels", "niveaus", "deelnemers" }, allowSetters = true)
    private Score score;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Onderdeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Onderdeel naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Onderdeel omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Score getScore() {
        return this.score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Onderdeel score(Score score) {
        this.setScore(score);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Onderdeel)) {
            return false;
        }
        return id != null && id.equals(((Onderdeel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Onderdeel{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
