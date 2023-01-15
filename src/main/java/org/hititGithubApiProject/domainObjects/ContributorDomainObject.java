package org.hititGithubApiProject.domainObjects;

public class ContributorDomainObject {
    private final String repositoryName;
    private final String userName;
    private final int contributionQuantity;
    private final int userFollowerQuantity;

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return  "repositoryName='" + repositoryName + '\'' +
                ", userName='" + userName + '\'' +
                ", contributionQuantity=" + contributionQuantity +
                ", userFollowerQuantity=" + userFollowerQuantity +
                '}';
    }

    public int getContributionQuantity() {
        return contributionQuantity;
    }

    public int getUserFollowerQuantity() {
        return userFollowerQuantity;
    }

    public ContributorDomainObject(String repositoryName, String userName, int contributionQuantity, int userFollowerQuantity) {
        this.repositoryName = repositoryName;
        this.userName = userName;
        this.contributionQuantity = contributionQuantity;
        this.userFollowerQuantity = userFollowerQuantity;
    }
}
