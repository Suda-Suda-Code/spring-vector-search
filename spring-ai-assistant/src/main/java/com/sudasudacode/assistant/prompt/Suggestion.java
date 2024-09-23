package com.sudasudacode.assistant.prompt;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;


@Getter
@Setter
@Accessors(fluent = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Suggestion {

    String id;

    String name;

    String location;

    List<String> reviews;

    String aiSummary;

    Double rating;

    List<String> keyHighlights;

    List<String> tags;

    List<String> dishesToTry;


}
