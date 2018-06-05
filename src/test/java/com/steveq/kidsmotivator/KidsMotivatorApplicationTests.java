package com.steveq.kidsmotivator;

import static org.assertj.core.api.Assertions.assertThat;
import com.steveq.kidsmotivator.app.dashboard.controller.DashboardController;
import com.steveq.kidsmotivator.app.auth.LoginController;
import com.steveq.kidsmotivator.app.missions.controller.MissionController;
import com.steveq.kidsmotivator.app.prizes.controller.PrizesController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KidsMotivatorApplicationTests {

    @Autowired
    private DashboardController dashboardController;

    @Autowired
    private LoginController loginController;

    @Autowired
    private MissionController missionController;

    @Autowired
    private PrizesController prizesController;

    @Test
    public void contextLoads() {
        assertThat(dashboardController).isNotNull();
        assertThat(loginController).isNotNull();
        assertThat(missionController).isNotNull();
        assertThat(prizesController).isNotNull();
    }

}
