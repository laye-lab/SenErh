package com.seneau.senerh.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.seneau.senerh.web.rest.TestUtil;

public class RecuperationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recuperation.class);
        Recuperation recuperation1 = new Recuperation();
        recuperation1.setId(1L);
        Recuperation recuperation2 = new Recuperation();
        recuperation2.setId(recuperation1.getId());
        assertThat(recuperation1).isEqualTo(recuperation2);
        recuperation2.setId(2L);
        assertThat(recuperation1).isNotEqualTo(recuperation2);
        recuperation1.setId(null);
        assertThat(recuperation1).isNotEqualTo(recuperation2);
    }
}
