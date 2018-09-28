package service;

public interface ILookupService {
    /**
     * Look up by key
     * @param key key to look up
     * @return value
     */
    String lookup(String key);
}
