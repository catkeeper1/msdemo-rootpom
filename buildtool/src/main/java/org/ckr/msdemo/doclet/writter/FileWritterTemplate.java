package org.ckr.msdemo.doclet.writter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2017/7/2.
 */
public abstract class FileWritterTemplate {

    private File file = null;

    public FileWritterTemplate(File file) {
        this.file = file;
    }

    protected abstract void doWrite(OutputStreamWriter writer) throws IOException;

    public void execute() {

        FileWriter docWriter = null;

        try {
            docWriter = new FileWriter(file);
            this.doWrite(docWriter);
            docWriter.flush();
        } catch (IOException ioExp) {
            throw new RuntimeException(ioExp);
        } finally {
            if (docWriter != null) {
                try {
                    docWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


}
