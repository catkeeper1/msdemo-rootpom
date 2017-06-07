package org.ckr.msdemo.doclet;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;

/**
 * Created by Administrator on 2017/6/1.
 */
public class LiquiBaseDoclet {
    public static boolean start(RootDoc root) {

//        System.out.println("---------------------------property-------");
//        System.out.println(System.getProperties());
//
//        ClassDoc[] classes = root.classes();
//        System.out.println("---------------------------doclet-------");
//        for(int i = 0 ; i < root.options().length; i++) {
//            String[] ops = root.options()[i];
//            System.out.println("---------------------------option-------");
//            for(int j = 0 ; j < ops.length; j++ ) {
//                System.out.println(ops[j]);
//            }
//        }
//
//
//        for (int i = 0; i < classes.length; ++i) {
//            System.out.println(classes[i]);
//
//            ClassDoc cls = classes[i];
//
//            AnnotationDesc[] annotations = cls.annotations();
//
//            for(int j = 0 ; j < annotations.length; j++) {
//                System.out.println(annotations[j]);
//
//            }
//        }

        new DataModel(root.classes());

        return true;
    }
}
