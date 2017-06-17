package org.ckr.msdemo.doclet;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */
public class Column {

    public static final String COLUMN_QUALIFIED_NAME = "javax.persistence.Column";

    public static final String COLUMN_NAME_QUALIFIED_NAME = "javax.persistence.Column.name";

    private String javaFieldName;

    private String columnName;

    private Column() {

    }

    public static List<Column> createColumns(ClassDoc classDoc) {

        List<Column> result = new ArrayList();

        List<MethodDoc> methods = DocletUtil.findMethodWithAnnotation(classDoc,
                COLUMN_QUALIFIED_NAME);

        for(MethodDoc method : methods) {

            Column column = new Column();


            AnnotationDesc annotation = DocletUtil.findAnnotation(method, COLUMN_QUALIFIED_NAME);

            if(annotation == null) {
                continue;
            }

            column.columnName = DocletUtil.getAnnotationAttributeStringValue(annotation, COLUMN_NAME_QUALIFIED_NAME);

        }

        return result;
    }
}
