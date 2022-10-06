package com.city.online.api.config.usercontext;

public class UserThreadLocal {
    private static final ThreadLocal<UserContext> THREAD_LOCAL = new ThreadLocal<>();

    private UserThreadLocal() {

    }

    public static void setUserContext(UserContext userContext) {
        THREAD_LOCAL.set(userContext);
    }

    public static void unset() {
        THREAD_LOCAL.remove();
    }

    public static UserContext get() {
        return (UserContext) THREAD_LOCAL.get();
    }
}
