package org.ckr.msdemo.doclet;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;

/**
 * Created by Administrator on 2017/6/1.
 */
public class DataDictDoclet {
    public static boolean start(RootDoc root) {
        ClassDoc[] classes = root.classes();
        System.out.println("---------------------------doclet-------");
        for (int i = 0; i < classes.length; ++i) {
            System.out.println(classes[i]);
        }
        return true;
    }
}
