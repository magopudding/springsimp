package com.kxy.servlet;

import com.kxy.annotation.KXYAutowire;
import com.kxy.annotation.KXYController;
import com.kxy.annotation.KXYRequestMapping;
import com.kxy.annotation.KXYService;
import com.kxy.controller.DemoController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @Auther: kxy
 * @Date: 2019/7/22 23:34
 * @Desraption
 **/
public class KXYDispatherServlet extends HttpServlet {

    private Properties properties = new Properties();
    private List <String> classNames = new ArrayList <>();
    private Map <String, Object> ioc = new HashMap <>();
    private Map <String, Object> handleMapping = new HashMap <>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispath(req,resp);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //运行阶段
        try {
            doDispath(req,resp);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        //1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //2.扫描相关的类
        doScanner(properties.getProperty("scan_package"));

        //3.初始化ioc容器，将所有相关的类实例化保存
        doInstance();

        //4.依赖注入
        doAutoWired();

        //5.初始化handlerMapping
        initHandlerMaping();

        System.out.println("初始化完成");

    }


    private void doDispath(HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException {
        String servletPath = req.getRequestURI().replaceAll("//+","/");
        String projectName = req.getServletContext().getServletContextName();
        servletPath.replace(projectName+"//","");
        Method method = (Method) handleMapping.get("/qq/as");
        String classNameSalis = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(ioc.get(classNameSalis),req,resp);
    }

    private void initHandlerMaping() {
        ioc.forEach((String k, Object v) -> {
            Class <?> aClass = v.getClass();

            if(aClass.isAnnotationPresent(KXYController.class)){
                Method[] methods = aClass.getDeclaredMethods();
                KXYRequestMapping mapping = aClass.getAnnotation(KXYRequestMapping.class);
                String urll = "";
                if(mapping != null){
                    urll = mapping.value() == null ? "":mapping.value();
                }
                for (Method method : methods) {

                    StringBuffer url = new StringBuffer();
                    if(urll.length() > 0){
                        url.append("/"+urll);
                    }
                    if(!method.isAnnotationPresent(KXYRequestMapping.class)){continue;}
                    url.append(method.getAnnotation(KXYRequestMapping.class).value());
                    method.setAccessible(true);
                    handleMapping.put(url.toString().replaceAll("//+","/"), method);
                    System.out.println("11111111" +url.toString()+","+method);
                }
            }
        });


    }

    private void doAutoWired() {
        if (ioc.isEmpty()) {
            return;
        }
        ioc.forEach((k, v) -> {
            Field[] declaredFields = v.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (!field.isAnnotationPresent(KXYAutowire.class)) {
                    continue;
                }

                KXYAutowire annotation = field.getAnnotation(KXYAutowire.class);
                String beanName = annotation.value().trim();
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                }
                try {
                    field.setAccessible(true);
                    field.set(v, ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        try {
            for (String className : classNames) {
                Class <?> clazz = Class.forName(className);
                Annotation[] annotations = clazz.getAnnotations();
                for (Annotation annotation : annotations) {
                    System.out.println(annotation.toString());
                }
                if (clazz.isAnnotationPresent(KXYController.class)) {
                    //默认别名
                    String salis = toLowerFirstCase(clazz.getSimpleName());
                    Object instance = clazz.newInstance();
                    ioc.put(salis, instance);
                } else if (clazz.isAnnotationPresent(KXYService.class)) {
                    //默认别名
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    Object instance = clazz.newInstance();

                    //自定义命名
                    if (!"".equals(clazz.getAnnotation(KXYService.class).value())) {
                        beanName = clazz.getAnnotation(KXYService.class).value();
                    }

                    ioc.put(beanName, instance);

                    //以类型命名
                    for (Class <?> i : clazz.getInterfaces()) {
                        if (ioc.containsKey(i.getName())) {
                            throw new Exception("");
                        }
                        ioc.put(i.getName(), instance);
                    }
                } else {
                    continue;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String toLowerFirstCase(String name) {
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private void doLoadConfig(String contextConfigLocation) {

        InputStream i = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            properties.load(i);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (properties != null)
                properties.clone();
        }
    }

    private void doScanner(String scanPackage) {

        URL resource = this.getClass().getClassLoader().getResource(scanPackage.replaceAll("\\.", "\\\\"));
        File classPath = new File(resource.getFile());

        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().contains(".class")) {
                    continue;
                }
                String className = scanPackage + "." + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }
    }
}
