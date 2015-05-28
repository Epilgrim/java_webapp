package com.epilgrim.framework.controller;

import java.util.Vector;

import com.epilgrim.httpFoundation.Request;

public class ControllerResolver {

    private Vector<RouteMap> controllers = new Vector<RouteMap>();

    public ControllerInterface resolve(Request request) throws ControllerNotFoundException
    {
        if (this.controllers.isEmpty()){
            throw new ControllerNotFoundException();
        }
        for (RouteMap routeMap : this.controllers) {
            if (request.getAttributes().has("_controller")) {
                if (routeMap.name.equals(request.getAttributes().get("_controller"))) {
                    return routeMap.controller;
                }
            } else {
                if (
                    routeMap.method.equals(request.getMethod())
                    &&
                    routeMap.path.equals(request.getPath())
                ) {
                    return routeMap.controller;
                }
            }
        }

        throw new ControllerNotFoundException();
    }

    public void addController(String name, String method, String path, ControllerInterface controller)
    {
        RouteMap routeMap = new RouteMap();
        routeMap.name = name;
        routeMap.method = method;
        routeMap.path = path;
        routeMap.controller = controller;

        this.controllers.add(routeMap);

    }

    private class RouteMap
    {
        public String name;
        public String method;
        public String path;
        public ControllerInterface controller;
    }
}
