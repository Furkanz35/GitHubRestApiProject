package org.hititGithubApiProject.entites;

public class Repository {
    private String name;
    private String description;
    private String url;
    private int forks_count;

    private String contributors_url;

    public String getContributors_url() {
        return contributors_url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public int getForks_count() {
        return forks_count;
    }


    @Override
    public String toString() {
        return "Repository Name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", forks_count=" + forks_count;
    }

}
