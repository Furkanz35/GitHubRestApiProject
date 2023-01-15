package org.hititGithubApiProject.writer;

import org.hititGithubApiProject.domainObjects.ContributorDomainObject;
import org.hititGithubApiProject.domainObjects.RepositoryDomainObject;
import org.hititGithubApiProject.entites.Repository;

import java.util.List;

public interface Writer {

    void writeRepositoryDomainObjects(List<RepositoryDomainObject> repositoryDomainObjectList, String organizationName);
    void writeContributorDomainObjects(List<ContributorDomainObject> contributorDomainObjectList, String organizationName);

}
