package net.oschina.git.justlive1.breeze.lighting.chained.core.step;

import java.util.HashMap;
import java.util.Map;

/**
 * 步骤上下文
 * 
 * @author wubo
 *
 */
public class StepContext {

    public static final String REMOTE = "REMOTE";
    public static final String REMOTE_FILE = "REMOTE_FILE";
    public static final String TARGET_FILE = "TARGET_FILE";
    public static final String TARGET_FILE_NAME = "TARGET_FILE_NAME";
    public static final String BUILD_DIR = "BUILD_DIR";
    public static final String RELEASE_DIR = "RELEASE_DIR";
    public static final String PROJECT = "PROJECT";
    public static final String PROJECT_NAME = "PROJECT_NAME";
    public static final String DEPLOY = "DEPLOY";

    private Map<String, Object> data;

    public StepContext() {
        data = new HashMap<>();
    }

    public Object put(String key, Object value) {
        return this.data.put(key, value);
    }

    public Object get(String key) {
        return this.data.get(key);
    }

    public void clear() {
        this.data.clear();
    }
}
