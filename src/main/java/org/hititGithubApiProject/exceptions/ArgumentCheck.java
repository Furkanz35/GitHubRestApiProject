package org.hititGithubApiProject.exceptions;

public class ArgumentCheck{
    public static void checkForArgumentsCount(String[] args) throws InvalidArgumentCountException {
        if(args.length != 3){
            throw new InvalidArgumentCountException();
        }
    }
}