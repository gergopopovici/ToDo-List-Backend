package edu.bbte.idde.pgim2289.backend.config;

public class Config {
    private String daoType;
    private String jdbcUrl;
    private String jdbcDriver;
    private String jdbcUser;
    private String jdbcPassword;
    private int jdbcPoolSize;
    private Boolean logQueries;
    private Boolean logUpdates;

    public Boolean getLogQueries() {
        return logQueries;
    }

    public void setLogQueries(Boolean logQueries) {
        this.logQueries = logQueries;
    }

    public Boolean getLogUpdates() {
        return logUpdates;
    }

    public void setLogUpdates(Boolean logUpdates) {
        this.logUpdates = logUpdates;
    }

    public String getDaoType() {
        return daoType;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public String getJdbcUser() {
        return jdbcUser;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public int getJdbcPoolSize() {
        return jdbcPoolSize;
    }

    public void setDaoType(String daoType) {
        this.daoType = daoType;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public void setJdbcUser(String jdbcUser) {
        this.jdbcUser = jdbcUser;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public void setJdbcPoolSize(int jdbcPoolSize) {
        this.jdbcPoolSize = jdbcPoolSize;
    }
}
