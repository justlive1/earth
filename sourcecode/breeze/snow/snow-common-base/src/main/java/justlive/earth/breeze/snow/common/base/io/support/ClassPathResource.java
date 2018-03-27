package justlive.earth.breeze.snow.common.base.io.support;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import justlive.earth.breeze.snow.common.base.constant.BaseConstants;
import justlive.earth.breeze.snow.common.base.io.SourceResource;
import justlive.earth.breeze.snow.common.base.util.Checks;

/**
 * classpath路径下的资源
 * 
 * @author wubo
 *
 */
public class ClassPathResource implements SourceResource {

  private String path;
  private ClassLoader classLoader;
  private Class<?> clazz;

  /**
   * 创建一个{@code ClassPathResource}<br>
   * classloader为null可能资源访问不了
   * 
   * @param path
   */
  public ClassPathResource(String path) {
    this(path, (ClassLoader) null);
  }

  /**
   * 使用{@code ClassLoader}创建{@code ClassPathResource}
   * 
   * @param path
   * @param classLoader
   */
  public ClassPathResource(String path, ClassLoader classLoader) {
    this.path = this.cutRootPath(Checks.notNull(path));;
    this.classLoader = classLoader;
  }

  /**
   * 使用{@code Class}创建{@code ClassPathResource}
   * 
   * @param path
   * @param clazz
   */
  public ClassPathResource(String path, Class<?> clazz) {
    this.path = this.cutRootPath(Checks.notNull(path));
    this.clazz = clazz;
  }

  /**
   * 去除路径起始的/
   * 
   * @param path
   * @return
   */
  String cutRootPath(String path) {
    String usePath = path;
    if (usePath.startsWith(BaseConstants.ROOT_PATH)) {
      usePath = usePath.substring(BaseConstants.ROOT_PATH.length());
    }
    return usePath;
  }

  @Override
  public String path() {
    return this.path;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    InputStream is;
    if (this.clazz != null) {
      is = this.clazz.getResourceAsStream(this.path);
    } else if (this.classLoader != null) {
      is = this.classLoader.getResourceAsStream(this.path);
    } else {
      is = ClassLoader.getSystemResourceAsStream(this.path);
    }
    if (is == null) {
      throw new FileNotFoundException(this.path + " cannot be opened because it does not exist");
    }
    return is;
  }

  @Override
  public boolean isFile() {
    URL url = getURL0();
    if (url != null) {
      return BaseConstants.URL_PROTOCOL_FILE.equals(url.getProtocol());
    }
    return false;
  }

  @Override
  public File getFile() throws IOException {
    if (isFile()) {
      try {
        return new File(new URI(this.path.replace(" ", "%20")).getSchemeSpecificPart());
      } catch (URISyntaxException e) {
        return new File(getURL0().getFile());
      }
    }
    return null;
  }

  /**
   * 获取类加载器
   * 
   * @return
   */
  public ClassLoader getClassLoader() {
    if (this.clazz != null) {
      return this.clazz.getClassLoader();
    }
    return this.classLoader;
  }

  private URL getURL0() {
    if (this.clazz != null) {
      return this.clazz.getResource(this.path);
    } else if (this.classLoader != null) {
      return this.classLoader.getResource(this.path);
    } else {
      return ClassLoader.getSystemResource(this.path);
    }
  }

  /**
   * 获取资源URL
   * 
   * @return
   *
   */
  @Override
  public URL getURL() throws IOException {
    URL url = this.getURL0();
    if (url == null) {
      throw new FileNotFoundException(this.path + " cannot be found");
    }
    return url;
  }

}
