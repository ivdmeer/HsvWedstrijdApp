package nl.imvdm.hsv.contestapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
    private Wedstrijd wedstrijd;

    @ManyToOne
    @JsonIgnoreProperties(value = { "team" }, allowSetters = true)
    private Persoon persoon;

    @ManyToOne
    @JsonIgnoreProperties(value = { "vereniging" }, allowSetters = true)
    private Team team;

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
