package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import core.BaseController;
import filters.AuthenticationFilter;
import models.SysUser;
import ninja.*;
import ninja.params.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.RedisService;
import services.UserService;
import utils.SessionUtil;

@Singleton
public class IndexController extends BaseController {

    Logger log = LoggerFactory.getLogger(IndexController.class);

    @Inject
    ReverseRouter reverseRouter;
    @Inject
    RedisService redisService;
    @Inject
    UserService userService;

    @FilterWith(AuthenticationFilter.class)
    public Result index() {
        return Results.html().template("views/index/index.ftl.html");
    }

    public Result helloWorldJson(@Param("aa") String aa) {//接收aa参数
        String content = "Hello World! Hello Json!";
        return Results.json().render(content);
    }

    public Result login(@Param("username") String username, @Param("passwd") String pass, Context context) {
        SysUser user = userService.get(new SysUser(username));
        if (user != null) {
            boolean isValid = userService.authenticate(pass, user.getPassword());
            if (isValid) {
                log.info("User is Authenticated: " + isValid);
                SessionUtil.setSessionValue("username", user.getUsername());
                setCookie(username, context);
                return reverseRouter.with(IndexController::index).redirect();
            } else {
                log.info("Wrong credentials ... redirecting to login");
                return reverseRouter.with(IndexController::showLoginForm).redirect();
            }
        } else {
            log.info("User does not exists: " + username + "... go register");
            return reverseRouter.with(IndexController::showRegisterForm).redirect();
        }
    }

    @Transactional
    public Result register(Context context) {
        SysUser user = getEntity(context, SysUser.class);
        if (user != null) {
            log.info("User exists ...go to login page and log in");
            SysUser sysUser = userService.get(user);
            if (sysUser == null) {
                userService.save(user);
            }
        } else {
            //User does not exists ... go to login page
        }
        return reverseRouter.with(IndexController::showLoginForm).redirect();
    }

    public Result logout(Context context) {
        log.info("User session invalidated, log in again");
        String content = "成功退出";
        return reverseRouter.with(IndexController::showLoginForm).redirect().render(content);
    }

    public Result showLoginForm() {
        return Results.html().template("views/system/login.ftl.html");
    }

    public Result showRegisterForm() {
        return Results.html().template("views/system/register.ftl.html");
    }
}
