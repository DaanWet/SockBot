package me.damascus2000.sockapplication.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.damascus2000.sockapplication.controllers.AuthController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class CustomAccessDeniedHandler implements AuthenticationEntryPoint {

    record MessageModel(String message) {}

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        EntityModel<MessageModel> model = EntityModel.of(new MessageModel("Login required"), Links.of(
                linkTo(AuthController.class).slash("login").withRel("Login page")
        ));

        response.setHeader("Content-Type", "application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(
                objectMapper.writeValueAsString(model)
        );
    }
}