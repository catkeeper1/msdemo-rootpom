package org.ckr.msdemo.doclet;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.AnnotationTypeElementDoc;
import com.sun.javadoc.ClassDoc;

/**
 * Created by Administrator on 2017/6/7.
 */
public class Entity {

    private ClassDoc classDoc;

    private String tableName = null;

    public String getTableName() {
        return tableName;
    }

    private Entity(ClassDoc classDoc) {
        this.classDoc = classDoc;

        init();
    }

    private void init() {

        AnnotationDesc[] anntations = classDoc.annotations();

        for (AnnotationDesc anntation : anntations) {
            initTableName(anntation);
        }

    }

    public static Entity createEntity(ClassDoc classDoc) {

        System.out.println("classDoc " + classDoc);

        AnnotationDesc[] anntations = classDoc.annotations();

        if(null == anntations) {
            return null;
        }

        for (AnnotationDesc annotation : anntations) {

            //            System.out.println("annotation " + annotation);
//            System.out.println("annotation name " + annotation.annotationType().qualifiedName());
//
//            System.out.println("isEneity " + isEntity(annotation.annotationType()));
//
//
//            AnnotationTypeElementDoc[] elements = annotation.annotationType().elements();
//
//            for(int j = 0 ; j < elements.length ; j++) {
//                System.out.println("element " + elements[j]);
//            }

            if (isEntity(annotation.annotationType())) {

                Entity instance = new Entity(classDoc);

                return instance;
            }

        }

        return null;
    }



    private static boolean isEntity(AnnotationTypeDoc annotationTypeDoc) {

        return "javax.persistence.Entity".equals(annotationTypeDoc.qualifiedName());

    }

    private static boolean isTable(AnnotationTypeDoc annotationTypeDoc) {

        return "javax.persistence.Table".equals(annotationTypeDoc.qualifiedName());

    }

    private static boolean isTableName(AnnotationTypeElementDoc annotationTypeDoc) {
        return "javax.persistence.Table.name".equals(annotationTypeDoc.qualifiedName());
    }

    private void initTableName(AnnotationDesc anntation) {

        if(tableName != null) {
            return;
        }

        if(isTable(anntation.annotationType())) {

            for(AnnotationDesc.ElementValuePair pair : anntation.elementValues()) {

//                System.out.println("pair " + pair);
//                System.out.println("isTableName " + isTableName(pair.element()));
//                System.out.println("value " + pair.value().value());
                if(isTableName(pair.element())) {
                    this.tableName = (String) pair.value().value();
                    System.out.println("this.tableName " + this.tableName);
                    break;
                }

            }


        }


    }



}
