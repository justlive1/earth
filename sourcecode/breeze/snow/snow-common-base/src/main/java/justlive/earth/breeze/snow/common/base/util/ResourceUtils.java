package justlive.earth.breeze.snow.common.base.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import justlive.earth.breeze.snow.common.base.constant.BaseConstants;

/**
 * 资源工具类
 * 
 * @author wubo
 *
 */
public class ResourceUtils {

  /**
   * 判断URL是否是jar文件
   * 
   * @param url
   * @return
   */
  public static boolean isJarURL(URL url) {
    String protocol = url.getProtocol();
    return (BaseConstants.URL_PROTOCOL_JAR.equals(protocol)
        || BaseConstants.URL_PROTOCOL_WAR.equals(protocol)
        || BaseConstants.URL_PROTOCOL_ZIP.equals(protocol)
        || BaseConstants.URL_PROTOCOL_VFSZIP.equals(protocol)
        || BaseConstants.URL_PROTOCOL_WSJAR.equals(protocol));
  }

  /**
   * String转换成URI
   * 
   * @param url
   * @return
   * @throws URISyntaxException
   */
  public static URI toURI(String url) throws URISyntaxException {
    return new URI(url.replace(" ", "%20"));
  }

  /**
   * URL转换成URI
   * 
   * @param url
   * @return
   * @throws URISyntaxException
   */
  public static URI toURI(URL url) throws URISyntaxException {
    return new URI(url.toString().replace(" ", "%20"));
  }

  /**
   * 获取相对资源路径
   * 
   * @param rootPath
   * @param relative
   * @return
   */
  public static String relativePath(String rootPath, String relative) {
    int last = rootPath.lastIndexOf(BaseConstants.PATH_SEPARATOR);
    String newPath = rootPath;
    if (last > -1) {
      newPath = rootPath.substring(0, last);
      if (!relative.startsWith(BaseConstants.PATH_SEPARATOR)) {
        newPath += BaseConstants.PATH_SEPARATOR;
      }
      newPath += relative;
    }
    return newPath;
  }
}
