package org.ckr.msdemo.doclet;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationTypeDoc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ProgramElementDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */
public class DocletUtil {

    public static void logMsg(String msg) {
        //can be replaced by other logger later.
        System.out.println(msg);
    }

    public static AnnotationDesc findAnnotation(ProgramElementDoc classDoc, String qualifiedName) {

        AnnotationDesc[] anntations = classDoc.annotations();

        if(anntations == null) {
            return null;
        }

        List<AnnotationDesc> result = new ArrayList<AnnotationDesc>();

        for(AnnotationDesc annotation : anntations) {
            if(qualifiedName.equals(annotation.annotationType().qualifiedName())) {
                return annotation;
            }
        }

        return null;
    }



    public static AnnotationValue getAnnotationAttribute(AnnotationDesc annotation, String qualifiedName) {

        if(annotation.elementValues() == null) {
            return null;
        }

        for(AnnotationDesc.ElementValuePair pair : annotation.elementValues()) {

            if(qualifiedName.equals(pair.element().qualifiedName())) {
                return pair.value();
            }

        }

        return null;
    }

    public static List<MethodDoc> findMethodWithAnnotation(ClassDoc classDoc, String qualifiedName) {

        List<MethodDoc> result = new ArrayList<MethodDoc>();

        if(classDoc.methods() == null) {
            return result;
        }


        for(MethodDoc methodDoc : classDoc.methods()) {
            AnnotationDesc columnAnnotation =
                    findAnnotation(methodDoc, "javax.persistence.Column");

            if(columnAnnotation == null) {
                continue;
            }

            result.add(methodDoc);
        }

        return result;

    }

}
