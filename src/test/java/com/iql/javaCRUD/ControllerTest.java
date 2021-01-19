package com.iql.javaCRUD;

import com.iql.javaCRUD.models.User;
import com.iql.javaCRUD.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

public class ControllerTest extends AbstractTest {
    @Autowired
    private UserService userService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    @Transactional
    public void createUser() throws Exception {
        String uri = "/users/signup";
        User requestedUser = new User();
        requestedUser.setEmail("newmail@mail.ru");
        requestedUser.setName("PidorChina");
        requestedUser.setPassword("asdasd");
        Long expectedId = userService.countAllUsers();

        String inputJson = super.mapToJson(requestedUser);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.ALL).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,  status);

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }
}
