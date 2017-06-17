package org.ckr.msdemo.doclet;

import static org.ckr.msdemo.doclet.DocletUtil.findAnnotation;
import static org.ckr.msdemo.doclet.DocletUtil.logMsg;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.AnnotationTypeElementDoc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doc;
import com.sun.javadoc.MethodDoc;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */
public class Entity {

    public static final String ENTITY_QUALIFIED_NAME = "javax.persistence.Entity";
    public static final String TABLE_QUALIFIED_NAME = "javax.persistence.Table";
    public static final String TABLE_NAME = "name";

    private String tableName = null;

    public String getTableName() {
        return tableName;
    }

    private Entity() {

    }



    public static Entity createEntity(ClassDoc classDoc) {

        logMsg("try to create entity object for classDoc: " + classDoc);

        Entity instance = new Entity();

        new AnnotationScanTemplate<Entity>(classDoc, instance)
            .annotation(TABLE_QUALIFIED_NAME)
                .attribute(TABLE_NAME, (data, annotationValue) -> data.tableName = (String)annotationValue.value())
                .parent()
            .scaneAnnotation();


        if(instance.tableName == null) {
            logMsg("table name is null");
            return null;
        } else {
            logMsg("table name is " + instance.tableName);
            return instance;
        }

    }




    private static AnnotationDesc getEntityAnnotation(ClassDoc classDoc) {

        return findAnnotation(classDoc, ENTITY_QUALIFIED_NAME);

    }

    private static AnnotationDesc getTableAnnotation(ClassDoc classDoc) {

        return findAnnotation(classDoc, TABLE_QUALIFIED_NAME);

    }



}
