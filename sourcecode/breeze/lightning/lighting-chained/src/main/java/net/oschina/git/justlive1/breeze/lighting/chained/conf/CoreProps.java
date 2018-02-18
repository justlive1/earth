package net.oschina.git.justlive1.breeze.lighting.chained.conf;

import java.util.Map;

import lombok.Data;

/**
 * 核心配置属性
 * 
 * @author wubo
 *
 */
@Data
public class CoreProps {

    /**
     * 临时路径
     * 
     */
    private String tmpPath;

    /**
     * 日志路径
     */
    private String logPath;

    /**
     * 远程配置
     */
    private Map<String, Remote> remotes;

    /**
     * 构建配置
     */
    private Map<String, Build> builds;

    /**
     * 部署配置
     */
    private Map<String, Deploy> deploys;

    /**
     * 远程仓库
     * 
     * @author wubo
     *
     */
    @Data
    public static class Remote {

        public enum TYPE {
            GITHUB
        }

        /**
         * 远程类型
         */
        private String type;

        /**
         * 代理
         */
        private String agent;

        /**
         * 触发事件
         */
        private String event;

        /**
         * 分支前缀
         */
        private String refPrefix;

        /**
         * 文件前缀
         */
        private String filePrefix;

        /**
         * 文件后缀
         */
        private String fileSuffix;

    }

    /**
     * 构建方式
     * 
     * @author wubo
     *
     */
    @Data
    public static class Build {

        public enum TYPE {
            NODE
        }

        private String type;

        private String handle;

        private String envPath;

        private String[] cmds;
    }

    /**
     * 部署方式
     * 
     * @author wubo
     *
     */
    @Data
    public static class Deploy {

        public enum TYPE {
            STATIC
        }

        private String type;

        private String handle;

        private String envPath;

        private String[] cmds;
    }
}
