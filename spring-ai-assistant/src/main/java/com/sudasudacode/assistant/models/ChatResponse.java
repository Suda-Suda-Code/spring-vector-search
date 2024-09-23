package com.sudasudacode.assistant.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.sudasudacode.assistant.prompt.ReplyMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ChatResponse {

    private ReplyMessage reply;

}
