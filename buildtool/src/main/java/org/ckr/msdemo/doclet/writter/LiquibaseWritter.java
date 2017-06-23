package org.ckr.msdemo.doclet.writter;

import org.ckr.msdemo.doclet.model.DataModel;
import org.ckr.msdemo.doclet.model.Table;
import org.ckr.msdemo.doclet.util.DocletUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.ckr.msdemo.doclet.util.DocletUtil.indent;
import static org.ckr.msdemo.doclet.util.DocletUtil.ENTER;

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

        dir = new File(dir, "liquibaseXml");

        if(!dir.exists()) {
            if(!dir.mkdir()) {
                throw new RuntimeException("cannot create directory:" + dir.getAbsolutePath());
            }
        }

        this.baseDir = dir;
    }

    private File createDocFile(Table table) {


        File result = DocletUtil.createDirectory(this.baseDir,
                                                 table.getPackageName().replace(".", "/"));


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
                this.writeDoc(table, docWriter);
                docWriter.flush();
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
        writter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + ENTER);
        writter.write("<databaseChangeLog" + ENTER);
        writter.write("        xmlns=\"http://www.liquibase.org/xml/ns/dbchangelog\"" + ENTER);
        writter.write("        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + ENTER);
        writter.write("        xsi:schemaLocation=\"http://www.liquibase.org/xml/ns/dbchangelog" + ENTER);
        writter.write("         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd\">" + ENTER);
        writter.write(indent(1) + "<changeSet author=\"liquibase-docs\" id=\"createTable-"+
                table.getPackageName() + "." + table.getTableName()+"\">"  + ENTER);
        writter.write(ENTER);

        writeTableContent(table, writter);


        writter.write(ENTER);
        writter.write(indent(1) + "</changeSet>" + ENTER);


        //the end
        writter.write("</databaseChangeLog>");
    }

    private void writeTableContent(Table table, OutputStreamWriter writter) throws IOException{
        writter.write(indent(2) + "<createTable tableName=\"" +
                table.getTableName() + "\">" + ENTER);



        writter.write(indent(2) + "</createTable>" + ENTER);

    }


}
