package model;

public class DailySum {
    private int id;
    private int dailysum;
    private String buydate;
    private String memo;
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDailysum() {
		return dailysum;
	}
	public void setDailysum(int dailysum) {
		this.dailysum = dailysum;
	}
	public String getBuydate() {
		return buydate;
	}
	public void setBuydate(String buydate) {
		this.buydate = buydate;
	}



}
