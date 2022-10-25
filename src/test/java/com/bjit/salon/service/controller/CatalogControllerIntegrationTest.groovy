package com.bjit.salon.service.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CatalogControllerIntegrationTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    def "should create a new catalog"(){

        given:
        def content = "{\n" +
                "    \"name\":\"Massages\",\n" +
                "    \"description\":\"massages\",\n" +
                "    \"approximateTimeForCompletion\":120,\n" +
                "    \"payableAmount\":800.0\n" +
                "\n" +
                "}"


        expect:
        mockMvc.perform(post("/api/v1/salons/catalogs")
                .content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is2xxSuccessful());
    }

    def "should return a list of catalog"(){
        expect:
        mockMvc.perform(get("/api/v1/salons/catalogs"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    def "should return a catalog object by catalog id"(){
        expect:
        mockMvc.perform(get("/api/v1/salons/catalogs/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}