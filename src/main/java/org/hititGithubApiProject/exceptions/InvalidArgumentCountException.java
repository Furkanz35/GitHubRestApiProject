package org.hititGithubApiProject.exceptions;

public class InvalidArgumentCountException extends Exception{
    public InvalidArgumentCountException() {
        System.err.println("""
                User should enter valid arguments\s
                First parameter must be Organization name\s
                Second parameter must be number of most forked repositories\s
                Third parameter must be number of top contributors""");
    }
}
