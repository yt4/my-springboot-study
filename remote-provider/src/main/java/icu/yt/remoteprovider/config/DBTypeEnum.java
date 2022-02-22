package icu.yt.remoteprovider.config;

/**
 * @author yt
 * @date 2022/2/22 9:42
 * 功能说明
 */
public enum DBTypeEnum {
    /**
     * 主：写操作使用数据源
     */
    MASTER,
    /**
     * 从：读操作使用数据源
     */
    SLAVE;
}
