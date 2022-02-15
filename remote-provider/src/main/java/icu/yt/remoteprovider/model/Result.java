package icu.yt.remoteprovider.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yt
 * @date 2022/2/15 13:32
 * 功能说明
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private String code;
    private String msg;
    private T data;

    public static <T> Result<T> success (T data) {
        Result<T> result = new Result<>();
        result.setCode("0");
        result.setMsg("OK");
        result.setData(data);
        return result;
    }

    public static Result<Void> success () {
        Result<Void> result = new Result<>();
        result.setCode("0");
        result.setMsg("OK");
        result.setData(null);
        return result;
    }
}
