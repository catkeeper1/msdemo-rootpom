package org.ckr.msdemo.doclet.model;

import static org.ckr.msdemo.doclet.util.DocletUtil.findAnnotation;
import static org.ckr.msdemo.doclet.util.DocletUtil.logMsg;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import org.ckr.msdemo.doclet.util.AnnotationScanTemplate;
import org.ckr.msdemo.doclet.util.DocletUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */
public class Table {


    public static final String TABLE_QUALIFIED_NAME = "javax.persistence.Table";
    public static final String TABLE_NAME = "name";
    public static final String TABLE_INDEXS = "indexes";



    private String tableName = null;

    private String packageName = null;

    private List<Index> indexList = new ArrayList<>();

    private List<Column> columnList = new ArrayList<>();

    private String comment;

    private void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComment() {
        return comment;
    }

    private void setComment(String comment) {
        this.comment = comment;
    }

    public String getTableName() {
        return tableName;
    }



    public List<Index> getIndexList() {
        return this.indexList;
    }

    public String getPackageName() {
        return packageName;
    }

    private void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private void addIndex(Index index) {
        this.indexList.add(index);
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    private void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    private Table() {

    }



    public static Table createEntity(ClassDoc classDoc) {



        Table instance = new Table();

        new AnnotationScanTemplate<Table>(classDoc, instance)
            .annotation(TABLE_QUALIFIED_NAME)
                .attribute(TABLE_NAME, (data, annotationValue) -> data.setTableName((String)annotationValue.value()))
                .attribute(TABLE_INDEXS, (data, annotationValue) ->
                                          createIndexList(data, (AnnotationValue[])annotationValue.value()))
                .parent()
            .scaneProgramElement();


        if(instance.tableName == null) {
            return null;
        }

        instance.setPackageName(DocletUtil.getPackageName(classDoc));

        List<Column> columnList = Column.createColumns(classDoc);

        instance.setColumnList(columnList);

        instance.setComment(classDoc.commentText());

        logMsg("create entity object for classDoc: " + classDoc);
        logMsg("table: " + instance);
        return instance;

    }

    static private void createIndexList(Table dataObject, AnnotationValue[] indexAnnotationList) {

        if(indexAnnotationList == null) {
            return;
        }

        for(AnnotationValue indexAnnotation : indexAnnotationList) {
            Index index = Index.createIndex(indexAnnotation);

            if(index != null) {
                dataObject.addIndex(index);
            }
        }

    }




    @Override
    public String toString() {
        return "Table{" +
                "tableName='" + tableName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", indexList=" + indexList +
                ", columnList=" + columnList +
                ", comment=" + comment +
                '}';
    }
}
