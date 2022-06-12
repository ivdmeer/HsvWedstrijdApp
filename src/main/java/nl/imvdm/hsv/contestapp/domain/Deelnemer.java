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

    @OneToMany(mappedBy = "deelnemer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "deelnemer" }, allowSetters = true)
    private Set<Wedstrijd> wedstrijds = new HashSet<>();

    @OneToMany(mappedBy = "deelnemer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "verenigings", "deelnemer" }, allowSetters = true)
    private Set<Persoon> persoons = new HashSet<>();

    @OneToMany(mappedBy = "deelnemer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "verenigings", "deelnemer" }, allowSetters = true)
    private Set<Team> teams = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "onderdeels", "niveaus", "deelnemers" }, allowSetters = true)
    private Score score;

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

    public Set<Wedstrijd> getWedstrijds() {
        return this.wedstrijds;
    }

    public void setWedstrijds(Set<Wedstrijd> wedstrijds) {
        if (this.wedstrijds != null) {
            this.wedstrijds.forEach(i -> i.setDeelnemer(null));
        }
        if (wedstrijds != null) {
            wedstrijds.forEach(i -> i.setDeelnemer(this));
        }
        this.wedstrijds = wedstrijds;
    }

    public Deelnemer wedstrijds(Set<Wedstrijd> wedstrijds) {
        this.setWedstrijds(wedstrijds);
        return this;
    }

    public Deelnemer addWedstrijd(Wedstrijd wedstrijd) {
        this.wedstrijds.add(wedstrijd);
        wedstrijd.setDeelnemer(this);
        return this;
    }

    public Deelnemer removeWedstrijd(Wedstrijd wedstrijd) {
        this.wedstrijds.remove(wedstrijd);
        wedstrijd.setDeelnemer(null);
        return this;
    }

    public Set<Persoon> getPersoons() {
        return this.persoons;
    }

    public void setPersoons(Set<Persoon> persoons) {
        if (this.persoons != null) {
            this.persoons.forEach(i -> i.setDeelnemer(null));
        }
        if (persoons != null) {
            persoons.forEach(i -> i.setDeelnemer(this));
        }
        this.persoons = persoons;
    }

    public Deelnemer persoons(Set<Persoon> persoons) {
        this.setPersoons(persoons);
        return this;
    }

    public Deelnemer addPersoon(Persoon persoon) {
        this.persoons.add(persoon);
        persoon.setDeelnemer(this);
        return this;
    }

    public Deelnemer removePersoon(Persoon persoon) {
        this.persoons.remove(persoon);
        persoon.setDeelnemer(null);
        return this;
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<Team> teams) {
        if (this.teams != null) {
            this.teams.forEach(i -> i.setDeelnemer(null));
        }
        if (teams != null) {
            teams.forEach(i -> i.setDeelnemer(this));
        }
        this.teams = teams;
    }

    public Deelnemer teams(Set<Team> teams) {
        this.setTeams(teams);
        return this;
    }

    public Deelnemer addTeam(Team team) {
        this.teams.add(team);
        team.setDeelnemer(this);
        return this;
    }

    public Deelnemer removeTeam(Team team) {
        this.teams.remove(team);
        team.setDeelnemer(null);
        return this;
    }

    public Score getScore() {
        return this.score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Deelnemer score(Score score) {
        this.setScore(score);
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
