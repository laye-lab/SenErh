package com.seneau.senerh.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.seneau.senerh.web.rest.TestUtil;

public class CongeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conge.class);
        Conge conge1 = new Conge();
        conge1.setId(1L);
        Conge conge2 = new Conge();
        conge2.setId(conge1.getId());
        assertThat(conge1).isEqualTo(conge2);
        conge2.setId(2L);
        assertThat(conge1).isNotEqualTo(conge2);
        conge1.setId(null);
        assertThat(conge1).isNotEqualTo(conge2);
    }
}
