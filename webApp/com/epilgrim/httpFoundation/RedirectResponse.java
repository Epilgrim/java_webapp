package com.epilgrim.httpFoundation;

public class RedirectResponse extends Response{
    public RedirectResponse(String location) {
        super("", Response.HTTP_FOUND);
        this.getHeaders().set("Location", location);
    }
}
