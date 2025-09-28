package com.email.email_writer_kt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
public class MailGeneratorModel {

    private String mailContent;
    private String tone;

}
