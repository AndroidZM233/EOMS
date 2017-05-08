package speedata.com.eoms.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 张明_ on 2017/4/19.
 */
@Entity
public class User {
    @Id(autoincrement = false)
    private String user_name;

    private String pwd;

    private String real_name;

    @Generated(hash = 520470829)
    public User(String user_name, String pwd, String real_name) {
        this.user_name = user_name;
        this.pwd = pwd;
        this.real_name = real_name;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getReal_name() {
        return this.real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }
}
