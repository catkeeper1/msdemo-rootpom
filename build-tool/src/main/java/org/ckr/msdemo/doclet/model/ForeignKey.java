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

    public static final String MANY_TO_ONE_QUALIFIED_NAME = "javax.persistence.ManyToOne";

    public static final String ONE_TO_MANY_QUALIFIED_NAME = "javax.persistence.OneToMany";

    public static final String ONE_TO_ONE_QUALIFIED_NAME = "javax.persistence.OneToOne";

    public static final String JOIN_TABLE_QUALIFIED_NAME = "javax.persistence.JoinTable";

    public static final String JOIN_COLUMN_QUALIFIED_NAME = "javax.persistence.JoinColumn";

    public static final String JOIN_TABLE_NAME = "name";

    public static final String JOIN_COLUMN_NAME = "name";

    public static final String JOIN_COLUMNS_NAME = "joinColumns";

    public static final String JOIN_TYPE_ONE_TO_ONE = "ONE_TO_ONE";

    public static final String JOIN_TYPE_ONE_TO_MANY = "ONE_TO_MANY";

    public static final String JOIN_TYPE_MANY_TO_ONE = "MANY_TO_ONE";

    public static final String JOIN_TYPE_MANY_TO_MANY = "MANY_TO_MANY";

    private String joinType;

    private String sourceTableName = "";

    private String targetTableName = "";

    private List<String> sourceColumnNames = new ArrayList<>();

    private List<String> targetColumnNames = new ArrayList<>();

    public String getSourceTableName() {
        return sourceTableName;
    }

    private void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    private void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public List<String> getSourceColumnNames() {
        return sourceColumnNames;
    }

    private void setSourceColumnNames(List<String> sourceColumnNames) {
        this.sourceColumnNames = sourceColumnNames;
    }

    public List<String> getTargetColumnNames() {
        return targetColumnNames;
    }

    private void setTargetColumnNames(List<String> targetColumnNames) {
        this.targetColumnNames = targetColumnNames;
    }

    public String getJoinType() {
        return joinType;
    }

    private void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    private void addColumnName(String columnName) {

        if(JOIN_TYPE_MANY_TO_ONE.equals(this.joinType)) {
            this.sourceColumnNames.add(columnName);
        }

        if(JOIN_TYPE_ONE_TO_MANY.equals(this.joinType) ||
           JOIN_TYPE_ONE_TO_ONE.equals(this.joinType)) {

            this.targetColumnNames.add(columnName);
        }


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
                    .annotation(MANY_TO_ONE_QUALIFIED_NAME,
                            (dataObject, annotation) -> dataObject.setJoinType(JOIN_TYPE_MANY_TO_ONE))
                    .parent()
                    .annotation(ONE_TO_MANY_QUALIFIED_NAME,
                            (dataObject, annotation) -> dataObject.setJoinType(JOIN_TYPE_ONE_TO_MANY))
                    .parent()
                    .annotation(ONE_TO_ONE_QUALIFIED_NAME,
                            (dataObject, annotation) -> dataObject.setJoinType(JOIN_TYPE_ONE_TO_ONE))
                    .parent()
                    .annotation(JOIN_COLUMN_QUALIFIED_NAME)
                        .attribute(JOIN_COLUMN_NAME,
                                (data, annotationValue) ->
                                        data.addColumnName((String) annotationValue.value()))
                    .parent()
                    .annotation(JOIN_TABLE_QUALIFIED_NAME)
                        .attribute(JOIN_TABLE_NAME,
                            (data, annotationValue) ->
                                    data.addColumnName((String) annotationValue.value()))
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
        sb.append(", sourceTableName='").append(sourceTableName).append('\'');
        sb.append(", targetTableName='").append(targetTableName).append('\'');
        sb.append(", sourceColumnNames=").append(sourceColumnNames);
        sb.append(", targetColumnNames=").append(targetColumnNames);
        sb.append('}');
        return sb.toString();
    }
}
