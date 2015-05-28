package com.epilgrim.framework.session;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.epilgrim.httpFoundation.ParameterBag;

public class SessionStorage {

    private Map<String,Session> usedSessionKeys;
    private long lastCleanup;

    private final long cleanupEverySecs;
    private final long maxSessionInSecs;
    static private SecureRandom random = new SecureRandom();

    public static SessionStorage getInstance() {
        return Holder.INSTANCE;
    }

    private SessionStorage() {
        usedSessionKeys = new ConcurrentHashMap<String,Session>();
        Calendar cal = Calendar.getInstance();
        lastCleanup = cal.getTimeInMillis();
        cleanupEverySecs = 1000 * 60 * 5; // 5 minutes
        maxSessionInSecs = 1000 * 60 * 5; // 5 minutes
    }

    private static class Holder {
        private static final SessionStorage INSTANCE = new SessionStorage();
    }

    private Session createSession()
    {
        Calendar cal = Calendar.getInstance();
        String sessionKey = new BigInteger(130, random).toString(32);
        Session session = new Session(cal.getTimeInMillis(), sessionKey);

        usedSessionKeys.put(sessionKey, session);

        return session;
    }

    public ParameterBag getData()
    {
        return this.getData(this.createSession().getKey());
    }

    public ParameterBag getData(String sessionKey)
    {
        Calendar cal = Calendar.getInstance();
        Session session = usedSessionKeys.get(sessionKey);

        long currentTime = cal.getTimeInMillis();

        if (session == null) {
            session = this.createSession();
        } else if (currentTime - session.getStoredTime() > maxSessionInSecs) {
            session = this.createSession();
        }

        if (currentTime - lastCleanup > cleanupEverySecs)
            doCleanup();

        session.setStoredTime(Calendar.getInstance().getTimeInMillis());

        return session.getData();
    }

    private void doCleanup() {
        Calendar cal = Calendar.getInstance();
        lastCleanup = cal.getTimeInMillis();
        Iterator<String> iter = usedSessionKeys.keySet().iterator();
        String key = "";

        while(iter.hasNext()) {
            key = iter.next();
            if (lastCleanup - usedSessionKeys.get(key).getStoredTime() > maxSessionInSecs) {
                usedSessionKeys.remove(key);
            }
        }
    }

    private class Session {

        long storedTime;
        String key;
        ParameterBag data = new ParameterBag();

        public Session(long storedTime, String key)
        {
            this.storedTime = storedTime;
            this.key = key;
            this.data.set("session_id", key);
        }

        public long getStoredTime()
        {
            return storedTime;
        }

        public void setStoredTime(long storedTime)
        {
            this.storedTime = storedTime;
        }

        public String getKey()
        {
            return this.key;
        }

        public ParameterBag getData() {
            return data;
        }

    }
}
