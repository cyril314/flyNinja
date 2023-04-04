import lombok.extern.slf4j.Slf4j;
import ninja.servlet.NinjaServletListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.*;
import java.net.InetAddress;
import java.util.*;

/**
 * @className: FitApplication
 * @description: Jetty启动web入口
 * @author: Aim
 * @date: 2023/3/30
 **/
@Slf4j
public class FitApplication {

    private final static String[] WELCOMES = new String[]{"/index.ftl", "/index.html"};
    private final static String RESOURCE_PATH = FitApplication.class.getResource("/").getPath();
    private final static String CONTEXT_PATH = "/";
    private final static String WEB_APP = "fly.war";
    private final static String PORT_ENV = "PORT";
    private final static int DEFAULT_PORT = 8080;

    public static void main(String[] args) {
        int port = getPort();
        Server server = new Server(port);
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            WebAppContext web = new WebAppContext(WEB_APP, CONTEXT_PATH);
            web.addLocaleEncoding("zh", "utf-8");
            web.addFilter("com.google.inject.servlet.GuiceFilter", "/*", EnumSet.of(DispatcherType.REQUEST));
            web.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
            web.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
            web.setInitParameter("TemplatePath", RESOURCE_PATH + "/views");
            web.addServlet("freemarker.ext.servlet.FreemarkerServlet", "*.ftl.html");
            web.addEventListener(new NinjaServletListener());

            web.setClassLoader(Thread.currentThread().getContextClassLoader());
            // 设置Web内容上下文路径
            web.setResourceBase(RESOURCE_PATH);
            web.setResourceAliases(getResources());
            web.setParentLoaderPriority(true);
            web.setWelcomeFiles(WELCOMES);
            web.setDisplayName("Fly");
            // WEB服务
            server.setHandler(web);
            if (!web.isAvailable()) {
                System.exit(1);
            }
            server.start();

            log.info("\n---------------------------------------------------------\n" +
                    "Application Fly-Jetty is running! Access URLs:\n\t" +
                    "Local: \t\thttp://localhost:" + port + "/\n\t" +
                    "External:\thttp://" + ip + ":" + port + "/" +
                    "\n-----------------页面请部署 ---- web ----------------------");
            server.join();
        } catch (Exception e) {
            server.destroy();
            throw new RuntimeException("Could not start server", e);
        }
    }

    /**
     * 静态资源跳转
     */
    private static Map<String, String> getResources() {
        Map<String, String> map = new HashMap<>();
        map.put("assets", RESOURCE_PATH + "assets");
        map.put("views", RESOURCE_PATH + "views");
        return map;
    }

    /**
     * Returns the port specified by the environment variable PORT or DEFAULT_PORT if not present.
     */
    private static int getPort() {
        Optional<String> envPort = Optional.ofNullable(System.getenv(PORT_ENV));
        return envPort.map(Integer::parseInt).orElse(DEFAULT_PORT);
    }
}
