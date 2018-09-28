package service;

import annotation.RpcService;

import java.util.HashMap;
import java.util.Map;

@RpcService(ILookupService.class)
public class LookUpService implements ILookupService{

    Map<String, String> resultMap = new HashMap<>();

    public LookUpService() {
        initData();
    }

    private void initData() {
        resultMap.put("A", "Hello Stranger");
        resultMap.put("B", "This is some time");
        resultMap.put("C", "And a better life");
        resultMap.put("D", "Just some test data");
    }

    @Override
    public String lookup(String key) {
        return resultMap.getOrDefault(key, "null String");
    }

}
