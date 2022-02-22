package icu.yt.remoteprovider.dao;

import icu.yt.remoteprovider.model.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author yt
 * @date 2022/2/22 10:35
 * 功能说明
 */
@Repository
public interface UserDao extends BaseMapper<User> {
}
