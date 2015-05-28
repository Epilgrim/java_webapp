package com.epilgrim.framework.authorization;

import java.util.Vector;

import com.epilgrim.httpFoundation.Request;

public class AuthorizationResolver {

    private Vector<RuleMap> rules = new Vector<RuleMap>();

    public void assertAuthorized(Request request) throws NotAuthorizedException
    {
        for (RuleMap ruleMap : this.rules) {
            if (
                request.getPath().matches(ruleMap.path)
                &&
                ruleMap.role.matches(request.getAttributes().get("_role"))
            ) {
                return;
            }
        }

        throw new NotAuthorizedException();
    }

    public void addRule(String path, String role)
    {
        RuleMap ruleMap = new RuleMap();

        ruleMap.path = path;
        ruleMap.role = role;

        this.rules.add(ruleMap);

    }

    private class RuleMap
    {
        public String role;
        public String path;
    }
}
