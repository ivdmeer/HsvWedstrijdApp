package nl.imvdm.hsv.contestapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(mappedBy = "score")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "score" }, allowSetters = true)
    private Set<Onderdeel> onderdeels = new HashSet<>();

    @OneToMany(mappedBy = "score")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "score" }, allowSetters = true)
    private Set<Niveau> niveaus = new HashSet<>();

    @OneToMany(mappedBy = "score")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "wedstrijds", "persoons", "teams", "score" }, allowSetters = true)
    private Set<Deelnemer> deelnemers = new HashSet<>();

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

    public Set<Onderdeel> getOnderdeels() {
        return this.onderdeels;
    }

    public void setOnderdeels(Set<Onderdeel> onderdeels) {
        if (this.onderdeels != null) {
            this.onderdeels.forEach(i -> i.setScore(null));
        }
        if (onderdeels != null) {
            onderdeels.forEach(i -> i.setScore(this));
        }
        this.onderdeels = onderdeels;
    }

    public Score onderdeels(Set<Onderdeel> onderdeels) {
        this.setOnderdeels(onderdeels);
        return this;
    }

    public Score addOnderdeel(Onderdeel onderdeel) {
        this.onderdeels.add(onderdeel);
        onderdeel.setScore(this);
        return this;
    }

    public Score removeOnderdeel(Onderdeel onderdeel) {
        this.onderdeels.remove(onderdeel);
        onderdeel.setScore(null);
        return this;
    }

    public Set<Niveau> getNiveaus() {
        return this.niveaus;
    }

    public void setNiveaus(Set<Niveau> niveaus) {
        if (this.niveaus != null) {
            this.niveaus.forEach(i -> i.setScore(null));
        }
        if (niveaus != null) {
            niveaus.forEach(i -> i.setScore(this));
        }
        this.niveaus = niveaus;
    }

    public Score niveaus(Set<Niveau> niveaus) {
        this.setNiveaus(niveaus);
        return this;
    }

    public Score addNiveau(Niveau niveau) {
        this.niveaus.add(niveau);
        niveau.setScore(this);
        return this;
    }

    public Score removeNiveau(Niveau niveau) {
        this.niveaus.remove(niveau);
        niveau.setScore(null);
        return this;
    }

    public Set<Deelnemer> getDeelnemers() {
        return this.deelnemers;
    }

    public void setDeelnemers(Set<Deelnemer> deelnemers) {
        if (this.deelnemers != null) {
            this.deelnemers.forEach(i -> i.setScore(null));
        }
        if (deelnemers != null) {
            deelnemers.forEach(i -> i.setScore(this));
        }
        this.deelnemers = deelnemers;
    }

    public Score deelnemers(Set<Deelnemer> deelnemers) {
        this.setDeelnemers(deelnemers);
        return this;
    }

    public Score addDeelnemer(Deelnemer deelnemer) {
        this.deelnemers.add(deelnemer);
        deelnemer.setScore(this);
        return this;
    }

    public Score removeDeelnemer(Deelnemer deelnemer) {
        this.deelnemers.remove(deelnemer);
        deelnemer.setScore(null);
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
