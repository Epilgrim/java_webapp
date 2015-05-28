package com.epilgrim.framework;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map.Entry;

import com.epilgrim.httpFoundation.Request;
import com.epilgrim.httpFoundation.RequestFactory;
import com.epilgrim.httpFoundation.Response;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class HttpKernel extends AppKernel implements HttpHandler
{
    public void handle(HttpExchange t) throws IOException
    {
        Request request = RequestFactory.createFromExchange(t);

        Response response = this.handle(request);

        Iterator<Entry<String, String>> headers = response.getHeaders().getIterator();
        while (headers.hasNext()) {
            Entry<String, String> header = headers.next();
            t.getResponseHeaders().set(header.getKey(), header.getValue());
        }
        t.sendResponseHeaders(response.getStatus(), response.getContent().length());
        OutputStream os = t.getResponseBody();
        os.write(response.getContent().getBytes());
        os.close();
    }
}
