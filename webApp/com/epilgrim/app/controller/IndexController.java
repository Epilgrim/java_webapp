package com.epilgrim.app.controller;

import com.epilgrim.framework.controller.ControllerInterface;
import com.epilgrim.framework.templating.TemplateResolver;
import com.epilgrim.httpFoundation.Request;
import com.epilgrim.httpFoundation.Response;

public class IndexController implements ControllerInterface {
    private TemplateResolver templateResolver;

    public IndexController(TemplateResolver templateResolver) {
        this.templateResolver = templateResolver;
    }

    public Response execute(Request request)
    {
        String[] placeholders = { "juan" };
        Response response = new Response(this.templateResolver.render("index", placeholders), 200);
        response.getHeaders().set("Set-Cookie", "session_id=" + request.getSession().get("session_id"));
        return response;
    }
}
