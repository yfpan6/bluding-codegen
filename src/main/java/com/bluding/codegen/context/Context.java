package com.bluding.codegen.context;

import com.bluding.codegen.context.model.Configuration;
import com.bluding.codegen.context.model.ModelConfiguration;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by myron.pan on 2017/01/29.
 */
public class Context {

    private static Configuration currConfiguration;
    private static ModelConfiguration modelConfiguration;

    private static final Map<String, Configuration> configurationMap = Maps.newLinkedHashMap();

    public static Configuration getCurrConfiguration() {
        return currConfiguration;
    }

    private static String today;
    static {
        today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
    public static String getToday() {
        return today;
    }

    public static Configuration getCurrConfigOrEmptyConfig() {
        return currConfiguration == null ? new Configuration() : currConfiguration;
    }

    public static void setCurrConfiguration(Configuration configuration) {
        currConfiguration = configuration;
    }

    public static void putConfiguration(Configuration configuration) {
        configurationMap.put(configuration.getId(), configuration);
    }

    public static Configuration getConfiguration(String configGroup) {
        return configurationMap.get(configGroup);
    }

    public static Collection<Configuration> getConfigs() {
        return configurationMap.values();
    }

    public static void removeCurrConfiguration() {
        if (currConfiguration == null) {
            return;
        }
        configurationMap.remove(currConfiguration.getId());
        currConfiguration = null;
    }

    public static ModelConfiguration getModelConfiguration() {
        return modelConfiguration;
    }

    public static void setModelConfiguration(ModelConfiguration modelConfiguration) {
        Context.modelConfiguration = modelConfiguration;
    }

    public static void loadConfigs() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Reader reader = null;
        try {
            File file = new File(Context.class.getClassLoader().getResource("configs.json").getFile());
            if (!file.exists()) {
                file.createNewFile();
            }
            reader = new FileReader(file);
            List<Configuration> configurationList = gsonBuilder.create().fromJson(reader,
                    new TypeToken<List<Configuration>>(){}.getType());
            if (configurationList == null) {
                return;
            }
            configurationList.forEach(configuration -> {
                configurationMap.put(configuration.getId(), configuration);
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveConfig() {
        Writer writer = null;
        try {
            File file = new File(Context.class.getClassLoader().getResource("configs.json").getFile());
            writer = new FileWriter(file);
            Gson gson = new Gson();
            gson.toJson(Context.getConfigs(), writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
