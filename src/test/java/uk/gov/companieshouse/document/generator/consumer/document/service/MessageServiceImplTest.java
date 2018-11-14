package uk.gov.companieshouse.document.generator.consumer.document.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.document.generator.consumer.avro.AvroSerializer;
import uk.gov.companieshouse.document.generator.consumer.document.models.GenerateDocumentResponse;
import uk.gov.companieshouse.document.generator.consumer.document.models.Links;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DeserialisedKafkaMessage;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationCompleted;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationFailed;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationStarted;
import uk.gov.companieshouse.document.generator.consumer.document.service.impl.MessageServiceImpl;
import uk.gov.companieshouse.document.generator.consumer.exception.MessageCreationException;
import uk.gov.companieshouse.kafka.message.Message;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageServiceImplTest {

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private AvroSerializer avroSerializer;

    private static final String STARTED_PRODUCER_TOPIC = "document-generation-started";
    private static final String FAILED_PRODUCER_TOPIC = "document-generation-failed";
    private static final String COMPLETED_PRODUCER_TOPIC = "document-generation-completed";

    private static final byte[] STARTED_BYTES = "Unit test started message".getBytes();
    private static final byte[] FAILED_BYTES = "Unit test failed message".getBytes();
    private static final byte[] COMPLETED_BYTES = "Unit test completed message".getBytes();

    @Test
    @DisplayName("Test that a create document started message is generated")
    void createDocumentGenerationStartedTest() throws Exception {

        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();

        doReturn(STARTED_BYTES).when(avroSerializer).serialize(any(), any(DocumentGenerationStarted.class));

        Message message = messageService.createDocumentGenerationStarted(deserialisedKafkaMessage);

        assertMessageGenerated(message, STARTED_PRODUCER_TOPIC, STARTED_BYTES);
    }

    @Test
    @DisplayName("Test that a create document started message throws exception")
    void createDocumentGenerationStartedExceptionTest() throws Exception {

        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();
        doThrow(new IOException()).when(avroSerializer).serialize(any(), any(DocumentGenerationStarted.class));

        assertThrows(MessageCreationException.class, () -> messageService.createDocumentGenerationStarted(deserialisedKafkaMessage));
    }

    @Test
    @DisplayName("Test that a create document failed message is generated with populated generateDocumentResponse")
    void createDocumentGenerationFailedPopulatedDocumentResponseTest() throws Exception {
        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();
        GenerateDocumentResponse generateDocumentResponse = createResponse();

        doReturn(FAILED_BYTES).when(avroSerializer).serialize(any(), any(DocumentGenerationFailed.class));

        Message message = messageService.createDocumentGenerationFailed(deserialisedKafkaMessage, generateDocumentResponse);

        assertMessageGenerated(message, FAILED_PRODUCER_TOPIC, FAILED_BYTES);
    }

    @Test
    @DisplayName("Test that a create document failed message is generated with null generateDocumentResponse")
    void createDocumentGenerationFailedNullDocumentResponseTest() throws Exception {
        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();
        GenerateDocumentResponse generateDocumentResponse = null;

        doReturn(FAILED_BYTES).when(avroSerializer).serialize(any(), any(DocumentGenerationFailed.class));

        Message message = messageService.createDocumentGenerationFailed(deserialisedKafkaMessage, generateDocumentResponse);

        assertMessageGenerated(message, FAILED_PRODUCER_TOPIC, FAILED_BYTES);
    }

    @Test
    @DisplayName("Test that a create document failed message is generated with null generateDocumentResponse && null deserialised kafka message")
    void createDocumentGenerationFailedNullDocumentResponseAndKafkaMessageTest() throws Exception {
        DeserialisedKafkaMessage deserialisedKafkaMessage = null;
        GenerateDocumentResponse generateDocumentResponse = null;

        doReturn(FAILED_BYTES).when(avroSerializer).serialize(any(), any(DocumentGenerationFailed.class));

        Message message = messageService.createDocumentGenerationFailed(deserialisedKafkaMessage, generateDocumentResponse);

        assertMessageGenerated(message, FAILED_PRODUCER_TOPIC, FAILED_BYTES);
    }

    @Test
    @DisplayName("Test that a create document failed message throws exception")
    void createDocumentGenerationFailedExceptionTest() throws Exception {
        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();
        GenerateDocumentResponse generateDocumentResponse = createResponse();

        doThrow(new IOException()).when(avroSerializer).serialize(any(), any(DocumentGenerationFailed.class));

        assertThrows(MessageCreationException.class, () -> messageService.createDocumentGenerationFailed(deserialisedKafkaMessage, generateDocumentResponse));
    }

    @Test
    @DisplayName("Test that a create document completed message is generated")
    void createDocumentGenerationCompletedTest() throws Exception {
        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();
        GenerateDocumentResponse generateDocumentResponse = createResponse();

        doReturn(COMPLETED_BYTES).when(avroSerializer).serialize(any(), any(DocumentGenerationCompleted.class));

        Message message = messageService.createDocumentGenerationCompleted(deserialisedKafkaMessage, generateDocumentResponse);

        assertMessageGenerated(message, COMPLETED_PRODUCER_TOPIC, COMPLETED_BYTES);
    }

    @Test
    @DisplayName("Test that a create document completed message throws exception")
    void createDocumentGenerationCompletedExceptionTest() throws Exception {
        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();
        GenerateDocumentResponse generateDocumentResponse = createResponse();

        doThrow(new IOException()).when(avroSerializer).serialize(any(), any(DocumentGenerationCompleted.class));

        assertThrows(MessageCreationException.class, () -> messageService.createDocumentGenerationCompleted(deserialisedKafkaMessage, generateDocumentResponse));
    }

    /**
     * Populate GenerateDocumentResponse with content
     *
     * @return
     */
    private GenerateDocumentResponse createResponse() {
        GenerateDocumentResponse response = new GenerateDocumentResponse();
        Links links = new Links();
        links.setLocation("location");
        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("value1", "value2");
        response.setDescription("description");
        response.setDescriptionIdentifier("descriptionIdentifier");
        response.setDescriptionValues(descriptionValues);
        response.setLinks(links);
        response.setSize("size");

        return response;
    }

    /**
     * Populate DeserialisedKafkaMessage with content
     *
     * @return
     */
    private DeserialisedKafkaMessage createDeserialisedKafkaMessage() {
        DeserialisedKafkaMessage deserialisedKafkaMessage = new DeserialisedKafkaMessage();
        deserialisedKafkaMessage.setContentType("contentType");
        deserialisedKafkaMessage.setDocumentType("documentType");
        deserialisedKafkaMessage.setId("id");
        deserialisedKafkaMessage.setResource("resource");
        deserialisedKafkaMessage.setResourceId("resourceId");
        deserialisedKafkaMessage.setUserId("userId");

        return deserialisedKafkaMessage;
    }

    /**
     * Assert message is not null and assert that message topic and message value match
     *
     * @param message
     * @param producerTopic
     * @param valueBytes
     */
    private void assertMessageGenerated(Message message, String producerTopic, byte[] valueBytes) {
        assertNotNull(message);
        assertEquals(producerTopic, message.getTopic());
        assertEquals(valueBytes, message.getValue());
    }
}
