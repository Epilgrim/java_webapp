package com.epilgrim.framework;

import com.epilgrim.framework.authentication.AuthenticationResolverInterface;
import com.epilgrim.framework.authorization.AuthorizationResolver;
import com.epilgrim.framework.authorization.NotAuthorizedException;
import com.epilgrim.framework.controller.ControllerInterface;
import com.epilgrim.framework.controller.ControllerNotFoundException;
import com.epilgrim.framework.controller.ControllerResolver;
import com.epilgrim.framework.session.SessionStorage;
import com.epilgrim.framework.templating.TemplateResolver;
import com.epilgrim.httpFoundation.ParameterBag;
import com.epilgrim.httpFoundation.RedirectResponse;
import com.epilgrim.httpFoundation.Request;
import com.epilgrim.httpFoundation.Response;

public abstract class AppKernel {

    public abstract void init();

    private ControllerResolver controllerResolver = new ControllerResolver();
    private AuthorizationResolver authorizationResolver = new AuthorizationResolver();
    private TemplateResolver templateResolver = new TemplateResolver();
    private AuthenticationResolverInterface authenticationResolver;
    private SessionStorage sessionStorage = SessionStorage.getInstance();

    public Response handle(Request request)
    {
        Response response;
        ParameterBag session;

        if (request.getCookies().has("session_id")){
            session = this.sessionStorage.getData(request.getCookies().get("session_id"));
        } else {
            session = this.sessionStorage.getData();
        }
        request.setSession(session);

        try {
            this.authenticationResolver.authenticate(request);
            this.authorizationResolver.assertAuthorized(request);

            ControllerInterface controller = this.getControllerResolver().resolve(request);
            response = controller.execute(request);
        } catch (ControllerNotFoundException e) {
            response = new Response("controller not found", Response.HTTP_INTERNAL_SERVER_ERROR);
        } catch (NotAuthorizedException e) {
            if (request.getAttributes().get("_role").matches(AuthenticationResolverInterface.ANNONYMOUS)) {
                response = new RedirectResponse("/login");
            } else {
                response = new Response("user unauthorized", Response.HTTP_FORBIDDEN);
            }
        }

        return response;
    }

    public void setAuthenticationResolver(AuthenticationResolverInterface authenticationResolver)
    {
        this.authenticationResolver = authenticationResolver;
    }

    public ControllerResolver getControllerResolver()
    {
        return controllerResolver;
    }

    public AuthorizationResolver getAuthorizationResolver()
    {
        return authorizationResolver;
    }

    public TemplateResolver getTemplateResolver()
    {
        return templateResolver;
    }
}
