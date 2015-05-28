package com.epilgrim.framework.authentication;

import com.epilgrim.httpFoundation.Request;

public interface AuthenticationResolverInterface {
    public static final String ANNONYMOUS = "annonymous";

    public void authenticate(Request request);
}
