package org.ckr.msdemo.doclet;

import static org.ckr.msdemo.doclet.DocletUtil.findAnnotation;
import static org.ckr.msdemo.doclet.DocletUtil.logMsg;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.AnnotationTypeElementDoc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;

/**
 * Created by Administrator on 2017/6/7.
 */
public class Entity {


    private String tableName = null;

    public String getTableName() {
        return tableName;
    }

    private Entity() {

    }



    public static Entity createEntity(ClassDoc classDoc) {

        logMsg("try to create entity object for classDoc: " + classDoc);

        AnnotationDesc entityAnnotation = getEntityAnnotation(classDoc);
        AnnotationDesc tableAnnotation = getTableAnnotation(classDoc);

        if(entityAnnotation == null || tableAnnotation == null) {
            logMsg("there is no entity or table annotation.");
            return null;
        }

        logMsg("try to create entity object instance.");
        Entity instance = new Entity();

        AnnotationValue tableNameValue =
                DocletUtil.getAnnotationAttribute(tableAnnotation, "javax.persistence.Table.name");

        if(tableNameValue == null) {
            logMsg("table name is null");
        } else {
            logMsg("table name is " + tableNameValue.value());
            instance.tableName = (String) tableNameValue.value();
        }



        return instance;

    }

    private static void initColumns(ClassDoc classDoc, Entity instance) {
        //DocletUtil.findAnnotations(classDoc, )


    }


    private static AnnotationDesc getEntityAnnotation(ClassDoc classDoc) {

        return findAnnotation(classDoc, "javax.persistence.Entity");

    }

    private static AnnotationDesc getTableAnnotation(ClassDoc classDoc) {

        return findAnnotation(classDoc, "javax.persistence.Table");

    }



}
