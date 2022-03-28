package com.catalouge.book.bookcatalouge.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookEventsListener {
    @JmsListener(destination = "book.events.queue")
    public void onMessage(String content) {
        log.info("Message received : " + content);
    }
}
