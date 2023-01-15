package org.hititGithubApiProject.entites;

public class Contributor {

    private String login;
    private String url;
    private int contributions;

    private String repository;


    public String getLogin() {
        return login;
    }

    public String getRepository() {
        return repository;
    }

    public int getContributions() {
        return contributions;
    }

    public String getUrl() {
        return url;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    @Override
    public String toString() {
        return  "login='" + login + '\'' +
                ", url='" + url + '\'' +
                ", contributionQuantity=" + contributions +
                ", repo=" + repository +
                '}';
    }
}
