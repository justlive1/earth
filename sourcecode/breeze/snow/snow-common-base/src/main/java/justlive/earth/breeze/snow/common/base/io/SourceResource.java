package justlive.earth.breeze.snow.common.base.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 各种类型的资源公共接口，包括文件系统的文件或者是classpath下的资源
 * 
 * @author wubo
 *
 */
public interface SourceResource extends SourceStream {

  /**
   * 是否存在
   * 
   * @return
   */
  default boolean exist() {
    try {
      return getFile().exists();
    } catch (IOException e) {
      try {
        InputStream in = getInputStream();
        in.close();
        return true;
      } catch (IOException e1) {
        return false;
      }
    }
  }

  /**
   * 是否文件
   * 
   * @return
   */
  boolean isFile();

  /**
   * 获取文件
   * 
   * @return
   */
  File getFile() throws IOException;
}
