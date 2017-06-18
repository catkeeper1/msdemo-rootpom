package org.ckr.msdemo.doclet.model;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import org.ckr.msdemo.doclet.util.AnnotationScanTemplate;
import org.ckr.msdemo.doclet.util.DocletUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */
public class Column {

    public static final String COLUMN_QUALIFIED_NAME = "javax.persistence.Column";

    public static final String COLUMN_ID_QUALIFIED_NAME = "javax.persistence.Id";

    public static final String COLUMN_NAME = "name";

    public static final String COLUMN_NULLABLE = "nullable";

    public static final String COLUMN_DEFINITION = "columnDefinition";

    public static final String COLUMN_LENGTH = "length";

    public static final String COLUMN_PRECISION = "precision";

    public static final String COLUMN_SCALE = "scale";


    private String name = null;

    private Boolean nullable = true;

    private Boolean isPrimaryKey = false;

    private String columnDefinition;

    private Integer length;

    private Integer precision;

    private Integer scale;

    private String javaFieldName;

    private String javaFieldType;

    private String comment;

    public String getComment() {
        return comment;
    }

    private void setComment(String comment) {
        this.comment = comment;
    }

    public String getJavaFieldName() {
        return javaFieldName;
    }

    private void setJavaFieldName(String javaFieldName) {
        this.javaFieldName = javaFieldName;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Boolean getNullable() {
        return nullable;
    }

    private void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    private void setIsPrimaryKey(Boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public String getColumnDefinition() {
        return columnDefinition;
    }

    private void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    public Integer getLength() {
        return length;
    }

    private void setLength(Integer length) {
        this.length = length;
    }

    public Integer getPrecision() {
        return precision;
    }

    private void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public Integer getScale() {
        return scale;
    }

    private void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getJavaFieldType() {
        return javaFieldType;
    }

    private void setJavaFieldType(String javaFieldType) {
        this.javaFieldType = javaFieldType;
    }

    private Column() {

    }

    public static List<Column> createColumns(ClassDoc classDoc) {

        List<Column> result = new ArrayList();

        MethodDoc[] methods = classDoc.methods();

        if(methods == null) {
            return result;
        }

        for(MethodDoc method : methods) {

            Column column = new Column();


            new AnnotationScanTemplate<Column>(method, column)
                .annotation(COLUMN_QUALIFIED_NAME)
                    .attribute(COLUMN_NAME, (data, annotationValue) -> data.setName((String)annotationValue.value()))
                    .attribute(COLUMN_NULLABLE,
                            (data, annotationValue) -> data.setNullable((Boolean)annotationValue.value()))
                    .attribute(COLUMN_DEFINITION,
                            (data, annotationValue) -> data.setColumnDefinition((String)annotationValue.value()))
                    .attribute(COLUMN_LENGTH,
                            (data, annotationValue) -> data.setLength((Integer) annotationValue.value()))
                    .attribute(COLUMN_PRECISION, (data, annotationValue) ->
                            data.setPrecision((Integer) annotationValue.value()))
                    .attribute(COLUMN_SCALE, (data, annotationValue) ->
                            data.setScale((Integer) annotationValue.value()))
                    .parent()
                .annotation(COLUMN_ID_QUALIFIED_NAME, (data, annotationValue) -> data.setIsPrimaryKey(true)).parent()
                .scaneProgramElement();


            if(column.getName() == null) {
                continue;
            }

            column.setJavaFieldName(DocletUtil.getMethodName(method));
            column.setJavaFieldType(DocletUtil.getFieldTypeName(method));

            column.setComment(method.commentText());
            result.add(column);
        }

        return result;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", nullable=" + nullable +
                ", isPrimaryKey=" + isPrimaryKey +
                ", columnDefinition='" + columnDefinition + '\'' +
                ", length=" + length +
                ", precision=" + precision +
                ", scale=" + scale +
                ", javaFieldName='" + javaFieldName + '\'' +
                ", javaFieldType=" + javaFieldType +
                ", comment=" + comment +
                '}';
    }
}
