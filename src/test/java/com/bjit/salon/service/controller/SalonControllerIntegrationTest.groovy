package com.bjit.salon.service.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class SalonControllerIntegrationTest extends Specification {


    @Autowired
    private MockMvc mockMvc;

    def "should create a new salon"(){

        given:
        def content = "{\n" +
                "    \"userId\":2,\n" +
                "    \"name\":\"CDE Salon\",\n" +
                "    \"description\":\"Near Dhaka\",\n" +
                "    \"address\":\"Dhaka\",\n" +
                "    \"openingTime\":\"\",\n" +
                "    \"closingTime\":\"\",\n" +
                "    \"contractNumber\":\"456346\"\n" +
                "}"


        expect:
        mockMvc.perform(post("/api/v1/salons")
                .content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is2xxSuccessful());

    }

    def "should update a salon by salon id"(){
        given:
        def content = "{\n" +
                "    \"id\":1,\n" +
                "    \"userId\":2,\n" +
                "    \"name\":\"ABC Salon new updated\",\n" +
                "    \"description\":\"Near Dhaka\",\n" +
                "    \"address\":\"Dhaka\",\n" +
                "    \"openingTime\":\"\",\n" +
                "    \"closingTime\":\"\",\n" +
                "    \"contractNumber\":\"456346\"\n" +
                "}"


        expect:
        mockMvc.perform(put("/api/v1/salons")
                .content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is2xxSuccessful());


    }

    def "should return a salon object by salon id"(){
        expect:
        mockMvc.perform(get("/api/v1/salons/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    def "should return the list of salons"(){
        expect:
        mockMvc.perform(get("/api/v1/salons"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    def "should return the list of salons by query search"(){
        expect:
        mockMvc.perform(get("/api/v1/salons?q=abc"))
                .andExpect(status().isOk())
                .andDo(print());
    }



}