package justlive.earth.breeze.snow.common.base.io.support;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import justlive.earth.breeze.snow.common.base.constant.BaseConstants;
import justlive.earth.breeze.snow.common.base.io.SourceResource;
import justlive.earth.breeze.snow.common.base.util.PathMatcher;
import lombok.extern.slf4j.Slf4j;

/**
 * properties配置文件加载器<br>
 * 支持classpath下配置文件，例如 classpath:/config/dev.properties,classpath*:/config/*.properties<br>
 * 支持文件系统下配置文件，例如 file:/home/dev.properties, file:D:/conf/dev.properties
 * 
 * @author wubo
 *
 */
@Slf4j
public class PropertiesLoader {

  public static final String ALL_CLASSPATH_PREFIX = "classpath*:";
  public static final String CLASSPATH_PREFIX = "classpath:";
  public static final String FILE_PREFIX = "file:";

  /**
   * 配置路径
   */
  private String[] locations;

  private Properties props = new Properties();

  private ClassLoader loader;

  private PathMatcher matcher = new PathMatcher();

  private List<SourceResource> resources = new ArrayList<>();

  /**
   * 使用路径创建{@code PropertiesLoader}
   * 
   * @param locations
   */
  public PropertiesLoader(String... locations) {
    this(ClassLoader.getSystemClassLoader(), locations);
  }

  /**
   * 使用路径创建{@code PropertiesLoader}
   * 
   * @param loader
   * @param locations
   */
  public PropertiesLoader(ClassLoader loader, String... locations) {
    this.locations = locations;
    this.loader = loader;
    this.init();
  }

  /**
   * 获取属性值
   * 
   * @return
   */
  public Properties props() {
    return this.props;
  }

  /**
   * 获取资源列表
   * 
   * @return
   */
  public List<SourceResource> resources() {
    return this.resources;
  }

  void init() {
    this.resources.addAll(this.parse(this.locations));
    this.transfer();
  }

  /**
   * 解析路径
   */
  List<SourceResource> parse(String... locations) {
    List<SourceResource> list = new LinkedList<>();
    for (String location : locations) {
      if (location.startsWith(ALL_CLASSPATH_PREFIX)) {
        list.addAll(
            this.resolveAllClassPathResource(location.substring(ALL_CLASSPATH_PREFIX.length())));
      } else if (location.startsWith(CLASSPATH_PREFIX)) {
        list.addAll(this.resolveClassPathResource(location.substring(CLASSPATH_PREFIX.length())));
      } else if (location.startsWith(FILE_PREFIX)) {
        list.addAll(this.resolveFileSystemResource(location));
      } else {
        list.addAll(this.resolveClassPathResource(location));
      }
    }
    return list;
  }

  /**
   * 处理所有classpath下的资源
   * 
   * @param location
   */
  List<SourceResource> resolveAllClassPathResource(String location) {
    List<SourceResource> list = new LinkedList<>();
    if (matcher.isPattern(location)) {
      try {
        list.addAll(this.findMatchPath(location));
      } catch (IOException e) {
        if (log.isWarnEnabled()) {
          log.warn("location [{}] cannot find resources", location, e);
        }
      }
    } else {
      if (location.startsWith(BaseConstants.ROOT_PATH)) {
        location = location.substring(BaseConstants.ROOT_PATH.length());
      }
      Enumeration<URL> resources = null;
      try {
        resources = loader.getResources(location);
      } catch (IOException e) {
        if (log.isWarnEnabled()) {
          log.warn("location [{}] cannot find resources", location, e);
        }
      }
      if (resources == null) {
        return list;
      }
      while (resources.hasMoreElements()) {
        URL url = resources.nextElement();
        list.add(new UrlResource(url));
      }
    }
    return list;
  }

  /**
   * 处理classpath下的资源
   * 
   * @param location
   */
  List<SourceResource> resolveClassPathResource(String location) {
    List<SourceResource> list = new LinkedList<>();
    if (matcher.isPattern(location)) {
      try {
        list.addAll(this.findMatchPath(location));
      } catch (IOException e) {
        if (log.isWarnEnabled()) {
          log.warn("location [{}] cannot find resources", location, e);
        }
      }
    } else {
      list.add(new ClassPathResource(location, loader));
    }
    return list;
  }

  /**
   * 处理文件系统下的资源
   * 
   * @param location
   */
  List<SourceResource> resolveFileSystemResource(String location) {
    List<SourceResource> list = new LinkedList<>();
    if (matcher.isPattern(location)) {
      try {
        list.addAll(this.findMatchPath(location));
      } catch (IOException e) {
        if (log.isWarnEnabled()) {
          log.warn("location [{}] cannot find resources", location, e);
        }
      }
    } else {
      list.add(new FileSystemResource(location.substring(FILE_PREFIX.length())));
    }
    return list;
  }

  /**
   * 将资源转换成properties
   */
  void transfer() {
    for (SourceResource resource : this.resources) {
      try {
        // TODO 设置编码
        props.load(resource.getInputStream());
      } catch (IOException e) {
        if (log.isWarnEnabled()) {
          log.warn("resource [{}] read error", resource.path(), e);
        }
      }
    }
  }

  /**
   * 获取匹配的路径
   * 
   * @param location
   * @return
   * @throws IOException
   */
  List<SourceResource> findMatchPath(String location) throws IOException {
    List<SourceResource> all = new LinkedList<>();
    String rootPath = this.getRootDir(location);
    String subPattern = location.substring(rootPath.length());
    List<SourceResource> rootResources = this.parse(rootPath);
    for (SourceResource resource : rootResources) {
      URL rootUrl = resource.getURL();
      if (BaseConstants.URL_PROTOCOL_FILE.equals(rootUrl.getProtocol())) {
        all.addAll(this.findFileMatchPath(resource, subPattern));
      } else {
        // TODO
      }
    }
    return all;
  }

  /**
   * 获取资源下匹配的文件
   * 
   * @param resource
   * @param subPattern
   * @return
   */
  List<SourceResource> findFileMatchPath(SourceResource resource, String subPattern) {
    List<SourceResource> list = new LinkedList<>();
    File rootDir;
    try {
      rootDir = resource.getFile().getAbsoluteFile();
    } catch (IOException e) {
      if (log.isWarnEnabled()) {
        log.warn("resource [{}] cannot converter to File", resource.path(), e);
      }
      return list;
    }
    Set<File> matchedFiles = findMatchedFiles(rootDir, subPattern);
    for (File file : matchedFiles) {
      list.add(new FileSystemResource(file));
    }
    return list;
  }

  /**
   * 获取目录下匹配的文件
   * 
   * @param rootDir
   * @param subPattern
   * @return
   */
  Set<File> findMatchedFiles(File rootDir, String subPattern) {
    Set<File> files = new LinkedHashSet<>();
    if (!rootDir.exists() || !rootDir.isDirectory() || !rootDir.canRead()) {
      if (log.isWarnEnabled()) {
        log.warn("dir [{}] cannot execute search operation", rootDir.getPath());
      }
      return files;
    }
    String fullPattern =
        rootDir.getAbsolutePath().replace(File.pathSeparator, BaseConstants.PATH_SEPARATOR);
    if (!subPattern.startsWith(BaseConstants.PATH_SEPARATOR)) {
      fullPattern += BaseConstants.PATH_SEPARATOR;
    }
    fullPattern += subPattern.replace(File.pathSeparator, BaseConstants.PATH_SEPARATOR);
    this.searchMatchedFiles(fullPattern, rootDir, files);
    return files;
  }

  /**
   * 递归查询匹配的文件
   * 
   * @param fullPattern
   * @param dir
   * @param files
   */
  void searchMatchedFiles(String fullPattern, File dir, Set<File> files) {
    if (log.isDebugEnabled()) {
      log.debug("search files under dir [{}]", dir);
    }
    File[] dirContents = dir.listFiles();
    for (File content : dirContents) {
      String currentPath =
          content.getAbsolutePath().replace(File.pathSeparator, BaseConstants.PATH_SEPARATOR);
      if (content.isDirectory()) {
        if (!content.canRead() && log.isDebugEnabled()) {
          log.debug("dir [{}] has no read permission, skip");
        } else {
          this.searchMatchedFiles(fullPattern, content, files);
        }
      } else if (this.matcher.match(fullPattern, currentPath)) {
        files.add(content);
      }
    }
  }

  /**
   * 获取不含通配符的根路径
   * 
   * @param location
   * @return
   */
  String getRootDir(String location) {
    int prefixEnd = location.indexOf(BaseConstants.PROTOCOL_SEPARATOR) + 1;
    int rootDirEnd = location.length();
    while (rootDirEnd > prefixEnd && matcher.isPattern(location.substring(prefixEnd, rootDirEnd))) {
      rootDirEnd = location.lastIndexOf(BaseConstants.PATH_SEPARATOR, rootDirEnd - 2) + 1;
    }
    if (rootDirEnd == 0) {
      rootDirEnd = prefixEnd;
    }
    return location.substring(0, rootDirEnd);
  }


}
