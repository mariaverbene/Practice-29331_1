package kafkaspring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("config")
public class ConfigProperties {
private String groupId;
private String key_serializer;
private String key_deserializer;
private String value_serializer;
private String value_deserializer;

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getGroupId() {
        return groupId;
    }

    public void setValue_serializer(String value_serializer) { this.value_serializer = value_serializer; }
    public String getValue_serializer() { return value_serializer; }

    public void setValue_deserializer(String value_deserializer) { this.value_deserializer = value_deserializer; }
    public String getValue_deserializer() { return value_deserializer; }

    public void setKey_serializer(String key_serializer) { this.key_serializer = key_serializer; }
    public String getKey_serializer() { return key_serializer; }

    public void setKey_deserializer(String key_deserializer) { this.key_deserializer = key_deserializer; }
    public String getKey_deserializer() { return key_deserializer; }







}
