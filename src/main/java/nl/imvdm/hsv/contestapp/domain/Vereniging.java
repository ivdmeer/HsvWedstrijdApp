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
 * A Vereniging.
 */
@Entity
@Table(name = "vereniging")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vereniging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "naam", nullable = false)
    private String naam;

    @OneToMany(mappedBy = "vereniging")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vereniging", "deelnemers" }, allowSetters = true)
    private Set<Persoon> persoons = new HashSet<>();

    @OneToMany(mappedBy = "vereniging")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vereniging", "deelnemers" }, allowSetters = true)
    private Set<Team> teams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vereniging id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Vereniging naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Set<Persoon> getPersoons() {
        return this.persoons;
    }

    public void setPersoons(Set<Persoon> persoons) {
        if (this.persoons != null) {
            this.persoons.forEach(i -> i.setVereniging(null));
        }
        if (persoons != null) {
            persoons.forEach(i -> i.setVereniging(this));
        }
        this.persoons = persoons;
    }

    public Vereniging persoons(Set<Persoon> persoons) {
        this.setPersoons(persoons);
        return this;
    }

    public Vereniging addPersoon(Persoon persoon) {
        this.persoons.add(persoon);
        persoon.setVereniging(this);
        return this;
    }

    public Vereniging removePersoon(Persoon persoon) {
        this.persoons.remove(persoon);
        persoon.setVereniging(null);
        return this;
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<Team> teams) {
        if (this.teams != null) {
            this.teams.forEach(i -> i.setVereniging(null));
        }
        if (teams != null) {
            teams.forEach(i -> i.setVereniging(this));
        }
        this.teams = teams;
    }

    public Vereniging teams(Set<Team> teams) {
        this.setTeams(teams);
        return this;
    }

    public Vereniging addTeam(Team team) {
        this.teams.add(team);
        team.setVereniging(this);
        return this;
    }

    public Vereniging removeTeam(Team team) {
        this.teams.remove(team);
        team.setVereniging(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vereniging)) {
            return false;
        }
        return id != null && id.equals(((Vereniging) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vereniging{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
