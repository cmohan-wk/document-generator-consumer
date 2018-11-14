package uk.gov.companieshouse.document.generator.consumer.avro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.avro.AvroRuntimeException;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationStarted;
import uk.gov.companieshouse.kafka.message.Message;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AvroDeserializerTest {

	private static final String ENCODED_AVRO_STRING = "Test encoded string";
    private static final String ID = "ID";
    private static final String REQUESTER_ID = "requester-id";

    @Mock
    private DatumReader<DocumentGenerationStarted> mockReader;

    private DocumentGenerationStarted started;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        started = createStartedMessage();
    }

    @Test
    @DisplayName("Check that data is deserialized")
    public void testDeserialize() throws IOException {
        Message message = new Message();
        message.setValue(ENCODED_AVRO_STRING.getBytes());

        when(mockReader.read(any(), any(Decoder.class))).thenReturn(started);

        AvroDeserializer<DocumentGenerationStarted> deserializer = new AvroDeserializer<>();
        DocumentGenerationStarted record = deserializer.deserialize(mockReader, message, DocumentGenerationStarted.getClassSchema());
        assertNotNull(record);
        assertEquals(ID, record.getId());
        assertEquals(REQUESTER_ID, record.getRequesterId());

        verify(mockReader).read(any(), any(Decoder.class));
    }

    @Test
    @DisplayName("Check that error returned when invalid data is entered")
    public void testDeserializeInvalidData() throws IOException {
        String expectionMessage = "Unit test failure";

        Message message = new Message();
        message.setValue("invalid".getBytes());

        when(mockReader.read(any(), any(Decoder.class))).thenThrow(new AvroRuntimeException(expectionMessage));

        AvroDeserializer<DocumentGenerationStarted> deserializer = new AvroDeserializer<>();

        AvroRuntimeException ex = assertThrows(AvroRuntimeException.class, () ->  deserializer.deserialize(
            mockReader, message, DocumentGenerationStarted.getClassSchema()));

        assertEquals(expectionMessage, ex.getMessage());
    }

    /**
     * Create a dummy started message for testing
     * 
     * @return message
     */
    private DocumentGenerationStarted createStartedMessage() {
        DocumentGenerationStarted started = new DocumentGenerationStarted();
        started.setId(ID);
        started.setRequesterId(REQUESTER_ID);

        return started;
    }
}
