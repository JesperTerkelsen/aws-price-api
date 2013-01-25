/*
 * Copyright 2013 Jesper Terkelsen.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 */
package dk.deck.aws.price.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Jesper Terkelsen
 */
public abstract class AbstractPriceList {
    
    // Httpclient
    private  DefaultHttpClient httpclient;
    protected ObjectMapper mapper;
    
    public AbstractPriceList() {
        httpclient = new DefaultHttpClient();
        mapper = new ObjectMapper();        
    }
    
    protected Map<String, Object> getRegion(String regionName, Map<String, Object> map) {
        AwsRegion awsRegion = AwsRegion.getByRegionId(regionName);
        // Get the region
        Map<String, Object> config = (Map<String, Object>) map.get("config");
        Map<String, Object> regionFound  = getJsonElementFromList(config, "regions", "region", awsRegion.getPriceName());
        return regionFound;
    }

    protected Map<String, Object> getSubElement(Map<String, Object> source, String name){
        return (Map<String, Object>) source.get(name);
    }
    
    protected List<Map<String, Object>> getSubList(Map<String, Object> source, String name){
        List<Map<String, Object>> list = (List<Map<String, Object>>) source.get(name);
        return list;
    }
    
    protected Map<String, Object> getJsonElementFromList(Map<String, Object> source, String listName, String elementPropertyName, String elementValue){
        if (source == null){
            return null;
        }
        List<Map<String, Object>> list = getSubList(source,listName);
        return getJsonElementFromList(list, elementPropertyName, elementValue);
    }
    
    protected Map<String, Object> getJsonElementFromList(List<Map<String, Object>> list, String elementPropertyName, String elementValue){
        if (list == null){
            return null;
        }
        for (Map<String, Object> element : list) {
            String name = (String) element.get(elementPropertyName);
            if (elementValue.equals(name)) {
                return element;
            }
        }
        return null;
    }
    
    protected String getRequestAsString(String url) throws IOException {
        String content = null;
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpGet);
        try {
            // System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new IllegalStateException(response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            } else {
                content = IOUtils.toString(entity.getContent());
            }
            EntityUtils.consume(entity);
        } finally {
            httpGet.releaseConnection();
        }
        return content;
    }
}
