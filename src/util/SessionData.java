package util;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/** セッションテストデータ **/
public class SessionData implements HttpSessionBindingListener {
  /** セッション開始時の処理 **/
  public void valueBound (HttpSessionBindingEvent event) {
    System.out.println("valueBound");
  }

  /** セッション終了時の処理 **/
  public void valueUnbound (HttpSessionBindingEvent event) {
    System.out.println("valueUnBound");
  }
}