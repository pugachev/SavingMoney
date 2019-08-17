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

import model.Product;

public class BuyListDAO {

	//購入品一覧を取得するSQL
    private static final String SELECT = "select * from buylist where buydate " ;

    //対象月の合計金額を取得するSQL
    private static final String SELECTAMOUNTBYMONTH = "select sum(price) as buyamount from buylist where buydate between";

    //購入品を追加するSQL
	private static final String INSERTBUYLIST = "insert into buylist(itemnum,price,buydate,regidate) values(?,?,?,?);";

	//選択日に使った合計金額を取得するSQL
	private static final String SELECTAMOUNTBYDAY = "select buydate,sum(price) as buyamount from buylist group by ?;";

    private DataSource source;

    public BuyListDAO() {
        source = DaoUtil.getSource();
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

    public int getAmountByMonth(String date,String date2) throws SQLException
    {
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        int ret=0;

        try {

            con = source.getConnection();

            String sqltmp = "select sum(price) as buyamount from buylist where buydate " +" between '"+ date+ "' and '" + date2 +"'";

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

    //対象月のリストを作る
    public List<Product> getProductList(String date,String date2) throws SQLException {
        List<Product> list = new ArrayList<Product>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {

            con = source.getConnection();
            String sqltmp = "select buydate,sum(price) as buyamount from buylist where buydate " +" between '"+ date+ "' and '" + date2 +"'" + "group by buydate";
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

    public void entry(Product pro) throws SQLException {
        //新しく入力された商品にテーブルを追加する
        Connection con = source.getConnection();
        PreparedStatement pStmt = null;
        Date now = nowDate();
        try{
            pStmt = con.prepareStatement(INSERTBUYLIST);

            pStmt.setInt(1,pro.getItemnum());
            pStmt.setInt(2,pro.getPrice());
            pStmt.setString(3,pro.getBuydate());
            pStmt.setDate(4,now);
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
        pro.setItemnum(rs.getInt("itemnum"));
        pro.setPrice(rs.getInt("price"));
        pro.setBuydate(rs.getString("buydate"));
        return pro;
    }
    private Date nowDate() {
        Calendar cal = Calendar.getInstance();
        return new Date(cal.getTimeInMillis());
    }
}
