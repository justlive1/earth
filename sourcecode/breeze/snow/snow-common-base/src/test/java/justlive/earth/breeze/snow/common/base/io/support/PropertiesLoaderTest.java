package justlive.earth.breeze.snow.common.base.io.support;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import org.junit.Test;

public class PropertiesLoaderTest {

  String classpath = "classpath:/config/config.properties";
  String defaultPath = "/config/config.properties";
  String dir = "/tmp";
  String path = dir + "/config.properties";
  String filePath = "file:" + path;


  @Test
  public void testDefault() {

    PropertiesLoader loader = new PropertiesLoader(defaultPath);

    Properties props = loader.props();

    assertEquals("k001", props.getProperty("k1"));
    assertEquals("k002", props.getProperty("k2"));

  }

  @Test
  public void testClasspath() {

    PropertiesLoader loader = new PropertiesLoader(classpath);

    Properties props = loader.props();

    assertEquals("k001", props.getProperty("k1"));
    assertEquals("k002", props.getProperty("k2"));

  }

  @Test
  public void testFile() {

    makeFile();

    PropertiesLoader loader = new PropertiesLoader(filePath);

    Properties props = loader.props();

    assertEquals("k001", props.getProperty("k1"));
    assertEquals("k002", props.getProperty("k2"));

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
