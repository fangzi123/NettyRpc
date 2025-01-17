/*
 * Copyright (c) 2019 Baidu, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this list except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nettyrpc.naming;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * format is zookeeper://127.0.0.1:2181
 */
@Data
@Slf4j
public class RegistryCenterAddress {
    private String schema;
    /**
     * we do not parse host and port,
     * because different naming url has different formats:
     * "127.0.0.1:8002,127.0.0.1:8003"
     * "test.bj:portTag"
     * "brpc.com"
     * "127.0.0.1:8080"
     */
    private String hostPorts;
    private String path;
    private Map<String, Object> queryMap = new HashMap<String, Object>();

    public RegistryCenterAddress(String uri) {
        // schema
        int index = uri.indexOf("://");
        if (index < 0) {
            throw new IllegalArgumentException("invalid uri:" + uri);
        }
        this.schema = uri.substring(0, index).toLowerCase();
        // hostPorts
        int index2 = uri.indexOf('/', index + 3);
        int index3 = uri.indexOf('?', index + 3);
        if (index2 > 0) {
            this.hostPorts = uri.substring(index + 3, index2);
        } else if (index3 > 0) {
            this.hostPorts = uri.substring(index + 3, index3);
        } else {
            this.hostPorts = uri.substring(index + 3);
        }

        // path
        if (index2 > 0) {
            if (index3 > 0) {
                this.path = uri.substring(index2, index3);
            } else {
                this.path = uri.substring(index2);
            }
        } else {
            this.path = "/";
        }

        // query
        if (index3 > 0) {
            String query = uri.substring(index3 + 1);
            String[] querySplits = query.split("&");
            for (String kv : querySplits) {
                String[] kvSplit = kv.split("=");
                queryMap.put(kvSplit[0], kvSplit[1]);
            }
        }
    }

    public void addParameter(String key, Object value) {
        queryMap.put(key, value);
    }

    public Object getParameter(String key) {
        return queryMap.get(key);
    }

    public Object getParameter(String key, Object defaultValue) {
        Object value = queryMap.get(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    public int getIntParameter(String key, int defaultValue) {
        Object value = queryMap.get(key);
        if (value != null) {
            return Integer.valueOf((String) value);
        } else {
            return defaultValue;
        }
    }

    public String getStringParameter(String key, String defaultValue) {
        Object value = queryMap.get(key);
        if (value != null) {
            return (String) value;
        } else {
            return defaultValue;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(schema).append("://");
        if (!StringUtils.isEmpty(hostPorts)) {
            sb.append(hostPorts);
        }
        sb.append(path);
        if (queryMap.size() > 0) {
            sb.append("?");
            for (Map.Entry<String, Object> entry : queryMap.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return sb.toString();
    }
}
