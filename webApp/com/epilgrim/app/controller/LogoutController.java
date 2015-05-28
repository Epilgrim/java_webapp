package com.epilgrim.app.controller;

import com.epilgrim.framework.controller.ControllerInterface;
import com.epilgrim.httpFoundation.RedirectResponse;
import com.epilgrim.httpFoundation.Request;
import com.epilgrim.httpFoundation.Response;

public class LogoutController implements ControllerInterface {

    public Response execute(Request request)
    {
        request.getSession().clear();

        Response response = new RedirectResponse("/login?msg=logout%20successful");

        return response;
    }
}
