package acc;

import lombok.Data;

import java.util.HashMap;

/**
 * @Auther: Lee
 * @Date 2020/6/19 14:29
 * @Description:
 */
@Data
public class JsonResult extends HashMap{

    private boolean success;
    private String message;
    private Object data;

    public JsonResult(boolean success) {
        this.success = success;
        this.message = success == true ? "操作成功" : "操作失败";
        super.put("success", success);
        super.put("message", message);
    }

    public JsonResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        super.put("success", success);
        super.put("message", message);
    }

    public JsonResult(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        super.put("success", success);
        super.put("message", message);
        super.put("data", data);
    }

    public static JsonResult successResult() {
        return new JsonResult(true);
    }

    public static JsonResult successResult(Object data) {
        return new JsonResult(true, "操作成功", data);
    }

    public static JsonResult successResult(String message, Object data) {
        return new JsonResult(true, message, data);
    }

    public static JsonResult failResult() {
        return new JsonResult(false);
    }

    public static JsonResult failResult(String message) {
        return new JsonResult(false, message);
    }
}
