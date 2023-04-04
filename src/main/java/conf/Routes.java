package conf;

import controllers.IndexController;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;

/**
 * @className: NinjaRoutes
 * @description: Ninja的路由配置
 * @author: Aim
 * @date: 2023/3/31
 **/
public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {
        //支持不同的请求方式，链式编程，后面helloWorldJson指向ApplicationController的具体实现的一个方法
        router.GET().route("/").with(IndexController.class, "index");
        router.GET().route("/hello_world.json").with(IndexController.class, "helloWorldJson");
        router.POST().route("/hello_world.json").with(IndexController.class, "helloWorldJson");

        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");

        ///////////////////////////////////////////////////////////////////////
        // Dashboard User Login, Logout and Register API
        ///////////////////////////////////////////////////////////////////////
        router.POST().route("/login").with(IndexController::login);
        router.GET().route("/login").with(IndexController::showLoginForm);
        router.POST().route("/register").with(IndexController::register);
        router.GET().route("/register").with(IndexController::showRegisterForm);
        router.GET().route("/logout").with(IndexController::logout);

        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(IndexController.class, "index");
    }
}
