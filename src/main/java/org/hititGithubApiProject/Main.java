package org.hititGithubApiProject;

import org.hititGithubApiProject.apimanager.RepositoryAndContributorManager;
import org.hititGithubApiProject.exceptions.ArgumentCheck;
import org.hititGithubApiProject.exceptions.InvalidArgumentCountException;
import org.hititGithubApiProject.factories.RepositoryAndContributorManagerFactory;
import org.hititGithubApiProject.factories.WriterFactory;
import org.hititGithubApiProject.urls.UrlsUtil;
import org.hititGithubApiProject.writer.Writer;

public class Main {
    public static void main(String[] args) {
        try {
            ArgumentCheck.checkForArgumentsCount(args);
        } catch (InvalidArgumentCountException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        String organization = args[0];
        int mostForkedRepositoryCount = Integer.parseInt(args[1]);
        int numberOfContributors = Integer.parseInt(args[2]);

        RepositoryAndContributorManager repositoryAndContributorManager = RepositoryAndContributorManagerFactory.getRepositoryManagerInstance();

        String repositoriesUrl = UrlsUtil.getUrlForRepository(organization);

        repositoryAndContributorManager.findMostForkedRepositories(repositoriesUrl, mostForkedRepositoryCount);
        repositoryAndContributorManager.findTopContributorsOfRepositories(
                UrlsUtil.getReposContributorsUrls(
                repositoryAndContributorManager.getRepositoryList()),
                numberOfContributors);
        repositoryAndContributorManager.displayMostForkedRepositories();
        repositoryAndContributorManager.displayTopContirbutorsOfRepositories();

        Writer cswWriter = WriterFactory.getWriterInstance();
        cswWriter.writeRepositoryDomainObjects(repositoryAndContributorManager.getRepositoryDomainObjectList(), organization);
        cswWriter.writeContributorDomainObjects(repositoryAndContributorManager.getContributorDomainObjectList(), organization);
    }
}



