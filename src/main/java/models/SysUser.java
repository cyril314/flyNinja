package models;

import java.util.Date;

/**
 * @className: SysUser
 * @description: 用户表
 * @author: Aim
 * @date: 2023/4/3
 **/
public class SysUser {

    private static final long serialVersionUID = 1L;

    //用户姓名
    private Long id;
    //创建时间
    private Date ctime = new Date();
    //用户姓名
    private String name;
    //登陆用户名(登陆号)
    private String username;
    //用户密码
    private String password;
    //描述
    private String desc;
    //修改时间
    private Date etime = new Date();
    //是否被禁用 0禁用1正常
    private Integer enabled = 0;
    //是否是超级用户 0非1是
    private Integer isys = 0;

    public SysUser() {
        super();
    }

    public SysUser(String username) {
        super();
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * 设置：用户姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：用户姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：登陆用户名(登陆号)
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取：登陆用户名(登陆号)
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置：用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取：用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置：描述
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 获取：描述
     */
    public String getDesc() {
        return desc;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    /**
     * 设置：是否被禁用 0禁用1正常
     */
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取：是否被禁用 0禁用1正常
     */
    public Integer getEnabled() {
        return enabled;
    }

    /**
     * 设置：是否是超级用户 0非1是
     */
    public void setIsys(Integer isys) {
        this.isys = isys;
    }

    /**
     * 获取：是否是超级用户 0非1是
     */
    public Integer getIsys() {
        return isys;
    }

    @Override
    public String toString() {
        return "SysUserBean{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", desc='" + desc + '\'' +
                ", enabled=" + enabled +
                ", isys=" + isys +
                '}';
    }
}
