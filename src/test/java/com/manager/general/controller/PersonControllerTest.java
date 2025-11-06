package com.manager.general.controller;


import com.manager.general.entity.Person;
import com.manager.general.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    void shouldReturnAllPersons() throws Exception {
        // given
        Person p1 = new Person(1L, "mehdi", "taouil");
        Person p2 = new Person(2L, "Jane", "Smith");
        Page<Person> page = new PageImpl<>(List.of(p1, p2));

        when(personService.getAllPersons(any(Pageable.class))).thenReturn(page);

        // when & then
        mockMvc.perform(get("/api/persons")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("mehdi"))
                .andExpect(jsonPath("$.content[1].firstName").value("Jane"));
    }
    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    void shouldReturnPersonById() throws Exception {
        Person p = new Person(1L, "mehdi", "taouil");
        when(personService.findById(1L)).thenReturn(Optional.of(p));

        mockMvc.perform(get("/api/persons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("mehdi"))
                .andExpect(jsonPath("$.lastName").value("taouil"));
    }

}
