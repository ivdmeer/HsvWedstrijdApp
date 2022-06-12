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
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "naam", nullable = false)
    private String naam;

    @ManyToOne
    @JsonIgnoreProperties(value = { "persoons", "teams" }, allowSetters = true)
    private Vereniging vereniging;

    @OneToMany(mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "wedstrijd", "persoon", "team", "scores" }, allowSetters = true)
    private Set<Deelnemer> deelnemers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Team id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Team naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Vereniging getVereniging() {
        return this.vereniging;
    }

    public void setVereniging(Vereniging vereniging) {
        this.vereniging = vereniging;
    }

    public Team vereniging(Vereniging vereniging) {
        this.setVereniging(vereniging);
        return this;
    }

    public Set<Deelnemer> getDeelnemers() {
        return this.deelnemers;
    }

    public void setDeelnemers(Set<Deelnemer> deelnemers) {
        if (this.deelnemers != null) {
            this.deelnemers.forEach(i -> i.setTeam(null));
        }
        if (deelnemers != null) {
            deelnemers.forEach(i -> i.setTeam(this));
        }
        this.deelnemers = deelnemers;
    }

    public Team deelnemers(Set<Deelnemer> deelnemers) {
        this.setDeelnemers(deelnemers);
        return this;
    }

    public Team addDeelnemer(Deelnemer deelnemer) {
        this.deelnemers.add(deelnemer);
        deelnemer.setTeam(this);
        return this;
    }

    public Team removeDeelnemer(Deelnemer deelnemer) {
        this.deelnemers.remove(deelnemer);
        deelnemer.setTeam(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        return id != null && id.equals(((Team) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            "}";
    }
}
