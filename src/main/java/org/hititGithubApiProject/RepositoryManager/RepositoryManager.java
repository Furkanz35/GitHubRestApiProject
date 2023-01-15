package org.hititGithubApiProject.RepositoryManager;

import org.hititGithubApiProject.domainObjects.ContributorDomainObject;
import org.hititGithubApiProject.domainObjects.RepositoryDomainObject;
import org.hititGithubApiProject.entites.Contributor;
import org.hititGithubApiProject.entites.Repository;

import java.util.List;

public interface RepositoryManager {

    List<RepositoryDomainObject> findMostForkedRepositories(String organizationName, int numberOfMostForkedRepositories);

    List<ContributorDomainObject> findTopContributorsOfRepositories(List<String> contributorRepoUrls, int numberOfTopContributors);

    void displayMostForkedRepositories();

    void displayTopContirbutorsOfRepositories();

    List<RepositoryDomainObject> getRepositoryDomainObjectList();
    List<ContributorDomainObject> getContributorDomainObjectList();
    List<Repository> getRepositoryList();

    List<Contributor> getContributorList();

}
