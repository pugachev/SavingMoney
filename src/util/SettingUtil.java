package util;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;


public class SettingUtil {
	private static SettingUtil inst = new SettingUtil();
	private static final String PRPFNAME = "jdbc.properties";

	private Properties prop;

	private SettingUtil(){
		prop = new Properties();

                //1）本クラスが実行されているWebアプリケーションのクラスローダを使う
            	InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(PRPFNAME);
		try{
            		prop.load(in);
		}catch(IOException iex){
			iex.printStackTrace();
		}
	}

	public static String getProperty(String key){
		return inst.prop.getProperty(key);
	}

}
