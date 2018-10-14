package me.web.files;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Main {
    public static void main(String[] args) throws ServletException, LifecycleException, InterruptedException {
        String hostName = "localhost";
        int post = 8080;
        String contextPath = "/im";

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(post);
        tomcat.setHostname(hostName);
        tomcat.getHost().setAppBase(".");

        Context context = tomcat.addWebapp(contextPath, getAbsolutePath() + "/src/main/webapp");
        context.setParentClassLoader(Main.class.getClassLoader());

        tomcat.start();
        tomcat.getServer().await();

    }

    private static String getAbsolutePath() {
        String path = null;
        String folderPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath(); // .substring(1);
        if (folderPath.indexOf("target") > 0) {
            path = folderPath.substring(0, folderPath.indexOf("target"));
        }
        return path;
    }
}