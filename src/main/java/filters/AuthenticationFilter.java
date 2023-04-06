package filters;

import com.google.inject.Inject;
import controllers.IndexController;
import core.NinjaKey;
import ninja.*;
import ninja.session.Session;
import org.slf4j.Logger;

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

    @Override
    public Result filter(FilterChain chain, Context context) {
        Session session = context.getSession();
        String requestPath = context.getRequestPath();
        log.debug("Request Path: {}", requestPath);
        String sessionValueString = session.get(NinjaKey.AUTHENTICATED_USER);
        if (requestPath.equals("/logout") || sessionValueString == null) {
            log.debug("Logout Path: {}", context.getRequestPath());
            session.clear();
            return reverseRouter.with(IndexController::showLoginForm).redirect();
        }

        return chain.next(context);
    }
}
