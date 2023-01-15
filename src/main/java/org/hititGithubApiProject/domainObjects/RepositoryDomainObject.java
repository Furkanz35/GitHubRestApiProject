package org.hititGithubApiProject.domainObjects;


public class RepositoryDomainObject {
    private final String name;

    private final String description;
    private final String url;
    private final int forks_count;
    public RepositoryDomainObject(String name, String description, String url, int forks_count) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.forks_count = forks_count;
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
        return "Repository name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", forks_count=" + forks_count;
    }
}
