package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import core.BaseController;
import core.NinjaKey;
import core.ResultJson;
import filters.AuthenticationFilter;
import models.SysUser;
import ninja.*;
import ninja.params.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.UserService;
import utils.JacksonUtil;

@Singleton
public class IndexController extends BaseController {

    Logger log = LoggerFactory.getLogger(IndexController.class);

    @Inject
    ReverseRouter reverseRouter;
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
                context.getSession().put(NinjaKey.AUTHENTICATED_USER, user.getUsername());

                return reverseRouter.with(IndexController::index).redirect();
            } else {
                log.info("Wrong credentials ... redirecting to login");
                return reverseRouter.with(IndexController::showLoginForm).queryParam("data", "用户登录密码错误").redirect();
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

    public Result showLoginForm(Context context) {
        log.info(context.getAttributes().toString());
        return Results.html().template("views/system/login.ftl.html");
    }

    public Result showRegisterForm() {
        return Results.html().template("views/system/register.ftl.html");
    }
}
