package com.twillio.sendgrid.model;

import lombok.Data;

import java.awt.*;
import java.util.List;

@Data
public class EmailRequest {
    private  String name;
    private String[] to;
    private  String from;
    private  String subject;
}
