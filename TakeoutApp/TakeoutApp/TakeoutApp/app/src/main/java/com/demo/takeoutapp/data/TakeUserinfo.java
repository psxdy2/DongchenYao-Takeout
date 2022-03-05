package com.demo.takeoutapp.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p>
 * 
 * </p>
 *
 * @author moyu
 * @since 2021-05-07
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TakeUserinfo  {

    private String username;

    private String account;

    private String password;

    @Id
    private Long onlyid;

    private Integer id;

    private String portrait;

    private String sex;

    private String birthday;

    @Generated(hash = 671389289)
    public TakeUserinfo(String username, String account, String password,
            Long onlyid, Integer id, String portrait, String sex, String birthday) {
        this.username = username;
        this.account = account;
        this.password = password;
        this.onlyid = onlyid;
        this.id = id;
        this.portrait = portrait;
        this.sex = sex;
        this.birthday = birthday;
    }

    @Generated(hash = 1958746861)
    public TakeUserinfo() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPortrait() {
        return this.portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Long getOnlyid() {
        return this.onlyid;
    }

    public void setOnlyid(Long onlyid) {
        this.onlyid = onlyid;
    }

}
