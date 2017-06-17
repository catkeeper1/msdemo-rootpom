package org.ckr.msdemo.doclet;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ProgramElementDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */
public class AnnotationScanTemplate<T> {

    private ProgramElementDoc programElementDoc;

    private T dataObject;

    private List<AnnotationHandler<T>> annotationHandlerList = new ArrayList();



    public AnnotationScanTemplate(ProgramElementDoc programElementDoc, T dataObject) {
        this.programElementDoc = programElementDoc;
        this.dataObject = dataObject;
    }

    public AnnotationScanTemplate<T> addAnnotationHandler(AnnotationHandler<T> handler) {
        annotationHandlerList.add(handler);
        return this;
    }

    public AnnotationHandler<T> annotation(String qualifeidName) {
        BasicAnnotationHandler<T> handler = new BasicAnnotationHandler<T>(qualifeidName);
        addAnnotationHandler(handler);
        return handler;
    }




    public void scaneAnnotation() {

        AnnotationDesc[] anntations = programElementDoc.annotations();

        if (anntations == null) {
            return;
        }

        List<AnnotationDesc> result = new ArrayList<AnnotationDesc>();

        for (AnnotationDesc annotation : anntations) {

            callAnnotationHandlers(annotation);

        }


    }

    private void callAnnotationHandlers(AnnotationDesc annotation) {
        for (AnnotationHandler<T> handler : this.annotationHandlerList) {
            if (!handler.supported(annotation)) {
                continue;
            }
            handler.handle(this.dataObject, annotation);
        }

    }


    public interface AnnotationHandler<T> {
        public boolean supported(AnnotationDesc annotation);
        void handle(T dataObject, AnnotationDesc annotation);
        void setParent(AnnotationScanTemplate parent);
        AnnotationScanTemplate parent();

        AnnotationHandler<T> attribute(String attributeName, SetDataFunction<T> setDataFunction) ;
    }

    public static class BasicAnnotationHandler<T> implements AnnotationHandler<T> {

        private String annotationQualifiedName;

        private List<AnnotationAttributeHandler<T>> annotationAttributeHandlerList = new ArrayList();

        private AnnotationScanTemplate parent;

        public BasicAnnotationHandler(String annotationQualifiedName) {
            this.annotationQualifiedName = annotationQualifiedName;
        }

        public BasicAnnotationHandler<T> addAnnotationAttributeHandler(AnnotationAttributeHandler<T> handler) {

            handler.setParent(this);
            annotationAttributeHandlerList.add(handler);
            return this;
        }

        @Override
        public boolean supported(AnnotationDesc annotation) {
            return this.annotationQualifiedName.equals(annotation.annotationType().qualifiedName());
        }

        @Override
        public void handle(T dataObject, AnnotationDesc annotation) {

            preHandle(dataObject, annotation);

            if (annotation.elementValues() == null) {
                return;
            }

            for (AnnotationDesc.ElementValuePair valuePair : annotation.elementValues()) {

                callAnnotationAttributeHandlers(dataObject, annotation, valuePair);

            }

        }

        @Override
        public void setParent(AnnotationScanTemplate parent) {
            this.parent = parent;
        }

        @Override
        public AnnotationScanTemplate parent() {
            return this.parent;
        }

        @Override
        public AnnotationHandler<T> attribute(String attributeName, SetDataFunction<T> setDataFunction) {

            AnnotationAttributeHandler<T> handler = new BasicAnnotationAttributaHandler<T>(attributeName,
                                                                                           setDataFunction);
            addAnnotationAttributeHandler(handler);
            return this;

        }

        protected void preHandle(T dataObject, AnnotationDesc annotation) {

        }

        private void callAnnotationAttributeHandlers(T dataObject,
                                                     AnnotationDesc annotation,
                                                     AnnotationDesc.ElementValuePair valuePair) {
            for (AnnotationAttributeHandler<T> handler : this.annotationAttributeHandlerList) {

                if (!handler.supported(annotation, valuePair)) {
                    continue;
                }

                handler.handle(dataObject, annotation, valuePair);
            }

        }
    }

    public interface AnnotationAttributeHandler<T> {

        boolean supported(AnnotationDesc annotation, AnnotationDesc.ElementValuePair valuePair);

        void handle(T dataObject,
                           AnnotationDesc annotation,
                           AnnotationDesc.ElementValuePair valuePair);

        void setParent(AnnotationHandler<T> parent);

        AnnotationHandler<T> parent();

    }

    public static class BasicAnnotationAttributaHandler<T> implements AnnotationAttributeHandler<T> {

        private String attributeName;

        private AnnotationHandler<T> parent;

        private SetDataFunction<T> setDataFunction;

        public BasicAnnotationAttributaHandler(String attributeName, SetDataFunction<T> setDataFunction) {

            this.attributeName = attributeName;
            this.setDataFunction = setDataFunction;
        }

        @Override
        public boolean supported(AnnotationDesc annotation, AnnotationDesc.ElementValuePair valuePair) {
            return this.attributeName.equals(valuePair.element().name());
        }

        @Override
        public void handle(T dataObject, AnnotationDesc annotation, AnnotationDesc.ElementValuePair valuePair) {
            setDataFunction.setData(dataObject, valuePair.value());
        }

        @Override
        public void setParent(AnnotationHandler<T> parent) {
            this.parent = parent;
        }

        @Override
        public AnnotationHandler<T> parent() {
            return this.parent;
        }

    }

    public interface SetDataFunction<T> {
        void setData(T dataObject, AnnotationValue annotationValue);
    }
}


