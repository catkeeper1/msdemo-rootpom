package org.ckr.msdemo.doclet.writter;

import org.ckr.msdemo.doclet.model.DataModel;
import org.ckr.msdemo.doclet.model.Table;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2017/6/20.
 */
public class LiquibaseWritter {

    private File baseDir;

    private DataModel dataModel;

    public LiquibaseWritter(String baseDirPath, DataModel dataModel) {
        createBaseDir(baseDirPath);
        this.dataModel = dataModel;
    }

    protected void createBaseDir(String baseDirPath) {
        File dir = new File(baseDirPath);

        if(!dir.isDirectory()) {
            throw new RuntimeException(dir.getAbsolutePath() + " is not a valid dir.");
        }

        baseDir = dir;
    }

    protected File createDocFile(Table table) {
        File result = new File(baseDir, table.getPackageName().replace(".", "/"));

        result.mkdir();

        result = new File(result, "db.changelog.create_" + table.getTableName() + ".xml");

        if(!result.exists()) {
            try {
                result.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("create not create new file " + result.getAbsolutePath(), e);
            }
        }
        return result;
    }

    public void generateXmlConfigDoc() {
        for(Table table : dataModel.getTableList()){

            File docFile = createDocFile(table);

            FileWriter docWriter = null;
            try {
                docWriter = new FileWriter(docFile);
            } catch (IOException ioExp) {
                throw new RuntimeException(ioExp);
            } finally {
                if(docWriter != null) {
                    try {
                        docWriter.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }

    }

    private void writeDoc(Table table, OutputStreamWriter writter) throws IOException{
        //write header
        writter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\r\n");
        writter.write("<databaseChangeLog" + "\r\n");
        writter.write("        xmlns=\"http://www.liquibase.org/xml/ns/dbchangelog\"" + "\r\n");
        writter.write("        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + "\r\n");
        writter.write("        xsi:schemaLocation=\"http://www.liquibase.org/xml/ns/dbchangelog" + "\r\n");
        writter.write("         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd\">" + "\r\n");


    }

}
