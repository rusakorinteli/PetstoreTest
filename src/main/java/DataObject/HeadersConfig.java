package DataObject;

import java.util.HashMap;
import java.util.Map;

public class HeadersConfig {
    public Map<String, String> header;

    public HeadersConfig() {
        header = new HashMap<>();
        header.put("connection", "keep-alive");
        header.put("content-type", "application/json; charset=utf-8");
    }

    public Map<String, String> getHeaders() {
        return header;
    }
}
