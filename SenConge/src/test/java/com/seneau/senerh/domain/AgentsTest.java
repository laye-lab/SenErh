package com.seneau.senerh.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.seneau.senerh.web.rest.TestUtil;

public class AgentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agents.class);
        Agents agents1 = new Agents();
        agents1.setId(1L);
        Agents agents2 = new Agents();
        agents2.setId(agents1.getId());
        assertThat(agents1).isEqualTo(agents2);
        agents2.setId(2L);
        assertThat(agents1).isNotEqualTo(agents2);
        agents1.setId(null);
        assertThat(agents1).isNotEqualTo(agents2);
    }
}
