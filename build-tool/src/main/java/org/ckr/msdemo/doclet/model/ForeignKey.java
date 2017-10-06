package org.ckr.msdemo.doclet.model;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import org.ckr.msdemo.doclet.util.AnnotationScanTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/6.
 */
public class ForeignKey {

    public static final String MANY_TO_MANY_QUALIFIED_NAME = "javax.persistence.ManyToMany";

    public static final String JOIN_TABLE_QUALIFIED_NAME = "javax.persistence.JoinTable";

    public static final String JOIN_TABLE_NAME = "name";

    public static final String JOIN_COLUMNS_NAME = "joinColumns";

    public static final String JOIN_TYPE_ONE_TO_ONE = "ONE_TO_ONE";

    public static final String JOIN_TYPE_ONE_TO_MANY = "ONE_TO_MANY";

    public static final String JOIN_TYPE_MANY_TO_ONE = "MANY_TO_ONE";

    public static final String JOIN_TYPE_MANY_TO_MANY = "MANY_TO_MANY";

    private String joinType;

    private String joinTableName;

    private String joinColumnNames;

    private String inverseJoinColumnNames;

    private void setJoinTableName(String joinTableName) {
        this.joinTableName = joinTableName;
    }

    public String getJoinTableName() {
        return joinTableName;
    }

    public String getJoinColumnNames() {
        return joinColumnNames;
    }

    private void setJoinColumnNames(String joinColumnNames) {
        this.joinColumnNames = joinColumnNames;
    }

    public String getInverseJoinColumnNames() {
        return inverseJoinColumnNames;
    }

    private void setInverseJoinColumnNames(String inverseJoinColumnNames) {
        this.inverseJoinColumnNames = inverseJoinColumnNames;
    }

    public String getJoinType() {
        return joinType;
    }

    private void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    private ForeignKey() {
    }

    public static List<ForeignKey> createForeignKeys(ClassDoc classDoc) {

        List<ForeignKey> result = new ArrayList();

        MethodDoc[] methods = classDoc.methods();

        if (methods == null) {
            return result;
        }

        for (MethodDoc method : methods) {

            ForeignKey foreignKey = new ForeignKey();


            new AnnotationScanTemplate<ForeignKey>(method, foreignKey)
                    .annotation(MANY_TO_MANY_QUALIFIED_NAME,
                            (dataObject, annotation) -> dataObject.setJoinType(JOIN_TYPE_MANY_TO_MANY))
                    .parent()
                    .annotation(JOIN_TABLE_QUALIFIED_NAME)
                    .parent()
                    .scanProgramElement();
//                    .attribute(JOIN_TABLE_NAME,
//                               (data, annotationValue) -> data.setJoinTableName((String) annotationValue.value()))
//                    .attribute(JOIN_COLUMNS_NAME,
//                            (data, annotationValue) -> data.setNullable((Boolean) annotationValue.value()))
//                    .attribute(COLUMN_DEFINITION,
//                            (data, annotationValue) -> data.setColumnDefinition((String) annotationValue.value()))
//                    .attribute(COLUMN_LENGTH,
//                            (data, annotationValue) -> data.setLength((Integer) annotationValue.value()))
//                    .attribute(COLUMN_PRECISION, (data, annotationValue) ->
//                            data.setPrecision((Integer) annotationValue.value()))
//                    .attribute(COLUMN_SCALE, (data, annotationValue) ->
//                            data.setScale((Integer) annotationValue.value()))
//                    .parent()
//                    .annotation(COLUMN_ID_QUALIFIED_NAME, (data, annotationValue) -> data.setIsPrimaryKey(true)).parent()
//                    .scanProgramElement();


            if (foreignKey.joinType == null) {
                continue;
            }


            result.add(foreignKey);
        }

//        if (result.size() > 0 && classDoc.superclass() != null) {
//
//            List<Column> superClassResult = createColumns(classDoc.superclass());
//
//            result.addAll(superClassResult);
//        }

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ForeignKey{");
        sb.append("joinType='").append(joinType).append('\'');
        sb.append(", joinTableName='").append(joinTableName).append('\'');
        sb.append(", joinColumnNames='").append(joinColumnNames).append('\'');
        sb.append(", inverseJoinColumnNames='").append(inverseJoinColumnNames).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
