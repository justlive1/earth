package net.oschina.git.justlive1.breeze.lighting.chained.core.step;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.oschina.git.justlive1.breeze.snow.common.base.exception.Exceptions;

/**
 * 解压
 * 
 * @author wubo
 *
 */
@Service
@Slf4j
public class UnzipStep extends BaseStep {

    @Override
    public String unqueId() {
        return beanName;
    }

    @Override
    public void handle(StepContext ctx) {

        String filePath = (String) ctx.get(StepContext.TARGET_FILE);
        String fileName = (String) ctx.get(StepContext.TARGET_FILE_NAME);
        String extractPath = coreProps.getTmpPath() + fileName;
        try {
            ZipFile zipFile = new ZipFile(filePath);
            zipFile.extractAll(coreProps.getTmpPath() + fileName);
        } catch (ZipException e) {
            log.error("", e);
            throw Exceptions.wrap(e);
        }

        ctx.put(StepContext.BUILD_DIR, extractPath);
    }

}
