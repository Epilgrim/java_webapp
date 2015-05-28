package com.epilgrim.httpFoundation;

public class Response {

    public static final Integer HTTP_OK = 200;
    public static final Integer HTTP_FOUND = 302;
    public static final Integer HTTP_FORBIDDEN = 403;
    public static final Integer HTTP_INTERNAL_SERVER_ERROR = 500;

    private String content;
    private Integer status;
    private ParameterBag headers;


    public Response(String content, Integer status) {
        this.content = content;
        this.status = status;
        this.headers = new ParameterBag();
    }

    public ParameterBag getHeaders()
    {
        return headers;
    }

    public Integer getStatus()
    {
        return status;
    }

    public String getContent()
    {
        return content;
    }
}
