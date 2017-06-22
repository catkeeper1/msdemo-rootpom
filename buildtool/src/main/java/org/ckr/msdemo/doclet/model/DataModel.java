package org.ckr.msdemo.doclet.model;

import com.sun.javadoc.ClassDoc;
import org.ckr.msdemo.doclet.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruoli.chen on 05/06/2017.
 */
public class DataModel {

    private ClassDoc[] classeDocs;

    private List<Table> tableList = new ArrayList<>();

    public DataModel(ClassDoc[] classeDocs) {
        this.classeDocs = classeDocs;

        for(int i = 0 ; i < classeDocs.length; i++) {

            Table table = Table.createEntity(classeDocs[i]);
            if(table == null) {
                continue;
            }
            tableList.add(table);
        }
    }

    public List<Table> getTableList() {
        return tableList;
    }
}
