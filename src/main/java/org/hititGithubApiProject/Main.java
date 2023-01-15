package org.hititGithubApiProject;

import org.hititGithubApiProject.RepositoryManager.RepositoryManager;
import org.hititGithubApiProject.exceptions.ArgumentCheck;
import org.hititGithubApiProject.exceptions.InvalidArgumentCountException;
import org.hititGithubApiProject.factories.RepositoryManagerFactory;
import org.hititGithubApiProject.factories.WriterFactory;
import org.hititGithubApiProject.urls.AbstractUrlsUtil;
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

        RepositoryManager repositoryManager = RepositoryManagerFactory.getRepositoryManagerInstance();

        String repositoriesUrl = AbstractUrlsUtil.getUrlForRepository(organization);

        repositoryManager.findMostForkedRepositories(repositoriesUrl, mostForkedRepositoryCount);
        repositoryManager.findTopContributorsOfRepositories(AbstractUrlsUtil.getReposContributorsUrls(repositoryManager.getRepositoryList()), numberOfContributors);
        repositoryManager.displayMostForkedRepositories();
        repositoryManager.displayTopContirbutorsOfRepositories();

        Writer cswWriter = WriterFactory.getWriterInstance();
        cswWriter.writeRepositoryDomainObjects(repositoryManager.getRepositoryDomainObjectList(), organization);
        cswWriter.writeContributorDomainObjects(repositoryManager.getContributorDomainObjectList(), organization);
    }
}



