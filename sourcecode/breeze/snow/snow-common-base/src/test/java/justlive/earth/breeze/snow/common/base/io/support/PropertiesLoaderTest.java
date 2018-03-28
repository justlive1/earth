package justlive.earth.breeze.snow.common.base.io.support;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import org.junit.Test;
import justlive.earth.breeze.snow.common.base.exception.CodedException;

public class PropertiesLoaderTest {

  String classpath = "classpath:/config/*.properties";
  String defaultPath = "/config/config.properties";
  String dir = "/tmp";
  String path = dir + "/config.properties";
  String filePath = "file:" + path;


  @Test
  public void testDefault() {

    PropertiesLoader loader = new PropertiesLoader(defaultPath);
    loader.init();

    Properties props = loader.props();

    assertEquals("k001", props.getProperty("k1"));
    assertEquals("k002", props.getProperty("k2"));

  }

  @Test
  public void testClasspath() {

    PropertiesLoader loader = new PropertiesLoader(classpath);
    loader.init();

    Properties props = loader.props();

    assertEquals("k001", props.getProperty("k1"));
    assertEquals("k002", props.getProperty("k2"));
    assertEquals("k003", props.getProperty("k3"));

  }

  @Test
  public void testFile() {

    makeFile();

    PropertiesLoader loader = new PropertiesLoader(filePath);
    loader.init();

    Properties props = loader.props();

    assertEquals("k001", props.getProperty("k1"));
    assertEquals("k002", props.getProperty("k2"));

  }

  @Test(expected = CodedException.class)
  public void testNotFound() {
    PropertiesLoader loader = new PropertiesLoader("classpath:/xxx.properties");
    loader.init();
  }

  @Test
  public void testIgnoreNotFound() {
    PropertiesLoader loader = new PropertiesLoader("classpath:/xxx.properties");
    loader.ignoreNotFound = true;
    loader.init();
  }

  void makeFile() {
    File dir = new File(this.dir);
    File target = new File(path);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    if (target.exists()) {
      target.delete();
    }
    ClassPathResource resource = new ClassPathResource(defaultPath);
    try {
      Files.copy(resource.getInputStream(), target.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
    target.deleteOnExit();
  }

}
