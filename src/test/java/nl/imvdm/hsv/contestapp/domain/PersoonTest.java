package nl.imvdm.hsv.contestapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.imvdm.hsv.contestapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Persoon.class);
        Persoon persoon1 = new Persoon();
        persoon1.setId(1L);
        Persoon persoon2 = new Persoon();
        persoon2.setId(persoon1.getId());
        assertThat(persoon1).isEqualTo(persoon2);
        persoon2.setId(2L);
        assertThat(persoon1).isNotEqualTo(persoon2);
        persoon1.setId(null);
        assertThat(persoon1).isNotEqualTo(persoon2);
    }
}
