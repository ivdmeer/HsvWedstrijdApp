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
 * A Deelnemer.
 */
@Entity
@Table(name = "deelnemer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Deelnemer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nummer", nullable = false)
    private String nummer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "deelnemers" }, allowSetters = true)
    private Wedstrijd wedstrijd;

    @ManyToOne
    @JsonIgnoreProperties(value = { "vereniging", "deelnemers" }, allowSetters = true)
    private Persoon persoon;

    @ManyToOne
    @JsonIgnoreProperties(value = { "vereniging", "deelnemers" }, allowSetters = true)
    private Team team;

    @OneToMany(mappedBy = "deelnemer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "onderdeel", "niveau", "deelnemer" }, allowSetters = true)
    private Set<Score> scores = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Deelnemer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Deelnemer nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public Wedstrijd getWedstrijd() {
        return this.wedstrijd;
    }

    public void setWedstrijd(Wedstrijd wedstrijd) {
        this.wedstrijd = wedstrijd;
    }

    public Deelnemer wedstrijd(Wedstrijd wedstrijd) {
        this.setWedstrijd(wedstrijd);
        return this;
    }

    public Persoon getPersoon() {
        return this.persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

    public Deelnemer persoon(Persoon persoon) {
        this.setPersoon(persoon);
        return this;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Deelnemer team(Team team) {
        this.setTeam(team);
        return this;
    }

    public Set<Score> getScores() {
        return this.scores;
    }

    public void setScores(Set<Score> scores) {
        if (this.scores != null) {
            this.scores.forEach(i -> i.setDeelnemer(null));
        }
        if (scores != null) {
            scores.forEach(i -> i.setDeelnemer(this));
        }
        this.scores = scores;
    }

    public Deelnemer scores(Set<Score> scores) {
        this.setScores(scores);
        return this;
    }

    public Deelnemer addScore(Score score) {
        this.scores.add(score);
        score.setDeelnemer(this);
        return this;
    }

    public Deelnemer removeScore(Score score) {
        this.scores.remove(score);
        score.setDeelnemer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deelnemer)) {
            return false;
        }
        return id != null && id.equals(((Deelnemer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deelnemer{" +
            "id=" + getId() +
            ", nummer='" + getNummer() + "'" +
            "}";
    }
}
