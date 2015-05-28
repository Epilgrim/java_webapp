package com.epilgrim.framework.controller;

import com.epilgrim.httpFoundation.Request;
import com.epilgrim.httpFoundation.Response;

public interface ControllerInterface {

    public Response execute(Request request);

}
