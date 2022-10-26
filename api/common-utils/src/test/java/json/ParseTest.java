package json;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;

public class ParseTest {


    public static void main(String[] args) {
        String json =  "{\n" +
                "    \"uGuid\": \"069f90d0-2acc-46f2-bc9a-dbc67ea5d627\",\n" +
                "    \"sRemark\": null,\n" +
                "    \"uClientId\": \"d4b7a028-6474-4693-a569-d586acd23ef6\",\n" +
                "    \"sSource\": null,\n" +

                "}";
        JSONObject object = JSONUtil.parseObj(json,true);
        System.out.println(object);

        JsonDTO jsonDTO = JSONUtil.toBean(json, JsonDTO.class);

        System.out.println(jsonDTO);

        JSONObject o = new JSONObject();
        o.set("a",null);

        System.out.println(o);

    }
}
