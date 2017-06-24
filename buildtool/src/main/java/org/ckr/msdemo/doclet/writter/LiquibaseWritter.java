package org.ckr.msdemo.doclet.writter;

import org.ckr.msdemo.doclet.model.Column;
import org.ckr.msdemo.doclet.model.DataModel;
import org.ckr.msdemo.doclet.model.Index;
import org.ckr.msdemo.doclet.model.Table;
import org.ckr.msdemo.doclet.util.DocletUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.ckr.msdemo.doclet.util.DocletUtil.indent;
import static org.ckr.msdemo.doclet.util.DocletUtil.ENTER;
import static org.ckr.msdemo.doclet.util.DocletUtil.CHANGE_SET_END;
import static org.ckr.msdemo.doclet.util.DocletUtil.writeChangeSet;
import static org.ckr.msdemo.doclet.util.DocletUtil.getColumnType;

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
        writter.write(DocletUtil.DOC_HEADER);

        writeTableContent(table, writter);

        writePrimaryContent(table, writter);

        writeIndexContent(table, writter);
        //the end
        writter.write(DocletUtil.DOC_END);
    }

    private void writeTableContent(Table table, OutputStreamWriter writter) throws IOException{
        writeChangeSet(writter,"createTable-" + table.getPackageName() + "." + table.getTableName());
        writter.write(ENTER);

        writter.write(indent(2) + "<createTable tableName=\"" +
                table.getTableName() + "\">" + ENTER);

        for(Column column : table.getColumnList()) {
            this.writeColumnContent(column, writter);
        }

        writter.write(indent(2) + "</createTable>" + ENTER);
        writter.write(DocletUtil.CHANGE_SET_END);
    }

    private void writePrimaryContent(Table table, OutputStreamWriter writter) throws IOException{
        StringBuilder fieldNames = new StringBuilder("");
        for(Column column : table.getColumnList()) {
            if(Boolean.TRUE.equals(column.getIsPrimaryKey())) {

                if(fieldNames.length() > 0) {
                    fieldNames.append(",");
                }

                fieldNames.append(column.getName());

            }
        }

        if(fieldNames.length() == 0) {
            return;
        }

        writeChangeSet(writter,"createTablePk-" + table.getPackageName() + "." + table.getTableName());
        writter.write(ENTER);

        writter.write(indent(2) + "<addPrimaryKey " +

                "constraintName=\"" + "PK_" + table.getTableName() + "\" " +
                "columnNames=\"" + fieldNames.toString() + "\" " +
                "tableName=\"" + table.getTableName() + "\" />" + ENTER);

        writter.write(DocletUtil.CHANGE_SET_END);
    }

    private void writeIndexContent(Table table, OutputStreamWriter writter) throws IOException{

        if(table.getIndexList().isEmpty()) {
            return;
        }

        writeChangeSet(writter,"createTableIndex-" + table.getPackageName() + "." + table.getTableName());

        int noOfIndex = 0;

        for(Index index : table.getIndexList()) {

            boolean unique = false;
            if(Boolean.TRUE.equals(index.getUnique())) {
                unique = true;
            }

            String indexName = "IND_" + table.getTableName() + "_" + noOfIndex;

            if(index.getName() != null) {
                indexName = index.getName();
            }

            writter.write(indent(2) + "<createIndex " +
                    "indexName=\"" + indexName + "\" " +
                    "tableName=\"" + table.getTableName() + "\" " +
                    "unique=\"" + unique + "\">" + ENTER);

            for(Index.IndexColumn indexColumn : index.getColumnList()) {

                writter.write(indent(3) + "<column name=\"" + indexColumn.getName() + "\"/>" + ENTER);

            }

            writter.write(indent(2) + "</createIndex>" + ENTER);

            noOfIndex++;
        }
        writter.write(DocletUtil.CHANGE_SET_END);
    }

    private void writeColumnContent(Column column, OutputStreamWriter writter) throws IOException{

        writter.write(indent(3) +
                "<column name=\"" + column.getName() + "\" type=\"" + getColumnType(column) + "\"/>" + ENTER);

    }

    private String getPrimaryKeyAttribute(Column column) {
        if(Boolean.TRUE.equals(column.getIsPrimaryKey())) {
            return "primaryKey=true";
        }

        return "";
    }

}
