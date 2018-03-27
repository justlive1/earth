package justlive.earth.breeze.snow.common.base.io.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import justlive.earth.breeze.snow.common.base.constant.BaseConstants;
import justlive.earth.breeze.snow.common.base.io.SourceResource;
import justlive.earth.breeze.snow.common.base.util.Checks;

/**
 * {@code URL} 类型的资源
 * 
 * @author wubo
 *
 */
public class UrlResource implements SourceResource {

  private URL url;

  /**
   * 使用{@code URL}创建{@code UrlResource}
   * 
   * @param url
   */
  public UrlResource(URL url) {
    this.url = Checks.notNull(url);
  }

  @Override
  public InputStream getInputStream() throws IOException {
    URLConnection con = this.url.openConnection();
    try {
      return con.getInputStream();
    } catch (IOException ex) {
      // 关闭HPPT连接
      if (con instanceof HttpURLConnection) {
        ((HttpURLConnection) con).disconnect();
      }
      throw ex;
    }
  }

  @Override
  public String path() {
    return url.toString();
  }

  @Override
  public boolean isFile() {
    return BaseConstants.URL_PROTOCOL_FILE.equals(url.getProtocol());
  }

  @Override
  public File getFile() throws IOException {
    if (isFile()) {
      try {
        return new File(new URI(this.url.toString().replace(" ", "%20")).getSchemeSpecificPart());
      } catch (URISyntaxException e) {
        return new File(url.getFile());
      }
    }
    return null;
  }

  @Override
  public URL getURL() throws IOException {
    return this.url;
  }
}
