package org.hititGithubApiProject.writer;

import org.hititGithubApiProject.domainObjects.ContributorDomainObject;
import org.hititGithubApiProject.domainObjects.RepositoryDomainObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVWriter;

public class CsvWriter implements Writer{

    private CSVWriter writer;

    @Override
    public void writeRepositoryDomainObjects(List<RepositoryDomainObject> repositoryDomainObjectList, String organizationName) {
        String fileName = organizationName + "_repos.csv";
        List<String[]> dataRepositoryCsv = new ArrayList<>();
        dataRepositoryCsv.add(new String[]{"Repository Name", "Fork Count", "Url", "Description"});
        for(RepositoryDomainObject repositoryDomainObject : repositoryDomainObjectList){
            dataRepositoryCsv.add( new String[]{
                    repositoryDomainObject.getName(),
                    Integer.toString(repositoryDomainObject.getForks_count()),
                    repositoryDomainObject.getUrl(),
                    repositoryDomainObject.getDescription()
                    });
        }

        try {
            writer = new CSVWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert writer != null;
        writer.writeAll(dataRepositoryCsv);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void writeContributorDomainObjects(List<ContributorDomainObject> contributorDomainObjectList, String organizationName) {
        String fileName = organizationName + "_users.csv";
        List<String[]> dataUsersCsv = new ArrayList<>();
        dataUsersCsv.add(new String[]{"Repository Name", "Username", "Contributors", "Followers"});
        for(ContributorDomainObject contributorDomainObject : contributorDomainObjectList){
            dataUsersCsv.add( new String[]{
                    contributorDomainObject.getRepositoryName(),
                    contributorDomainObject.getUserName(),
                    Integer.toString(contributorDomainObject.getContributionQuantity()),
                    Integer.toString(contributorDomainObject.getUserFollowerQuantity())
            });
        }

        try {
            writer = new CSVWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert writer != null;
        writer.writeAll(dataUsersCsv);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
