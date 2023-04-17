package com.data.datareport.client.controller;


import com.data.datareport.client.dto.ClientDTO;
import com.data.datareport.client.entity.Client;
import com.data.datareport.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    public ClientService clientService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            List<ClientDTO> clients = clientService.saveClient(file);
            System.out.println(clients);
            return ResponseEntity.status(HttpStatus.OK).body(clients.toString());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao carregar o arquivo: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Client>> getAllClients(){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAllClients());
    }
}
