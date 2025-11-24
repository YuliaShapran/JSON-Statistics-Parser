package org.example.json_stat_gen.dto;


import jakarta.xml.bind.annotation.XmlAttribute;

public class ItemDto {

    @XmlAttribute
    private String value;

    @XmlAttribute
    private Integer count;

    public ItemDto() {

    }

    public ItemDto(String value, Integer count) {
        this.value = value;
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public Integer getCount() {
        return count;
    }

}
