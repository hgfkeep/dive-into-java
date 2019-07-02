package win.hgfdodo.processor;


import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("*")
public class DataProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        dataImplementsEquals(roundEnv);
        return false;
    }

    private boolean implementDataInterface(TypeElement typeElement) {
        List<? extends TypeMirror> interfaces = typeElement.getInterfaces();
        return interfaces.stream().anyMatch(theMirror -> ((TypeMirror) theMirror).toString().equals(Data.class.getName()));
    }


    private void dataImplementsEquals(RoundEnvironment roundEnv) {
        Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnv.getRootElements());
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, typeElements.toString());

        for (TypeElement typeElement : typeElements) {
            List<? extends TypeMirror> interfaces = typeElement.getInterfaces();

            if(interfaces==null){

            }
            List<ExecutableElement> methodsElements = ElementFilter.methodsIn(typeElement.getEnclosedElements());
            if (!methodsElements.stream().anyMatch(m -> m.getSimpleName().contentEquals("equals"))) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        "'equals' method must be overridden for the class implementing " +
                                "com.logicbig.example.Data. \n Error class: " + typeElement);
            }
        }

        typeElements.stream()
//                .filter(this::implementDataInterface)
                .forEach(

                        typeElement -> {
                            List<ExecutableElement> methodsElements = ElementFilter.methodsIn(typeElement.getEnclosedElements());
                            if (!methodsElements.stream().anyMatch(m -> m.getSimpleName().contentEquals("equals"))) {
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                        "'equals' method must be overridden for the class implementing " +
                                                "com.logicbig.example.Data. \n Error class: " + typeElement);
                            }
                        }
                );
    }
}
