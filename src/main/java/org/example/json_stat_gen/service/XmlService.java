package org.example.json_stat_gen.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.example.json_stat_gen.dto.ItemDto;
import org.example.json_stat_gen.dto.StatisticsNodeDto;

import java.io.File;
import java.util.List;

public class XmlService {

    void writeXmlFile(String folder, String attribute, List<ItemDto> items) throws Exception {
        StatisticsNodeDto statisticsNode = new StatisticsNodeDto(items);


        JAXBContext context = JAXBContext.newInstance(StatisticsNodeDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        String fileName = "statistics_by_" + attribute + ".xml";
        File file = java.nio.file.Paths.get(folder, fileName).toFile();
        marshaller.marshal(statisticsNode, file);
    }
}
