package speedata.com.eoms.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 张明_ on 2017/4/26.
 */
@Entity
public class DeviceType {
    @Id(autoincrement = false)
    private String id;

    private String name;
    private String code;
    private String remark;
    @Generated(hash = 1688038854)
    public DeviceType(String id, String name, String code, String remark) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.remark = remark;
    }
    @Generated(hash = 1621062569)
    public DeviceType() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
