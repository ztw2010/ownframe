package com.ebest.frame.compilelib.route;

import com.ebest.frame.annomationlib.router.annomation.RouteConfig;
import com.ebest.frame.annomationlib.router.annomation.RouterRule;
import com.ebest.frame.compilelib.UtilMgr;
import com.ebest.frame.compilelib.route.exception.RouterException;
import com.ebest.frame.compilelib.route.factory.RuleFactory;
import com.ebest.frame.compilelib.route.model.BasicConfigurations;
import com.ebest.frame.compilelib.route.model.Parser;
import com.ebest.frame.compilelib.route.model.RouteRuleConfig;
import com.ebest.frame.compilelib.route.util.Utils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * The entry class of annotation processor
 */
@AutoService(Processor.class)
public class Compiler extends AbstractProcessor{
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            BasicConfigurations config = processRouteConfig(roundEnv);
            processRouteRules(roundEnv, config);
            return false;
        } catch (RouterException e) {
            e.printStackTrace();
            error(e.getElement(),e.getMessage());
            return true;
        }
    }

    /**
     * Parse the {@link RouteConfig} and create a {@link BasicConfigurations} to be used.
     * @param roundEnv data sources
     * @return The instance of {@link BasicConfigurations}
     * @throws RouterException pack all of the exception when a error occurs.
     */
    private BasicConfigurations processRouteConfig(RoundEnvironment roundEnv) throws RouterException{
        TypeElement type = null;
        try {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RouteConfig.class);
            Iterator<? extends Element> iterator = elements.iterator();
            BasicConfigurations configurations = null;
            while (iterator.hasNext()) {
                type = (TypeElement) iterator.next();
                if (configurations != null) {
                    throw new RouterException("The RouteConfig in this module was defined duplicated!",type);
                }
//                if (!Utils.isSuperClass(type,Constants.CLASSNAME_APPLICATION)) {
//                    throw new RouterException("The class you are annotated by RouteConfig must be a Application",type);
//                }
                if (!Utils.isSuperClass(type,Constants.CLASSNAME_MODULEROUTECONFIG)) {
                    throw new RouterException("The class you are annotated by RouteConfig must be a ModuleRouteConfig",type);
                }
                RouteConfig config = type.getAnnotation(RouteConfig.class);
                configurations = new BasicConfigurations(config);
            }
            return configurations == null ? new BasicConfigurations(null) : configurations;
        } catch (RouterException e) {
            throw e;
        } catch (Throwable e) {
            throw new RouterException(e.getMessage(),e,type);
        }
    }

    /**
     * Parse all of the elements that annotated by {@link RouterRule}. and combines the {@link BasicConfigurations} to create new java file.
     * @param roundEnv The data sources
     * @param config The instance of {@link BasicConfigurations} that be parsed by {@link Compiler#processRouteConfig(RoundEnvironment)}
     * @throws RouterException pack all of the exception when a error occurs.
     */
    private void processRouteRules(RoundEnvironment roundEnv, BasicConfigurations config) throws RouterException{
        Map<String, List<Parser>> map = new HashMap<>();
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RouterRule.class);
        TypeElement type = null;
        try {
            for (Element ele : elements) {
                type = (TypeElement) ele;
                if (!Utils.checkTypeValid(type)) continue;

                RouterRule rule = type.getAnnotation(RouterRule.class);
                RouteRuleConfig ruleConfig = RouteRuleConfig.create(rule, config, type);

                Parser parser = Parser.create(type, ruleConfig);
                parser.parse();

                String packName = ruleConfig.getPack();

                if (!map.containsKey(packName)) {
                    map.put(packName,new ArrayList<Parser>());
                }
                map.get(packName).add(parser);
            }

            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                new RuleFactory(ClassName.get(key,"RouterRuleCreator"),map.get(key)).generateCode();
            }
        } catch (RouterException e) {
            throw e;
        } catch (Throwable e) {
            throw new RouterException(e.getMessage(),e,type);
        }
    }

    /**
     * compiler output method, when a error occurs. should be notice here.
     *
     * @param element Element of class who has a exception when compiled
     * @param message The message should be noticed to user
     * @param args args to inflate message
     */
    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(RouterRule.class.getCanonicalName());
        set.add(RouteConfig.class.getCanonicalName());
        return set;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        UtilMgr.getMgr().init(processingEnv);
    }
}
