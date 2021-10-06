import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.tangzhangss.commonutils.utils.BaseUtil;

public class BaseTest {

    /**
     * 测试JSON parse走不走set get
       打断点
       要走set 方法
     */
    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.set("A","my is A's val");
        BaseEntity baseEntity = JSONUtil.toBean(obj, BaseEntity.class);
        BaseEntity baseEntity2 = JSON.parseObject(JSONUtil.toJsonStr(obj), BaseEntity.class);

        System.out.println(BaseUtil.sqlHandle(null,"ssss"));
    }
}
