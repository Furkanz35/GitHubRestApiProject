package org.hititGithubApiProject.factories;

import org.hititGithubApiProject.RepositoryManager.GitHubRepositoryManager;
import org.hititGithubApiProject.RepositoryManager.RepositoryManager;

public class RepositoryManagerFactory {
    private static final RepositoryManager REPOSITORY_MANAGER_INSTANCE;

    static
    {
        REPOSITORY_MANAGER_INSTANCE = new GitHubRepositoryManager();
    }

    public static RepositoryManager getRepositoryManagerInstance(){
        return REPOSITORY_MANAGER_INSTANCE;
    }

}
