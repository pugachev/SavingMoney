package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ReadProperties {
	private static ReadProperties inst = new ReadProperties();
	private static final String PRPFNAME = "custom.properties";


	private static Properties prop;

	private ReadProperties(){
		prop = new Properties();
	}

	//ファイル読込処理
	public static boolean isPropertiesFile()
	{
		boolean ret = false;
        //1）本クラスが実行されているWebアプリケーションのクラスローダを使う
    	InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(PRPFNAME);
		try
		{
		    prop.load(in);
		    ret = true;
		}
		catch(IOException iex)
		{
			return ret;
		}

		return ret;
	}

	public static String getProperty(String key){
		return inst.prop.getProperty(key);
	}


}
