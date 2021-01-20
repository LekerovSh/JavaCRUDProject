package com.iql.javaCRUD;

import com.iql.javaCRUD.models.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ControllerTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void signup() throws Exception {
        String uri = "/users/signup";
        User requestedUser = new User();
        requestedUser.setEmail("newuser@mail.ru");
        requestedUser.setName("newusername");
        requestedUser.setPassword("newuserpass");

        String inputJson = super.mapToJson(requestedUser);
        MvcResult mvcResult = mvc.perform(post(uri)
                .contentType(APPLICATION_JSON_UTF8).content(inputJson)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,  status);
    }

}
