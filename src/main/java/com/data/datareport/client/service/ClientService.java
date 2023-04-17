package com.data.datareport.client.service;

import com.data.datareport.client.dto.ClientDTO;
import com.data.datareport.client.entity.Client;
import com.data.datareport.client.repository.ClientRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    public ModelMapper modelMapper;
    public List<ClientDTO> saveClient(MultipartFile file) throws Exception{
            List<Client> clients = new ArrayList<>();

            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    // pula a primeira linha do arquivo, que contém os cabeçalhos das colunas
                    continue;
                }

                String name = row.getCell(0).getStringCellValue();
                Double debitValue = Double.valueOf(String.valueOf(row.getCell(1)));

                Client client = Client.builder()
                        .name(name)
                        .debitValue(debitValue)
                        .build();

                clients.add(client);
            }

            List<Client> clientList = clientRepository.saveAll(clients);
            workbook.close();

            return clientList.stream()
                    .map(client -> modelMapper.map(client, ClientDTO.class))
                    .collect(Collectors.toList());
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }
}
