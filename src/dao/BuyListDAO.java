package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import model.DailySum;
import model.Product;

public class BuyListDAO {

	//購入品一覧を取得するSQL
//    private static final String SELECT = "select * from buylist where userid=? and buydate " ;

    //対象月の合計金額を取得するSQL
//    private static final String SELECTAMOUNTBYMONTH = "select sum(price) as buyamount from buylist where buydate between";

    //購入品を追加するSQL
	private static final String INSERTBUYLIST = "insert into buylist(itemnum,price,userid,buydate,regidate) values(?,?,?,?,?);";

	//選択日に使った合計金額を取得するSQL
	private static final String SELECTAMOUNTBYDAY = "select buydate,sum(price) as buyamount from buylist where userid=? group by ? ;";

	//「項目」と「金額」を変更するSQL
	private static final String UPDATEITEM = "update buylist set itemnum=?,price=? where id=?";

	//「項目」を削除するSQL
	private static final String DELETEITEM = "delete from buylist where id=?";

	//「email」からユーザーの存在有無を調べるSQL
	private static final String ISUSER = "select password from loginUser where mail=?";

	//「email」と「パスワード」を登録するSQL
	private static final String REGIUSER = "insert into loginUser(mail,password,regidate) values(?,?,?);";

	//「userid」毎に「日付」で合計した購入金額をだす
	private static final String SUMPRICE = "SELECT sum(price) as sumprice,buydate FROM struts.buylist where userid= ? group by buydate having buydate >= ? and buydate <= ?;";

	//「上限」と「オフセット」ありで買い物明細を取得する
	private static final String SHOPPINGLIST_LIMIT ="select buylist.id as id,buylist.price as price,buylist.buydate as buydate,buylist.regidate as regidate,itemlist.title as title from buylist left join itemlist on buylist.itemnum = itemlist.itemnum where userid=? and buydate between ?  and ? order by  buydate asc limit 10 offset ?";

	private static final String COUNT_DATA ="select count(buylist.id) as totalcnt from buylist  left join itemlist on buylist.itemnum = itemlist.itemnum where userid= ? and buydate  between ? and ?;";

	//月別合計
	private static final String TOTALSUM_MONTH="select sum(price) as totalprice from buylist  left join itemlist on buylist.itemnum = itemlist.itemnum where userid=? and buydate  between ? and ?;";

	//外部出力
	private static final String OUTPUT_FILE="SELECT * FROM buylist INTO OUTFILE '/Users/mtake/Desktop/test.csv' FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\r\\n'; ";

    private DataSource source;

    public BuyListDAO() {
        source = DaoUtil.getSource();
    }

    public void outPutFle(String userid,String date,String date2) throws SQLException
    {
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {

            con = source.getConnection();

            pStmt = con.prepareStatement(OUTPUT_FILE);
            rs = pStmt.executeQuery();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            if(rs != null){
                rs.close();
            }
            pStmt.close();
            con.close();
        }
    }

    //月別の合計金額を調べる
    //ログインユーザーの指定期間でのデータ数を調べる
    public int getTotalYearSum(String userid,String date,String date2) throws SQLException
    {
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        int ret=0;

        try {

            con = source.getConnection();

            pStmt = con.prepareStatement(TOTALSUM_MONTH);
            pStmt.setString(1,userid);
            pStmt.setString(2,date);
            pStmt.setString(3,date2);
            rs = pStmt.executeQuery();

            while (rs.next()) {
            	if(rs.getString("totalprice")!=null) {
            		ret=Integer.parseInt(rs.getString("totalprice"));
            	}else {
            		ret=0;
            	}

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if(rs != null){
                rs.close();
            }
            pStmt.close();
            con.close();
        }

        return ret;
    }


    //月別の合計金額を調べる
    //ログインユーザーの指定期間でのデータ数を調べる
    public int getTotalSum(String userid,String date,String date2) throws SQLException
    {
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        int ret=0;

        try {

            con = source.getConnection();

            pStmt = con.prepareStatement(TOTALSUM_MONTH);
            pStmt.setString(1,userid);
            pStmt.setString(2,date);
            pStmt.setString(3,date2);
            rs = pStmt.executeQuery();

            while (rs.next()) {
            	if(rs.getString("totalprice")!=null) {
            		ret=Integer.parseInt(rs.getString("totalprice"));
            	}else {
            		ret=0;
            	}

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if(rs != null){
                rs.close();
            }
            pStmt.close();
            con.close();
        }

        return ret;
    }




    //ログインユーザーの指定期間でのデータ数を調べる
    public int getTotalDataCnt(String userid,String date,String date2) throws SQLException
    {
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        int ret=0;

        try {

            con = source.getConnection();

            pStmt = con.prepareStatement(COUNT_DATA);
            pStmt.setString(1,userid);
            pStmt.setString(2,date);
            pStmt.setString(3,date2);
            rs = pStmt.executeQuery();

            while (rs.next()) {
            	ret=Integer.parseInt(rs.getString("totalcnt"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if(rs != null){
                rs.close();
            }
            pStmt.close();
            con.close();
        }

        return ret;
    }

    //ユーザーの存在有無を調べる
    public String isUser(String email) throws SQLException
    {
        //userテーブルからパスワードを取得する(あればユーザーは存在する)
        Connection con = source.getConnection();
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        String ret = "";
        try{
            pStmt = con.prepareStatement(ISUSER);
            pStmt.setString(1,email);
            rs = pStmt.executeQuery();

            while (rs.next()) {
                ret=rs.getString("password");
            }
        }catch(SQLException ex){
            throw ex;

        }finally{
            pStmt.close();
            con.close();
        }

        return ret;
    }

    //ユーザーを登録する
    public void regiUser(String email,String password,String regidate) throws SQLException
    {
        //userテーブルからパスワードを取得する(あればユーザーは存在する)
        Connection con = source.getConnection();
        PreparedStatement pStmt = null;

        try{
            pStmt = con.prepareStatement(REGIUSER);
            pStmt.setString(1,email);
            pStmt.setString(2,password);
            pStmt.setString(3, regidate);
            pStmt.executeUpdate();

        }catch(SQLException ex){
            throw ex;

        }finally{
            pStmt.close();
            con.close();
        }
    }

    public void deleteItem(String id) throws SQLException
    {
        //新しく入力された商品にテーブルを更新する
        Connection con = source.getConnection();
        PreparedStatement pStmt = null;

        try{
            pStmt = con.prepareStatement(DELETEITEM);
            pStmt.setString(1,id);
            pStmt.executeUpdate();

        }catch(SQLException ex){
            throw ex;

        }finally{
            pStmt.close();
            con.close();
        }
    }

    public void updateItem(String id,String inum,String price) throws SQLException
    {
        //新しく入力された商品にテーブルを更新する
        Connection con = source.getConnection();
        PreparedStatement pStmt = null;

        try{
            pStmt = con.prepareStatement(UPDATEITEM);
            pStmt.setString(1,inum);
            pStmt.setString(2,price);
            pStmt.setString(3,id);
            pStmt.executeUpdate();

        }catch(SQLException ex){
            throw ex;

        }finally{
            pStmt.close();
            con.close();
        }
    }


    public int getAmount(String date) throws SQLException
    {
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        int ret=0;

        try {

            con = source.getConnection();

            pStmt = con.prepareStatement(SELECTAMOUNTBYDAY);
            pStmt.setString(1,date);
            rs = pStmt.executeQuery();

            while (rs.next()) {
                ret=rs.getInt("price");
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            if(rs != null){
                rs.close();
            }
            pStmt.close();
            con.close();
        }

        return ret;
    }

    public int getAmountByMonth(String userid,String date,String date2) throws SQLException
    {
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        int ret=0;

        try {

            con = source.getConnection();

            String sqltmp = "select sum(price) as buyamount from buylist where userid='"+ userid +"' and buydate " +" between '"+ date+ "' and '" + date2 +"'";

            pStmt = con.prepareStatement(sqltmp);
            rs = pStmt.executeQuery();

            while (rs.next()) {
                ret=rs.getInt("buyamount");
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            if(rs != null){
                rs.close();
            }
            pStmt.close();
            con.close();
        }

        return ret;
    }

    //対象付きのショピングリストを作成する
    public List<Product> getShoppingList(String userid,String date,String date2,int offset) throws SQLException
    {
        List<Product> list = new ArrayList<Product>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try
        {
            con = source.getConnection();
            //SHOPPINGLIST_LIMIT
            //"select buylist.id as id,buylist.price as price,buylist.buydate as buydate,buylist.regidate as regidate,itemlist.title as title from buylist left join itemlist on buylist.itemnum = itemlist.itemnum where userid=? and buydate between ?  and ? order by  buydate asc limit 10 offset ?";
            pStmt = con.prepareStatement(SHOPPINGLIST_LIMIT);
            pStmt.setString(1,userid);
            pStmt.setString(2,date);
            pStmt.setString(3,date2);
            pStmt.setInt(4,offset);
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                list.add(getProduct(rs));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if(rs != null){
                rs.close();
            }
            pStmt.close();
            con.close();
        }

        return list;
    }

    //対象月のリストを作る
    public List<Product> getProductList(String userid,String date,String date2) throws SQLException {
        List<Product> list = new ArrayList<Product>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {

            con = source.getConnection();
            String sqltmp = "select buydate,sum(price) as buyamount from buylist where userid='"+userid+"' and buydate " +" between '"+ date+ "' and '" + date2 +"'" + "group by buydate";
            pStmt = con.prepareStatement(sqltmp);
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                list.add(getProductAmount(rs));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            if(rs != null){
                rs.close();
            }
            pStmt.close();
            con.close();
        }

        return list;
    }

    //対象月のリストを作る
    public List<DailySum> getDailySumList(String userid,String date,String date2) throws SQLException {
        List<DailySum> list = new ArrayList<DailySum>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try{
        	con = source.getConnection();
            pStmt = con.prepareStatement(SUMPRICE);
            pStmt.setString(1,userid);
            pStmt.setString(2,date);
            pStmt.setString(3,date2);
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                list.add(getDailySum(rs));
            }

        }catch(SQLException ex){
            throw ex;

        }finally{
            pStmt.close();
            con.close();
        }

        return list;
    }


    public void entry(Product pro) throws SQLException {
        //新しく入力された商品にテーブルを追加する
        Connection con = source.getConnection();
        PreparedStatement pStmt = null;
        Date now = nowDate();
        try{
            pStmt = con.prepareStatement(INSERTBUYLIST);

            pStmt.setInt(1,pro.getItemnum());
            pStmt.setInt(2,pro.getPrice());
            pStmt.setString(3,pro.getUserid());
            pStmt.setString(4,pro.getBuydate());
            pStmt.setDate(5,now);
            pStmt.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pStmt.close();
            con.close();
        }
    }

    private Product getProductAmount(ResultSet rs) throws SQLException {
        Product pro = new Product();

        pro.setPrice(rs.getInt("price"));
        pro.setBuydate(rs.getString("buydate"));
        return pro;
    }

    private Product getProduct(ResultSet rs) throws SQLException {
        Product pro = new Product();
        pro.setId(rs.getInt("id"));
        pro.setPrice(rs.getInt("price"));
        pro.setTitle(rs.getString("title"));
        pro.setBuydate(rs.getString("buydate"));
        return pro;
    }
    private DailySum getDailySum(ResultSet rs) throws SQLException {
    	DailySum dsum = new DailySum();
    	dsum.setDailysum(rs.getInt("sumprice"));
    	dsum.setBuydate(rs.getString("buydate"));
        return dsum;
    }
    private Date nowDate() {
        Calendar cal = Calendar.getInstance();
        return new Date(cal.getTimeInMillis());
    }
}
