package com.ebs.rental.objects;

@SuppressWarnings("ALL")
public class RentalCheckIn {
	private int location = 0;
	private String equipmentId = "";
	private String hourMeter = "";
	private int checkInType = 0;
	private int radioDecider = 0;
	private boolean replacement = false;
	
	private boolean operatorPresent = false;
	private int returnReason = 0;
	private int equipmentFailure = 0;
	private String customerDamage = "";
	private String customerRepair = "";
	private int engineOperation = 0;
	
	private int brake = 0;
	private int controls = 0;
	private int lights = 0;
	private int seatBelt = 0;
	private int drive = 0;
	private int lift = 0;
	
	private int cabOperation = 0;
	private String representative = "";
	private String descrepencies = "";
	private int id = 0;
	private String userToken = "";

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getHourMeter() {
		return hourMeter;
	}

	public void setHourMeter(String hourMeter) {
		this.hourMeter = hourMeter;
	}

	public int getCheckInType() {
		return checkInType;
	}

	public void setCheckInType(int checkInType) {
		this.checkInType = checkInType;
	}

	public int getRadioDecider() {
		return radioDecider;
	}

	public void setRadioDecider(int radioDecider) {
		this.radioDecider = radioDecider;
	}

	public boolean isReplacement() {
		return replacement;
	}

	public void setReplacement(boolean replacement) {
		this.replacement = replacement;
	}

	public boolean isOperatorPresent() {
		return operatorPresent;
	}

	public void setOperatorPresent(boolean operatorPresent) {
		this.operatorPresent = operatorPresent;
	}

	public int getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(int returnReason) {
		this.returnReason = returnReason;
	}

	public int getEquipmentFailure() {
		return equipmentFailure;
	}

	public void setEquipmentFailure(int equipmentFailure) {
		this.equipmentFailure = equipmentFailure;
	}

	public String getCustomerDamage() {
		return customerDamage;
	}

	public void setCustomerDamage(String customerDamage) {
		this.customerDamage = customerDamage;
	}

	public String getCustomerRepair() {
		return customerRepair;
	}

	public void setCustomerRepair(String customerRepair) {
		this.customerRepair = customerRepair;
	}

	public int getEngineOperation() {
		return engineOperation;
	}

	public void setEngineOperation(int engineOperation) {
		this.engineOperation = engineOperation;
	}

	public int getBrake() {
		return brake;
	}

	public void setBrake(int brake) {
		this.brake = brake;
	}

	public int getControls() {
		return controls;
	}

	public void setControls(int controls) {
		this.controls = controls;
	}

	public int getLights() {
		return lights;
	}

	public void setLights(int lights) {
		this.lights = lights;
	}

	public int getSeatBelt() {
		return seatBelt;
	}

	public void setSeatBelt(int seatBelt) {
		this.seatBelt = seatBelt;
	}

	public int getDrive() {
		return drive;
	}

	public void setDrive(int drive) {
		this.drive = drive;
	}

	public int getLift() {
		return lift;
	}

	public void setLift(int lift) {
		this.lift = lift;
	}

	public int getCabOperation() {
		return cabOperation;
	}

	public void setCabOperation(int cabOperation) {
		this.cabOperation = cabOperation;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public String getDescrepencies() {
		return descrepencies;
	}

	public void setDescrepencies(String descrepencies) {
		this.descrepencies = descrepencies;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

}
