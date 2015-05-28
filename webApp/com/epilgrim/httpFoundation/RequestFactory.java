package com.epilgrim.httpFoundation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class RequestFactory {
    public static Request createFromExchange(HttpExchange exchange) throws IOException
    {

        return new Request(
            exchange.getRequestMethod(),
            exchange.getRequestURI().getPath(),
            parseGetParameters(exchange),
            parsePostParameters(exchange),
            parseCookies(exchange)
);
    }

    private static ParameterBag parseGetParameters(HttpExchange exchange)
    {
        Map<String, String> bag = new LinkedHashMap<String, String>();
        parseQuery(exchange.getRequestURI().getQuery(), bag);
        return new ParameterBag(bag);
    }

    private static ParameterBag parsePostParameters(HttpExchange exchange) throws IOException
    {
        Map<String, String> bag = new LinkedHashMap<String, String>();
        if ("post".equalsIgnoreCase(exchange.getRequestMethod())) {
            InputStreamReader isr;
            isr = new InputStreamReader(exchange.getRequestBody(),"utf-8");

            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();
            parseQuery(query, bag);
        }
        return new ParameterBag(bag);
    }

    private static ParameterBag parseCookies(HttpExchange exchange)
    {
        Map<String, String> bag = new LinkedHashMap<String, String>();
        Headers reqHeaders = exchange.getRequestHeaders();
        if (reqHeaders.containsKey("Cookie")){
            String cookies = reqHeaders.get("Cookie").toString();
            cookies = cookies.substring(1, cookies.length()-1);

            Pattern cookiePattern = Pattern.compile("([^=]+)=([^\\;]*);?\\s?");
            Matcher matcher = cookiePattern.matcher(cookies);
            while (matcher.find()) {
                String cookieKey = matcher.group(1);
                String cookieValue = matcher.group(2);
                bag.put(cookieKey, cookieValue);
            }
        }


        return new ParameterBag(bag);
    }

    private static void parseQuery(String queryString, Map<String, String> bag)
    {
        if (null != queryString) {
            String[] pairs = queryString.split("&");
            for (String pair : pairs) {
              int idx = pair.indexOf("=");
              String key = idx > 0 ? pair.substring(0, idx) : pair;

              String value = idx > 0 && pair.length() > idx + 1 ? pair.substring(idx + 1) : null;
              bag.put(key, value);
            }
        }
    }
}
