package com.example.agora.redis;

public interface RedisUtilService {
    public String getData(String key);
//    public void setData(String key, String value);
    public String setDataExpire(String key, String value, long exp);
//    public void deleteData(String key);
}
