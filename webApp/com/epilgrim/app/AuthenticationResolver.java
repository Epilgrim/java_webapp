package com.epilgrim.app;

import com.epilgrim.framework.authentication.AuthenticationResolverInterface;
import com.epilgrim.httpFoundation.Request;

public class AuthenticationResolver implements AuthenticationResolverInterface{
    public void authenticate(Request request) {
        if (request.getSession().has("user_role")){
            request.getAttributes().set("_role", request.getSession().get("user_role"));

            return;
        }
        request.getAttributes().set("_role", AuthenticationResolverInterface.ANNONYMOUS);
    }

}
