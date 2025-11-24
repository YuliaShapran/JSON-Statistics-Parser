package org.example.json_stat_gen.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "statistics")
public class StatisticsNodeDto {

    @XmlElement(name = "item")
    private List<ItemDto> items;

    public StatisticsNodeDto() {

    }

    public StatisticsNodeDto(List<ItemDto> items) {
        this.items = items;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}
