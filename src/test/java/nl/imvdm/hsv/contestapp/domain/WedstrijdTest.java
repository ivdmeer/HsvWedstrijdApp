package nl.imvdm.hsv.contestapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.imvdm.hsv.contestapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WedstrijdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wedstrijd.class);
        Wedstrijd wedstrijd1 = new Wedstrijd();
        wedstrijd1.setId(1L);
        Wedstrijd wedstrijd2 = new Wedstrijd();
        wedstrijd2.setId(wedstrijd1.getId());
        assertThat(wedstrijd1).isEqualTo(wedstrijd2);
        wedstrijd2.setId(2L);
        assertThat(wedstrijd1).isNotEqualTo(wedstrijd2);
        wedstrijd1.setId(null);
        assertThat(wedstrijd1).isNotEqualTo(wedstrijd2);
    }
}
