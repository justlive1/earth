package justlive.earth.breeze.snow.common.base.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 各种类型的输入公共接口
 * 
 * @author wubo
 *
 */
public interface SourceStream {

  /**
   * 获取输入流
   * 
   * @return
   * @throws IOException
   */
  InputStream getInputStream() throws IOException;
}
