package speedata.com.eoms.bean;

/**
 * Created by 张明_ on 2017/4/5.
 */

public class RVBean {
    private String name;
    private String type;
    private String position;
    private String responsible;
    private String repair;
    private boolean no1_check=false;
    private String remark;
    private String orderNumber;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public boolean isNo1_check() {
        return no1_check;
    }

    public void setNo1_check(boolean no1_check) {
        this.no1_check = no1_check;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
