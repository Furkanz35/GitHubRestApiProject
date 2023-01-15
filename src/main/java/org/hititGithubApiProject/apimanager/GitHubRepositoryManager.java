package org.hititGithubApiProject.apimanager;

import com.google.gson.Gson;
import org.hititGithubApiProject.comparators.sortByForkQuantity;
import com.google.gson.reflect.TypeToken;
import org.hititGithubApiProject.domainObjects.ContributorDomainObject;
import org.hititGithubApiProject.domainObjects.RepositoryDomainObject;
import org.hititGithubApiProject.entites.Contributor;
import org.hititGithubApiProject.entites.Repository;
import org.apache.http.client.utils.URIBuilder;
import org.hititGithubApiProject.entites.User;
import org.hititGithubApiProject.urls.UrlsUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GitHubRepositoryManager implements RepositoryAndContributorManager {

    private static final Gson gson = new Gson();
    private List<RepositoryDomainObject> repositoryDomainObjectList = new ArrayList<>();
    private List<ContributorDomainObject> contributorDomainObjectList = new ArrayList<>();
    private List<Repository> repositoryList = new ArrayList<>();
    private List<Contributor> contributorList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    private int pageCountForRepositoryUrl;

    {
        pageCountForRepositoryUrl = 1;
    }

    @Override
    public List<RepositoryDomainObject> findMostForkedRepositories(String urlForOrganizationRepos, int numberOfMostForkedRepositories) {
        HttpResponse<String> httpResponse = getHttpResponse(urlForOrganizationRepos);
        if(!httpResponseCheck(httpResponse)) {
            printHttpResponseProblemandExitTheProgram(httpResponse);
        }
        Type repoArrayListType = new TypeToken<ArrayList<Repository>>(){}.getType();
        System.out.println("1. page is in progress for Url = " + httpResponse.uri());
        repositoryList = gson.fromJson(httpResponse.body(), repoArrayListType);

        while (isThereAnotherPageForRequest(httpResponse)){
            httpResponse = getHttpResponseOfNextPage(urlForOrganizationRepos);
            repositoryList.addAll(gson.fromJson(httpResponse.body(), repoArrayListType));
        }
        repositoryList.sort(new sortByForkQuantity());
        repositoryList = selectFromAllRepositories(repositoryList, numberOfMostForkedRepositories);
        repositoryDomainObjectList = getRepositoryDomainObjectListFromRepositoryList(repositoryList);
        return repositoryDomainObjectList;
    }


    @Override
    public List<ContributorDomainObject> findTopContributorsOfRepositories(List<String> contirbutorRepoUrls, int numberOfTopContributors) {
        List<HttpResponse<String>> httpResponseForReposContributorsUrls = new ArrayList<>();
        for(int i = 0; i < contirbutorRepoUrls.size(); ++i) {
            httpResponseForReposContributorsUrls.add(i, getHttpResponse(contirbutorRepoUrls.get(i)));
            if(!httpResponseCheck(httpResponseForReposContributorsUrls.get(i))) {
                printHttpResponseProblemandExitTheProgram(httpResponseForReposContributorsUrls.get(i));
            }
        }
        contributorList = getContributorsFromResponse(httpResponseForReposContributorsUrls);
        contributorList = selectFromAllContributors(contributorList, numberOfTopContributors);
        return getContributorDomainObjectListFromContributorList(contributorList);
    }

    @Override
    public void displayMostForkedRepositories() {
        if(null != repositoryDomainObjectList) {
            System.out.println("-----Most Forked Repositories of Organization ----------");
            for (RepositoryDomainObject repositoryDomainObject : repositoryDomainObjectList) {
                System.out.println(repositoryDomainObject.toString());
            }
        }
    }

    @Override
    public void displayTopContirbutorsOfRepositories() {
        if(null != contributorDomainObjectList) {
            System.out.println("-----Top Contirbutors of these Repositories----------");
            for (ContributorDomainObject contributorDomainObject : contributorDomainObjectList) {
                System.out.println(contributorDomainObject.toString());
            }
        }
    }

    @Override
    public List<RepositoryDomainObject> getRepositoryDomainObjectList() {
        return repositoryDomainObjectList;
    }

    @Override
    public List<ContributorDomainObject> getContributorDomainObjectList() {
        return contributorDomainObjectList;
    }

    @Override
    public List<Repository> getRepositoryList() {
        return repositoryList;
    }

    @Override
    public List<Contributor> getContributorList() {
        return contributorList;
    }

    private HttpResponse<String> getHttpResponse(final String URL) {
        HttpRequest getRequestForRepositories = null;
        try {
            getRequestForRepositories = HttpRequest.newBuilder()
                    .uri(new URI(URL))
                    .header("accept", "application/json")
                    .header("Authorization","Bearer " + UrlsUtil.AUTHORIZATION_API_KEY)
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            System.out.println("Illegal URL information!");
            e.printStackTrace();
        }

        HttpResponse<String> httpResponseForRepositories = null;

        try {
            httpResponseForRepositories = httpClient.send(getRequestForRepositories, HttpResponse.BodyHandlers.ofString());
            System.out.println(httpResponseForRepositories.uri().toString() + " was requested");
        } catch (IOException e) {
            System.out.println("Error was occurred during sending or receiving operations!");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Operation is interrupted!");
            e.printStackTrace();
        }

        return httpResponseForRepositories;
    }
    private void printHttpResponseProblemandExitTheProgram(HttpResponse<String> response){
        System.err.println("HttpResponse for " + response.uri() + " --> " + response.statusCode());
        System.err.println("Program could not establish suitable connection, please try again with proper Organization name");
        System.exit(1);
    }
    private HttpResponse<String> getHttpResponseOfNextPage(final String URL) {
        URIBuilder ub = null;
        try {
            ub = new URIBuilder(URL)
                    .addParameter("page", String.valueOf(pageCountForRepositoryUrl))
                    .addParameter("per_page", String.valueOf(100));
        } catch (URISyntaxException e) {
            System.out.println("Illegal URL information!");
            e.printStackTrace();
        }
        assert ub != null;
        System.out.println(pageCountForRepositoryUrl + ". page is in progress for Url = " + ub);
        return getHttpResponse(ub.toString());
    }

    private List<ContributorDomainObject> getContributorDomainObjectListFromContributorList(List<Contributor> contributorList){
        List<String> usersUrl = UrlsUtil.getUsersUrlFromContributors(contributorList);
        userList = getUsersFromUrl(usersUrl);
        contributorDomainObjectList = new ArrayList<>();
        for(int i = 0; i < contributorList.size(); ++i) {
            contributorDomainObjectList.add(
                    new ContributorDomainObject(
                            contributorList.get(i).getRepository(),
                            contributorList.get(i).getLogin(), contributorList.get(i).getContributions(),
                            userList.get(i).getFollowers())
            );
        }
        return contributorDomainObjectList;
    }

    private List<RepositoryDomainObject> getRepositoryDomainObjectListFromRepositoryList(List<Repository> repositoryList){
        repositoryDomainObjectList = new ArrayList<>();
        for (Repository repository : repositoryList) {
            repositoryDomainObjectList.add(new RepositoryDomainObject(
                    repository.getName(),
                    repository.getDescription(),
                    repository.getUrl(),
                    repository.getForks_count()
            ));
        }
        return repositoryDomainObjectList;
    }



    private boolean httpResponseCheck(HttpResponse<String> httpResponse) {
        if (httpResponse.statusCode() == 404) {
            System.out.println("Entered url is invalid. Please try with proper url");
            return false;
        }
        else if(httpResponse.statusCode() != 200) {
            System.out.println("Http Response for the related request is not suitable try again later");
            return false;
        }
        return true;
    }

    private boolean isThereAnotherPageForRequest(HttpResponse<String> httpResponse) {
        HttpHeaders responseHeader = httpResponse.headers();
        Map<String , List<String>> headerMap = responseHeader.map();
        try {
            List<String> linkList = headerMap.get("link");
            ++pageCountForRepositoryUrl;
            return linkList.get(0).contains("rel=\"next\"");
        } catch (NullPointerException e) {
            System.out.println("There are no other pages for this organization repos.");
            return false;
        }
    }
    private List<Contributor> selectFromAllContributors(List<Contributor> contributorList,  int numberOfTopContributors){
        List<Contributor> selectedContributors = new ArrayList<>();
        int count = 1;
        for(int i = 0; i < contributorList.size() - 1; ++i) {
            if(!(contributorList.get(i).getRepository().equals(contributorList.get(i + 1).getRepository()))) {
                count = 0;
            }
            if(count < numberOfTopContributors){
                selectedContributors.add(contributorList.get(i));
                ++count;
            }
        }
        if(count != 0 || count < numberOfTopContributors)
            selectedContributors.add(contributorList.get(contributorList.size() - 1));

        return selectedContributors;
    }

    private List<Repository> selectFromAllRepositories(List<Repository> repositoryList, int numberOfMostForkedRepositories) {
        if(repositoryList.size() >= numberOfMostForkedRepositories)
            repositoryList = repositoryList.subList(0, numberOfMostForkedRepositories);
        else {
            System.out.println("Organization include less Repository Count from entered Most Forked Repository count," +
                    " all of the repositories of related organization will be listed");
        }
        return repositoryList;
    }


    private List<User> getUsersFromUrl(List<String> userUrls) {

        List<HttpResponse<String>> httpResponseForUrls = new ArrayList<>();
        for(int i = 0; i < userUrls.size(); ++i) {
            httpResponseForUrls.add(getHttpResponse(userUrls.get(i)));
            if(!httpResponseCheck((httpResponseForUrls.get(i)))){
                printHttpResponseProblemandExitTheProgram(httpResponseForUrls.get(i));
            }
        }
        Type userArrayListType = new TypeToken<User>(){}.getType();
        userList = new ArrayList<>();
        for (HttpResponse<String> httpResponseForUrl : httpResponseForUrls) {
            userList.add(gson.fromJson(httpResponseForUrl.body(), userArrayListType));
        }
        return userList;
    }

    public List<Contributor> getContributorsFromResponse(List<HttpResponse<String>> contributorResponses) {
        Type contributorArrayListType = new TypeToken<ArrayList<Contributor>>(){}.getType();
        contributorList = new ArrayList<>();
        for (HttpResponse<String> contributorResponse : contributorResponses) {
            int sizeOfContributorList = contributorList.size();
            contributorList.addAll(gson.fromJson(contributorResponse.body(), contributorArrayListType));
            for (int j = sizeOfContributorList; j < contributorList.size(); ++j) {
                contributorList.get(j).setRepository(UrlsUtil.foundRepoNameFromRepoContributorUrl
                        (contributorResponse.uri().toString()));
            }
        }
        return contributorList;
    }

}
