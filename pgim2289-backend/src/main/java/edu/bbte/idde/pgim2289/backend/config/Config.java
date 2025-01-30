package edu.bbte.idde.pgim2289.backend.config;

public class Config {
    private String daoType;
    private String jdbcUrl;
    private String jdbcDriver;
    private String jdbcUser;
    private String jdbcPassword;
    private int jdbcPoolSize;
    private Long limit;

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
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
