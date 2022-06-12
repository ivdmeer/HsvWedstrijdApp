package nl.imvdm.hsv.contestapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Score.
 */
@Entity
@Table(name = "score")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "punten", nullable = false)
    private Double punten;

    @ManyToOne
    @JsonIgnoreProperties(value = { "scores" }, allowSetters = true)
    private Onderdeel onderdeel;

    @ManyToOne
    @JsonIgnoreProperties(value = { "scores" }, allowSetters = true)
    private Niveau niveau;

    @ManyToOne
    @JsonIgnoreProperties(value = { "wedstrijd", "persoon", "team", "scores" }, allowSetters = true)
    private Deelnemer deelnemer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Score id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPunten() {
        return this.punten;
    }

    public Score punten(Double punten) {
        this.setPunten(punten);
        return this;
    }

    public void setPunten(Double punten) {
        this.punten = punten;
    }

    public Onderdeel getOnderdeel() {
        return this.onderdeel;
    }

    public void setOnderdeel(Onderdeel onderdeel) {
        this.onderdeel = onderdeel;
    }

    public Score onderdeel(Onderdeel onderdeel) {
        this.setOnderdeel(onderdeel);
        return this;
    }

    public Niveau getNiveau() {
        return this.niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Score niveau(Niveau niveau) {
        this.setNiveau(niveau);
        return this;
    }

    public Deelnemer getDeelnemer() {
        return this.deelnemer;
    }

    public void setDeelnemer(Deelnemer deelnemer) {
        this.deelnemer = deelnemer;
    }

    public Score deelnemer(Deelnemer deelnemer) {
        this.setDeelnemer(deelnemer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Score)) {
            return false;
        }
        return id != null && id.equals(((Score) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Score{" +
            "id=" + getId() +
            ", punten=" + getPunten() +
            "}";
    }
}
