package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.Product;

public class BuyListDAO {

	//購入品一覧を取得するSQL
    private static final String SELECT = "select * from buylist";

    //購入品を追加するSQL
	private static final String INSERTBUYLIST = "insert into buylist(itemnum,buyamount,buydate) values(?,?,?);";

    private DataSource source;

    public BuyListDAO() {
        source = DaoUtil.getSource();
    }

    public List<Product> getProductList() throws SQLException {
        List<Product> list = new ArrayList<Product>();
        Connection con = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {

            con = source.getConnection();

            pStmt = con.prepareStatement(SELECT);
            rs = pStmt.executeQuery();

            while (rs.next()) {
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

    public void entry(Product pro) throws SQLException {
        //新しく入力された商品にテーブルを追加する
        Connection con = source.getConnection();
        PreparedStatement pStmt = null;

        try{
            pStmt = con.prepareStatement(INSERTBUYLIST);

            pStmt.setInt(1,pro.getItemnum());
            pStmt.setInt(2,pro.getBuyamount());
            pStmt.executeUpdate();

        }catch(SQLException ex){
            throw ex;
        }finally{
            pStmt.close();
            con.close();
        }
    }

    private Product getProduct(ResultSet rs) throws SQLException {
        Product pro = new Product();

        pro.setId(rs.getInt("id"));
        pro.setItemnum(rs.getInt("itemnum"));
        pro.setBuyamount(rs.getInt("buyamount"));

        return pro;
    }

}
