package nl.imvdm.hsv.contestapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.imvdm.hsv.contestapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerenigingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vereniging.class);
        Vereniging vereniging1 = new Vereniging();
        vereniging1.setId(1L);
        Vereniging vereniging2 = new Vereniging();
        vereniging2.setId(vereniging1.getId());
        assertThat(vereniging1).isEqualTo(vereniging2);
        vereniging2.setId(2L);
        assertThat(vereniging1).isNotEqualTo(vereniging2);
        vereniging1.setId(null);
        assertThat(vereniging1).isNotEqualTo(vereniging2);
    }
}
