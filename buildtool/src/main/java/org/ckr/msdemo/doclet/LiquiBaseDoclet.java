package org.ckr.msdemo.doclet;

import com.sun.javadoc.RootDoc;
import org.ckr.msdemo.doclet.model.DataModel;
import org.ckr.msdemo.doclet.util.DocletUtil;
import org.ckr.msdemo.doclet.writter.LiquibaseWritter;

/**
 * Created by Administrator on 2017/6/1.
 */
public class LiquiBaseDoclet {

    public static boolean start(RootDoc root) {


        DataModel dataModel = new DataModel(root.classes());

        LiquibaseWritter writter = new LiquibaseWritter(DocletUtil.getOutputDirPath(), dataModel);

        writter.generateDdlXmlConfigDoc();
        writter.generateInsertXmlConfigDoc();
        writter.generateInsertCsvTemplate();
        writter.generateIncludeXmlConfig();

        return true;
    }
}
