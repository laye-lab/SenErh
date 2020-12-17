package com.seneau.senerh.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.seneau.senerh.web.rest.TestUtil;

public class HistoriqueCongeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoriqueConge.class);
        HistoriqueConge historiqueConge1 = new HistoriqueConge();
        historiqueConge1.setId(1L);
        HistoriqueConge historiqueConge2 = new HistoriqueConge();
        historiqueConge2.setId(historiqueConge1.getId());
        assertThat(historiqueConge1).isEqualTo(historiqueConge2);
        historiqueConge2.setId(2L);
        assertThat(historiqueConge1).isNotEqualTo(historiqueConge2);
        historiqueConge1.setId(null);
        assertThat(historiqueConge1).isNotEqualTo(historiqueConge2);
    }
}
