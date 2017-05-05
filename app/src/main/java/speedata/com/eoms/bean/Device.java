package speedata.com.eoms.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 张明_ on 2017/4/26.
 */
@Entity
public class Device {
    //设备ID
    @Id(autoincrement = false)
    private String id;

    //设备名称
    private String name;

    //二维码串
    private String barcode;
    //设备分类
    private String classify;
    //设备位置
    private String position;
    //设备购置日期
    private String buyDate;
    //设备使用日期
    private String useDate;
    //质保年限
    private String keepYears;
    //维修厂家
    private String maintenanceFactory;
    //维修厂家ID
    private String maintenanceFactoryId;
    //设备类型
    private String type;
    //设备型号
    private String model;
    //设备生产商
    private String manufacturer;
    //设备供应商
    private String supplier;
    //设备管理单位
    private String managementUnit;
    //负责科室
    private String responsibilityOffice;
    //使用车站
    private String useStation;
    //设备配备部门
    private String equipDepartment;
    //设备数量
    private String number;
    //设备状态
    private String state;
    //巡检标志位
    private String flagBit;
    //巡检周期
    private String checkPeriod;
    //报修级别
    private String repairLevel;
    //维修运行最长时间
    private String usedTime;
    //备注
    private String remark;
    //预留
    private String reservedOne;
    private String reservedTwo;
    private String reservedThree;
    //创建时间
    private String creationTime;
    @Generated(hash = 851080128)
    public Device(String id, String name, String barcode, String classify,
            String position, String buyDate, String useDate, String keepYears,
            String maintenanceFactory, String maintenanceFactoryId, String type,
            String model, String manufacturer, String supplier,
            String managementUnit, String responsibilityOffice, String useStation,
            String equipDepartment, String number, String state, String flagBit,
            String checkPeriod, String repairLevel, String usedTime, String remark,
            String reservedOne, String reservedTwo, String reservedThree,
            String creationTime) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.classify = classify;
        this.position = position;
        this.buyDate = buyDate;
        this.useDate = useDate;
        this.keepYears = keepYears;
        this.maintenanceFactory = maintenanceFactory;
        this.maintenanceFactoryId = maintenanceFactoryId;
        this.type = type;
        this.model = model;
        this.manufacturer = manufacturer;
        this.supplier = supplier;
        this.managementUnit = managementUnit;
        this.responsibilityOffice = responsibilityOffice;
        this.useStation = useStation;
        this.equipDepartment = equipDepartment;
        this.number = number;
        this.state = state;
        this.flagBit = flagBit;
        this.checkPeriod = checkPeriod;
        this.repairLevel = repairLevel;
        this.usedTime = usedTime;
        this.remark = remark;
        this.reservedOne = reservedOne;
        this.reservedTwo = reservedTwo;
        this.reservedThree = reservedThree;
        this.creationTime = creationTime;
    }
    @Generated(hash = 1469582394)
    public Device() {
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
    public String getClassify() {
        return this.classify;
    }
    public void setClassify(String classify) {
        this.classify = classify;
    }
    public String getPosition() {
        return this.position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getBuyDate() {
        return this.buyDate;
    }
    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }
    public String getUseDate() {
        return this.useDate;
    }
    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }
    public String getKeepYears() {
        return this.keepYears;
    }
    public void setKeepYears(String keepYears) {
        this.keepYears = keepYears;
    }
    public String getMaintenanceFactory() {
        return this.maintenanceFactory;
    }
    public void setMaintenanceFactory(String maintenanceFactory) {
        this.maintenanceFactory = maintenanceFactory;
    }
    public String getMaintenanceFactoryId() {
        return this.maintenanceFactoryId;
    }
    public void setMaintenanceFactoryId(String maintenanceFactoryId) {
        this.maintenanceFactoryId = maintenanceFactoryId;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getModel() {
        return this.model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getManufacturer() {
        return this.manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public String getSupplier() {
        return this.supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public String getManagementUnit() {
        return this.managementUnit;
    }
    public void setManagementUnit(String managementUnit) {
        this.managementUnit = managementUnit;
    }
    public String getResponsibilityOffice() {
        return this.responsibilityOffice;
    }
    public void setResponsibilityOffice(String responsibilityOffice) {
        this.responsibilityOffice = responsibilityOffice;
    }
    public String getUseStation() {
        return this.useStation;
    }
    public void setUseStation(String useStation) {
        this.useStation = useStation;
    }
    public String getEquipDepartment() {
        return this.equipDepartment;
    }
    public void setEquipDepartment(String equipDepartment) {
        this.equipDepartment = equipDepartment;
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getFlagBit() {
        return this.flagBit;
    }
    public void setFlagBit(String flagBit) {
        this.flagBit = flagBit;
    }
    public String getCheckPeriod() {
        return this.checkPeriod;
    }
    public void setCheckPeriod(String checkPeriod) {
        this.checkPeriod = checkPeriod;
    }
    public String getRepairLevel() {
        return this.repairLevel;
    }
    public void setRepairLevel(String repairLevel) {
        this.repairLevel = repairLevel;
    }
    public String getUsedTime() {
        return this.usedTime;
    }
    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getReservedOne() {
        return this.reservedOne;
    }
    public void setReservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
    }
    public String getReservedTwo() {
        return this.reservedTwo;
    }
    public void setReservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
    }
    public String getReservedThree() {
        return this.reservedThree;
    }
    public void setReservedThree(String reservedThree) {
        this.reservedThree = reservedThree;
    }
    public String getCreationTime() {
        return this.creationTime;
    }
    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
