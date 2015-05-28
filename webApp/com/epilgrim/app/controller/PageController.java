package com.epilgrim.app.controller;

import com.epilgrim.app.model.User;
import com.epilgrim.app.model.UserNotFoundException;
import com.epilgrim.app.model.UserRepository;
import com.epilgrim.framework.controller.ControllerInterface;
import com.epilgrim.framework.templating.TemplateResolver;
import com.epilgrim.httpFoundation.Request;
import com.epilgrim.httpFoundation.Response;

public class PageController implements ControllerInterface {
    private TemplateResolver templateResolver;
    private UserRepository userRepository;
    
    public PageController(TemplateResolver templateResolver,
            UserRepository userRepository) {
        this.templateResolver = templateResolver;
        this.userRepository = userRepository;
    }

    public Response execute(Request request) {
        try {
            User user = this.userRepository.findUser(request.getSession().get("user_name"));
            String[] values = { request.getPath(), user.getName()};
            
            return new Response(this.templateResolver.render("page", values), 200);
        } catch (UserNotFoundException e) {
        	
            return new Response("error!!", Response.HTTP_INTERNAL_SERVER_ERROR);
        }
    }

}
