package org.hititGithubApiProject.factories;

import org.hititGithubApiProject.apimanager.GitHubRepositoryManager;
import org.hititGithubApiProject.apimanager.RepositoryAndContributorManager;

public class RepositoryAndContributorManagerFactory {
    private static final RepositoryAndContributorManager REPOSITORY_MANAGER_INSTANCE;

    static
    {
        REPOSITORY_MANAGER_INSTANCE = new GitHubRepositoryManager();
    }

    public static RepositoryAndContributorManager getRepositoryManagerInstance(){
        return REPOSITORY_MANAGER_INSTANCE;
    }

}
