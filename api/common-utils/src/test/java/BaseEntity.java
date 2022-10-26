import cn.hutool.core.util.IdUtil;
import lombok.Data;

import java.util.UUID;


public class BaseEntity {
    private String uuid= IdUtil.randomUUID();
    private String A;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }
}
