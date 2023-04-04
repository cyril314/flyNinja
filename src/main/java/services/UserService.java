package services;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import core.BaseService;
import mapper.SysUserMapper;
import models.SysUser;
import ninja.utils.Crypto;
import ninja.utils.NinjaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: UserService
 * @description: 用户服务
 * @author: Aim
 * @date: 2023/4/3
 **/
public class UserService extends BaseService<SysUserMapper, SysUser> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    private NinjaProperties ninjaProperties;

    public boolean authenticate(String passwd, String hash) {
        Preconditions.checkNotNull(passwd, "Password is required for authenticate");
        Preconditions.checkNotNull(hash, "Hashed password is required for authenticate");
        boolean authenticated = false;
        try {
            authenticated = this.checkPwd(passwd, hash);
        } catch (IllegalArgumentException e) {
            log.error("Failed to check password against hash", e);
        }

        return authenticated;
    }

    private boolean checkPwd(String passwd, String hash) {
        Crypto crypto = new Crypto(ninjaProperties);
        return crypto.signHmacSha1(passwd).equals(hash);
    }
}
