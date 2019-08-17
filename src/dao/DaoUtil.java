package dao;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import util.SettingUtil;

public class DaoUtil {
	private DataSource source;
	private static DaoUtil inst = new DaoUtil();

	private static final String JDBC_URL = "jdbc.driver.url";
	private static final String JDBC_USER = "jdbc.driver.user";
	private static final String JDBC_PASS = "jdbc.driver.pass";
	private static final String JDBC_DRIVER = "jdbc.driver.classname";

	private static final String USER_LITERAL = "user";
	private static final String PASS_LITERAL = "password";

	private DaoUtil()
	{
		try {
			Class.forName(SettingUtil.getProperty(DaoUtil.JDBC_DRIVER));
			source = createDataSource();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static DataSource getSource()
	{
		return inst.source;
	}

	private DataSource createDataSource()
	{

		//1)設定ファイルから設定を読み込みJDBCの情報とする
		Properties p = new Properties();
		p.put("user", SettingUtil.getProperty(DaoUtil.JDBC_USER));
		p.put("password", SettingUtil.getProperty(DaoUtil.JDBC_PASS));

		//2) DataSourceの背後で動作するオブジェクトプールの作成
    	GenericObjectPool pool = new GenericObjectPool(null);

		//3）プール用のオブジェクトを作成するためのファクトリーを作成
    	ConnectionFactory cf = new DriverManagerConnectionFactory(SettingUtil.getProperty(DaoUtil.JDBC_URL), p);

		//4)プール用のファクトリを作成
		PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, pool, null, null, false, true);

		//5)データソースの作成
    	return new PoolingDataSource(pool);
	}
}