package com.krishinext.services;

import com.krishinext.models.Crop;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.messaging.ChangeStreamRequest;
import org.springframework.data.mongodb.core.messaging.MessageListener;
import org.springframework.data.mongodb.core.messaging.MessageListenerContainer;
import org.springframework.data.mongodb.core.messaging.DefaultMessageListenerContainer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockUpdateService implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void run(String... args) throws Exception {
        MessageListenerContainer container = new DefaultMessageListenerContainer(mongoTemplate);
        container.start();

        MessageListener<ChangeStreamDocument<Document>, Crop> listener = message -> {
            Crop crop = message.getBody();
            if (crop != null) {
                messagingTemplate.convertAndSend("/topic/stockUpdate", crop.getQuantity());
            }
        };

        ChangeStreamRequest<Crop> request = ChangeStreamRequest.builder(listener)
                .collection("crops")
                .build();

        container.register(request, Crop.class);
    }
}
