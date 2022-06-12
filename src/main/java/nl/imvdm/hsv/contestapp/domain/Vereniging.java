package nl.imvdm.hsv.contestapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "verenigings", "deelnemer" }, allowSetters = true)
    private Persoon persoon;

    @ManyToOne
    @JsonIgnoreProperties(value = { "verenigings", "deelnemer" }, allowSetters = true)
    private Team team;

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

    public Persoon getPersoon() {
        return this.persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

    public Vereniging persoon(Persoon persoon) {
        this.setPersoon(persoon);
        return this;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Vereniging team(Team team) {
        this.setTeam(team);
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
