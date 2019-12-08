package model;

public class UserInfo {
	//ログインステータス
	private String loginStatus;
	//ユーザーID(登録済メールアドレス)
	private String userId;
	//表示対象の月
	private String dispMonth;
	//表示対象の月の合計
	private int	   dispMonthSum;
	//表示対象の月の買い物リストデータ
	private String listData;
	//詳細画面の現在の表示ページ
	private int    presetPageNum;
	//表示対象の月の買い物リスト件数
	private int    dispMonthDataCnt;

	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDispMonth() {
		return dispMonth;
	}
	public void setDispMonth(String dispMonth) {
		this.dispMonth = dispMonth;
	}
	public int getDispMonthSum() {
		return dispMonthSum;
	}
	public void setDispMonthSum(int dispMonthSum) {
		this.dispMonthSum = dispMonthSum;
	}
	public String getListData() {
		return listData;
	}
	public void setListData(String listData) {
		this.listData = listData;
	}
	public int getPresetPageNum() {
		return presetPageNum;
	}
	public void setPresetPageNum(int presetPageNum) {
		this.presetPageNum = presetPageNum;
	}
	public int getDispMonthDataCnt() {
		return dispMonthDataCnt;
	}
	public void setDispMonthDataCnt(int dispMonthDataCnt) {
		this.dispMonthDataCnt = dispMonthDataCnt;
	}



}
