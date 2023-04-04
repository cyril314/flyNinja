package filters;

import com.google.inject.Inject;
import controllers.IndexController;
import ninja.*;
import org.slf4j.Logger;
import services.RedisService;
import utils.JacksonUtil;

/**
 * @className: AuthenticationFilter
 * @description:
 * @author: Aim
 * @date: 2023/4/3
 **/
public class AuthenticationFilter implements Filter {

    @Inject
    Logger log;
    @Inject
    ReverseRouter reverseRouter;
    @Inject
    RedisService redisService;

    @Override
    public Result filter(FilterChain chain, Context context) {
        log.debug("Request Path: {}", context.getRequestPath());
        if (context.getRequestPath().equals("/logout")) {
            log.debug("Logout Path: {}", context.getRequestPath());
            return reverseRouter.with(IndexController::logout).redirect();
        }
        String sessionValueString = redisService.getValue(context.getSession().getId());
        if (sessionValueString == null) {
            log.info("We couldn't find a value from REDIS ");
            context.getSession().clear();
            return Results.forbidden().html().template("/views/system/403forbidden.ftl.html");
        }

        String json = JacksonUtil.toJSon(sessionValueString);
        log.info("We have retrieved a session object [{}]", json);
        return chain.next(context);
    }
}
