/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.features.topology.app.internal.support;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opennms.features.topology.api.IconRepository;

public class IconRepositoryManager {
    
    private class ConfigIconRepository implements IconRepository{

        private Map<String, String> m_iconMap = new HashMap<String, String>();

        @Override
        public boolean contains(String type) {
            return m_iconMap.containsKey(type);
        }
        
        @Override
        public String getIconUrl(String type) {
            return m_iconMap.get(type);
        }
        
        public void addIconConfig(String key, String url) {
            if(m_iconMap.containsKey(key)) {
                m_iconMap.remove(key);
            }
            m_iconMap.put(key, url);
        }
        
    }
    
    private List<IconRepository> m_iconRepos = new ArrayList<IconRepository>();
    private ConfigIconRepository m_configRepo = new ConfigIconRepository();
    
    public IconRepositoryManager() {
        m_iconRepos.add(m_configRepo);
    }
    
    public void addRepository(IconRepository iconRepo) {
        m_iconRepos.add(iconRepo);
    }
    
    public void onBind(IconRepository iconRepo) {
        addRepository(iconRepo);
    }
    
    public void onUnbind(IconRepository iconRepo) {
        m_iconRepos.remove(iconRepo);
    }
    
    public String lookupIconUrlForExactKey(String key) {
        for(IconRepository iconRepo : m_iconRepos) {
            if(iconRepo.contains(key)) {
                return iconRepo.getIconUrl(key);
            }
        }
        return null;
    }
    
    public String findIconUrlByKey(String key) {
    	
    	// if the exact key is configured then use it
    	String iconUrl = lookupIconUrlForExactKey(key);
    	if (iconUrl != null) {
    		return iconUrl;
    	}
        
    	// 
        int lastColon = key.lastIndexOf(':');
        if ("default".equals(key)) {
        	// we got here an no default icon was registered!!
        	return "theme://images/server.png";
        } else if (lastColon == -1) {
        	// no colons in key so just return 'default' icon
        	return findIconUrlByKey("default");
        } else {
        	// remove the segment following the last colon
        	String newPrefix = key.substring(0, lastColon);
        	// see if there an icon registered as <prefix>:default
			String icon = findIconUrlByKey(newPrefix+":default");
			// if ':default' icon exists return it otherwise recurse with just the prefix
			return icon != null ? icon : findIconUrlByKey(newPrefix);
        }
        
    }

    public void updateIconConfig(Dictionary<Object, Object> properties) {
        Enumeration<Object> keys = properties.keys();
        
        while(keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String url = (String)properties.get(key);
            m_configRepo.addIconConfig(key, url);
        }
    }
}
