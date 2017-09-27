package com.ebest.frame.annomationapilib.route.parser;

import android.net.Uri;
import android.text.TextUtils;

import com.ebest.frame.annomationapilib.route.tools.Utils;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * A parser to parse uri to scheme/host/params .etc
 * Created by lzh on 16/9/5.
 */
public class URIParser {

    private Uri uri;
    private String route;
    private Map<String,String> params;

    public URIParser(Uri uri) {
        this.uri = uri;
        parse();
    }

    private void parse() {
        this.route = Utils.format(uri.getScheme() + "://" + uri.getHost() + uri.getPath());
        String query = uri.getEncodedQuery();
        if (!TextUtils.isEmpty(query)) {
            params = parseParams(query);
        } else {
            params = new HashMap<>();
        }
    }

    /**
     * Parse params form query string
     * <p>
     * To support parse list to bundle,use {@link IdentityHashMap} to hold key-value
     * </p>
     * @param query query in uri
     * @return a map contains key-value data parsed by query in uri
     */
    static Map<String,String> parseParams(String query) {
        Map<String,String> params = new IdentityHashMap<>();
        String[] split = query.split("&");
        for (String param : split) {
            if (!param.contains("=")) {
                continue;
            }
            int index = param.indexOf("=");
            //noinspection RedundantStringConstructorCall
            params.put(new String(param.substring(0, index)), Uri.decode(param.substring(index + 1)));
        }
        return params;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getRoute() {
        return route;
    }
}
