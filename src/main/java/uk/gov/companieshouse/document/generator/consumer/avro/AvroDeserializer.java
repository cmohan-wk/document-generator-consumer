package uk.gov.companieshouse.document.generator.consumer.avro;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;

import uk.gov.companieshouse.kafka.message.Message;

/**
 * Deserializes binary data into an object using Avro.
 *
 * @param <T> Type of object to be deserialized
 */
@Component
public class AvroDeserializer<T extends SpecificRecordBase> {

    /**
     * Deserializes a message in binary format into T.
     *
     * @param message
     * @param schema
     * @return T
     * @throws IOException
     */
    public T deserialize(DatumReader<T> reader, Message message, Schema schema) throws IOException {
        reader.setSchema(schema);

        try(ByteArrayInputStream in = new ByteArrayInputStream(message.getValue())) {
            Decoder decoder = DecoderFactory.get().binaryDecoder(in, null);
            return reader.read(null, decoder);
        }
    }
}
