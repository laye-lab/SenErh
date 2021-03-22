package com.seneau.senerh.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.seneau.senerh.web.rest.TestUtil;

public class TrackerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tracker.class);
        Tracker tracker1 = new Tracker();
        tracker1.setId(1L);
        Tracker tracker2 = new Tracker();
        tracker2.setId(tracker1.getId());
        assertThat(tracker1).isEqualTo(tracker2);
        tracker2.setId(2L);
        assertThat(tracker1).isNotEqualTo(tracker2);
        tracker1.setId(null);
        assertThat(tracker1).isNotEqualTo(tracker2);
    }
}
