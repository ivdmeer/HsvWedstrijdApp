package nl.imvdm.hsv.contestapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.imvdm.hsv.contestapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeelnemerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deelnemer.class);
        Deelnemer deelnemer1 = new Deelnemer();
        deelnemer1.setId(1L);
        Deelnemer deelnemer2 = new Deelnemer();
        deelnemer2.setId(deelnemer1.getId());
        assertThat(deelnemer1).isEqualTo(deelnemer2);
        deelnemer2.setId(2L);
        assertThat(deelnemer1).isNotEqualTo(deelnemer2);
        deelnemer1.setId(null);
        assertThat(deelnemer1).isNotEqualTo(deelnemer2);
    }
}
