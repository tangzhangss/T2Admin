package com.tangzhangss.commonutils.uidgenerator;

import com.xfvape.uid.UidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UidGeneratorService {
    @Autowired
    UidGenerator uidGenerator;

    public Long getuid(){
        return uidGenerator.getUID();
    }
}
