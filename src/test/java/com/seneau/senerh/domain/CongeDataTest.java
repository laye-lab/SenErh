package com.seneau.senerh.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.seneau.senerh.web.rest.TestUtil;

public class CongeDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CongeData.class);
        CongeData congeData1 = new CongeData();
        congeData1.setId(1L);
        CongeData congeData2 = new CongeData();
        congeData2.setId(congeData1.getId());
        assertThat(congeData1).isEqualTo(congeData2);
        congeData2.setId(2L);
        assertThat(congeData1).isNotEqualTo(congeData2);
        congeData1.setId(null);
        assertThat(congeData1).isNotEqualTo(congeData2);
    }
}
