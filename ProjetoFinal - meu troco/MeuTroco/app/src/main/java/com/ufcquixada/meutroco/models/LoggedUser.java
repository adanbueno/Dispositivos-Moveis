package com.ufcquixada.meutroco.models;

public final class LoggedUser {
    private static volatile LoggedUser instance;
    private User user;
    private String token;

    private LoggedUser() {}

    public static LoggedUser getInstance() {
        if (instance == null) {
            synchronized (LoggedUser.class) {
                if (instance == null) {
                    instance = new LoggedUser();
                }
            }
        }
        return instance;
    }

    public void setLogin(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public void setUpdateUser(User user) {
        this.user = user;
    }

    public void setLogout() {
        this.user = null;
        this.token = null;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }
}
