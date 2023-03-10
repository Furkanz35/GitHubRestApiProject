package org.hititGithubApiProject.urls;

import org.apache.http.client.utils.URIBuilder;
import org.hititGithubApiProject.entites.Contributor;
import org.hititGithubApiProject.entites.Repository;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class UrlsUtil {
    private UrlsUtil(){}

    public static final String AUTHORIZATION_API_KEY = "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";
    public static final String REPO_BASE_URL = "https://api.github.com/orgs/";
    public static final String REPO_SUB_URL = "/repos";

    private static  URIBuilder ub = null;

    public static String getUrlForRepository(String organization){
        return UrlsUtil.REPO_BASE_URL + organization + UrlsUtil.REPO_SUB_URL;
    }

    public static List<String> getReposContributorsUrls(List<Repository> repositoryList){
        List<String> reposContributorsUrls = new ArrayList<>(repositoryList.size());
        for(Repository repository : repositoryList) {
            String contributorUrl = repository.getContributors_url();
            try {
                ub = new URIBuilder(contributorUrl).addParameter("per_page", String.valueOf(100));
            } catch (URISyntaxException e) {
                System.out.println("Illegal URL information!");
                e.printStackTrace();
            }
            reposContributorsUrls.add(ub.toString());
        }

        return reposContributorsUrls;
    }

    public static List<String> getUsersUrlFromContributors(List<Contributor> contributorList) {
        List<String> usersUrl = new ArrayList<>(contributorList.size());
        for (Contributor contributor : contributorList) {
            usersUrl.add(contributor.getUrl());
        }
        return usersUrl;
    }

    public static String foundRepoNameFromRepoContributorUrl(String url){

        int lastIndex = url.lastIndexOf("/");
        StringBuilder str = new StringBuilder(url.substring(0, lastIndex));
        lastIndex = str.lastIndexOf("/");
        return str.substring(lastIndex+1);



    }


}
