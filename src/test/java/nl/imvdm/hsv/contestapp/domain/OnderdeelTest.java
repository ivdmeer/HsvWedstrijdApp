package nl.imvdm.hsv.contestapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.imvdm.hsv.contestapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OnderdeelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Onderdeel.class);
        Onderdeel onderdeel1 = new Onderdeel();
        onderdeel1.setId(1L);
        Onderdeel onderdeel2 = new Onderdeel();
        onderdeel2.setId(onderdeel1.getId());
        assertThat(onderdeel1).isEqualTo(onderdeel2);
        onderdeel2.setId(2L);
        assertThat(onderdeel1).isNotEqualTo(onderdeel2);
        onderdeel1.setId(null);
        assertThat(onderdeel1).isNotEqualTo(onderdeel2);
    }
}
