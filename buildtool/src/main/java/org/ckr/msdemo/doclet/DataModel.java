package org.ckr.msdemo.doclet;

import com.sun.javadoc.ClassDoc;

/**
 * Created by ruoli.chen on 05/06/2017.
 */
public class DataModel {

    private ClassDoc[] classeDocs;

    public DataModel(ClassDoc[] classeDocs) {
        this.classeDocs = classeDocs;

        for(int i = 0 ; i < classeDocs.length; i++) {
            Entity.createEntity(classeDocs[i]);
        }
    }


}
