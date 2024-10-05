package com.vaynerakawalo.marketdataingestionservice.shared.nats;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageConsumerTest {

  private static final String TEST_TOPIC = "test_topic";
  @Mock private Connection connection;
  @Mock private Dispatcher dispatcher;
  private TestConsumer consumer;

  @BeforeEach
  void setUp() {
    when(connection.createDispatcher(any())).thenReturn(dispatcher);

    consumer =
        Mockito.spy(new TestConsumer(new ObjectMapper(), connection, TEST_TOPIC, TestDTO.class));
  }

  @Test
  void consumer_createsDispatcherAndAssignsTopic() {
    verify(dispatcher).subscribe(TEST_TOPIC);
  }

  @Test
  void invalidMessage_doesNotCallHandler(@Mock Message message) {
    when(message.getData()).thenReturn(new byte[] {'e', 's', 's', 'a'});

    consumer.onMessage(message);

    verify(consumer, never()).handleMessage(any());
  }

  @Test
  void happyPath_callsHandler(@Mock Message message) throws JsonProcessingException {
    var dto = new TestDTO("TEST_VALUE");

    when(message.getData()).thenReturn(new ObjectMapper().writeValueAsBytes(dto));

    consumer.onMessage(message);

    verify(consumer).handleMessage(argThat(msg -> msg.field().equals(dto.field())));
  }
}

class TestConsumer extends MessageConsumer<TestDTO> {

  public TestConsumer(
      ObjectMapper objectMapper, Connection connection, String topic, Class<TestDTO> clazz) {
    super(objectMapper, connection, topic, clazz);
  }

  @Override
  public void handleMessage(TestDTO entity) {}
}

record TestDTO(String field) {}
