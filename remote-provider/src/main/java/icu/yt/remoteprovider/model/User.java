package icu.yt.remoteprovider.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author yt
 * @date 2022/2/22 10:31
 * 功能说明
 */
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Long id;
    private String username;
    private String password;
    private Date createTime;
    private Date updateTime;
}
