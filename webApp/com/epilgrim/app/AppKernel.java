package com.epilgrim.app;

import com.epilgrim.app.controller.IndexController;
import com.epilgrim.app.controller.LoginGetController;
import com.epilgrim.app.controller.LoginPostController;
import com.epilgrim.app.controller.LogoutController;
import com.epilgrim.app.controller.PageController;
import com.epilgrim.app.model.User;
import com.epilgrim.app.model.UserRepository;
import com.epilgrim.framework.HttpKernel;
import com.epilgrim.framework.authorization.AuthorizationResolver;
import com.epilgrim.framework.controller.ControllerResolver;
import com.epilgrim.framework.templating.TemplateResolver;

public class AppKernel extends HttpKernel {

    public void init() {

        UserRepository userRepository = new UserRepository();
        userRepository.addUser(new User("user1", "user1", "role_page_1"));
        userRepository.addUser(new User("user2", "user2", "role_page_2"));
        userRepository.addUser(new User("user3", "user3", "role_page_3"));

        ControllerResolver controllerResolver = this.getControllerResolver();
        controllerResolver.addController("login", "GET", "/login", new LoginGetController(this.getTemplateResolver()));
        controllerResolver.addController("login_post", "POST", "/login", new LoginPostController(userRepository));
        controllerResolver.addController("index", "GET", "/index", new IndexController(this.getTemplateResolver()));
        controllerResolver.addController("page_1", "GET", "/page_1", new PageController(this.getTemplateResolver(), userRepository));
        controllerResolver.addController("page_2", "GET", "/page_2", new PageController(this.getTemplateResolver(), userRepository));
        controllerResolver.addController("page_3", "GET", "/page_3", new PageController(this.getTemplateResolver(), userRepository));
        controllerResolver.addController("logout", "GET", "/logout", new LogoutController());

        AuthorizationResolver authorizationResolver = this.getAuthorizationResolver();
        authorizationResolver.addRule("/login", "annonymous");
        authorizationResolver.addRule("/page_1", "role_page_1");
        authorizationResolver.addRule("/page_2", "role_page_2");
        authorizationResolver.addRule("/index", "role_logged_in");
        authorizationResolver.addRule("/logout", "role_logged_in");

        TemplateResolver templateResolver = this.getTemplateResolver();
        templateResolver.setLayout("/com/epilgrim/app/templates/layout.template");
        templateResolver.addTemplate(
            "login",
            "/com/epilgrim/app/templates/login.template"
        );
        templateResolver.addTemplate(
            "index",
            "/com/epilgrim/app/templates/index.template"
        );
        templateResolver.addTemplate(
            "page",
            "/com/epilgrim/app/templates/page.template"
        );


        this.setAuthenticationResolver(new AuthenticationResolver());

    }
}
