package de.othr.im.controller;


import de.othr.im.repository.StudentProfessorRepository;
import de.othr.im.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransferController {
    @Autowired
    TransferRepository transferRepository;
    @Autowired
    StudentProfessorRepository studentProfessorRepository;
}
