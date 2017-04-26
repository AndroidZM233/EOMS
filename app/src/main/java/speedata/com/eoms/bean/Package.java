package speedata.com.eoms.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 张明_ on 2017/4/26.
 */
@Entity
public class Package {
    @Id(autoincrement = false)
    private String id;

    private String name;
    private String barcode;
    private String equipment;
    @Generated(hash = 1370930312)
    public Package(String id, String name, String barcode, String equipment) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.equipment = equipment;
    }
    @Generated(hash = 1194681198)
    public Package() {
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
    public String getBarcode() {
        return this.barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getEquipment() {
        return this.equipment;
    }
    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
