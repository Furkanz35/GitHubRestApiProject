package org.hititGithubApiProject.comparators;

import org.hititGithubApiProject.entites.Repository;

import java.util.Comparator;

public class sortByForkQuantity implements Comparator<Repository> {

    @Override
    public int compare(Repository o1, Repository o2) {
            return o2.getForks_count() - o1.getForks_count();
        }

}
