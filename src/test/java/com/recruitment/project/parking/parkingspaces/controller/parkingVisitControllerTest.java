package com.recruitment.project.parking.parkingspaces.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class parkingVisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldParkingVisitStartWithOnlyRegistration() throws Exception {
        mockMvc
                .perform(get("/parking/start/WWA 1234/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Started")));
    }

    @Test
    public void shouldParkingVisitStartWithAllParameters() throws Exception {
        mockMvc
                .perform(get("/parking/start/WWA 4321/Toyota/Avensis/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Started")));
    }

    @Test
    public void shouldParkingVisitStop() throws Exception {
        mockMvc
                .perform(get("/parking/stop/WWA 1234"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Finished")));
    }

    @Test
    public void shouldParkingVisitStopAllParameters() throws Exception {
        mockMvc
                .perform(get("/parking/stop/WWA 4321"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Finished")));
    }

    @Test
    public void shouldParkingVisitObjectReturn() throws Exception {
        mockMvc
                .perform(get("/parking/is-starting/WWA 4321"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
