package com.sudasudacode.assistant.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRequest {

    private String message;

    private List<Attachments> attachments;
}
