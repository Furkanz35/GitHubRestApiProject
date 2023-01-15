package org.hititGithubApiProject.factories;


import org.hititGithubApiProject.writer.CswWriter;
import org.hititGithubApiProject.writer.Writer;

public class WriterFactory {

    private static final Writer WRITER_INSTANCE;

    static {
        WRITER_INSTANCE = new CswWriter();
    }

    public static Writer getWriterInstance(){
        return WRITER_INSTANCE;
    }

}
