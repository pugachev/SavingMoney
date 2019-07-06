
package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.struts.upload.FormFile;

public class UploadFileUtil {
    
    private static final String IMGDIR ="img";    
    
    private ServletContext context;
    
    public UploadFileUtil(ServletContext ctx){
        context = ctx;
    }
    
    public String getFilePath(String baseName) {
        //FormFileインタフェースのgetFileName()メソッドはパスを含まずファイル名だけを返すので
        //ファイル名にimgフォルダを追加して相対パスを組み立てている
        //DBの文字列がUTF-8であることから日本語ファイル名が含まれている時に
        //エラーになるのでファイル名を日時とファイル名のHash値にする。

       Date cDate = new Date();

       String fileName = cDate.getTime() + baseName.hashCode() + getSuffix(baseName);

        String filePath = IMGDIR + "/" + fileName;
        return filePath;
    }

    private String getSuffix(String fileName){
	int index = fileName.lastIndexOf(".");
        
        if(index == -1){
          return "";
        }

	return fileName.substring(index);
    }

    public void write(String filePath, FormFile fForm) throws IOException { 
        
        //1)ServletContextクラスを利用して絶対パスを取得
        String realPath = context.getRealPath(filePath);
        OutputStream out = new FileOutputStream(realPath);
        
        //2)FormFileクラスを利用してアップロードファイルを書き込む
        out.write(fForm.getFileData(), 0, fForm.getFileSize());
        out.close();
    }
    
}
