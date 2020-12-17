package com.seneau.senerh.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.seneau.senerh.web.rest.TestUtil;

public class ValidationStepTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValidationStep.class);
        ValidationStep validationStep1 = new ValidationStep();
        validationStep1.setId(1L);
        ValidationStep validationStep2 = new ValidationStep();
        validationStep2.setId(validationStep1.getId());
        assertThat(validationStep1).isEqualTo(validationStep2);
        validationStep2.setId(2L);
        assertThat(validationStep1).isNotEqualTo(validationStep2);
        validationStep1.setId(null);
        assertThat(validationStep1).isNotEqualTo(validationStep2);
    }
}
