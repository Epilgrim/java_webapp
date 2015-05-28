package com.epilgrim.app.controller;

import com.epilgrim.app.model.User;
import com.epilgrim.app.model.UserNotFoundException;
import com.epilgrim.app.model.UserRepository;
import com.epilgrim.framework.controller.ControllerInterface;
import com.epilgrim.httpFoundation.RedirectResponse;
import com.epilgrim.httpFoundation.Request;
import com.epilgrim.httpFoundation.Response;

public class LoginPostController implements ControllerInterface {
    private UserRepository userRepository;
    
    public LoginPostController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response execute(Request request) {
        try {
            User user = this.userRepository.findUser(request.getPost().get("login"));
            if (user.getPassword().equals(request.getPost().get("password"))) {
                request.getSession().set("user_role", user.getRole().concat("|role_logged_in"));
                request.getSession().set("user_name", user.getName());
                
                Response response = new RedirectResponse("/index");
                
                return response;
            }
        } catch (UserNotFoundException e) {

        }
        Response response = new RedirectResponse("/login?msg=wrong%20username%20or%20password");

        return response;
    }

}
