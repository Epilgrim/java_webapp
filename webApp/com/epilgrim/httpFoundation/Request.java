package com.epilgrim.httpFoundation;

public class Request {

    private String method;
    private String path;
    private ParameterBag query;
    private ParameterBag post;
    private ParameterBag attributes;
    private ParameterBag session;
    private ParameterBag cookies;

    public Request(
        String method,
        String path,
        ParameterBag query,
        ParameterBag post,
        ParameterBag cookies
    ) {
        this.method = method;
        this.path = path;
        this.query = query;
        this.post = post;
        this.cookies = cookies;
        this.attributes = new ParameterBag();
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public ParameterBag getQuery()
    {
        return query;
    }

    public ParameterBag getPost()
    {
        return post;
    }

    public ParameterBag getCookies()
    {
        return cookies;
    }

    public ParameterBag getAttributes()
    {
        return attributes;
    }

    public ParameterBag getSession() {
        return session;
    }

    public void setSession(ParameterBag session) {
        this.session = session;
    }

    public String toString()
    {
        return "Query: "+ this.query.toString() + System.getProperty("line.separator") + "Post: " + this.post.toString();
    }
}
