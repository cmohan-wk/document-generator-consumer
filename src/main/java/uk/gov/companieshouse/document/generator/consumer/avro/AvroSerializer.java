package uk.gov.companieshouse.document.generator.consumer.avro;

import java.io.IOException;

import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Component;

/**
 * Serializes an object into binary data using Avro.
 *
 * @param <T> Type of object to be serialized
 */
@Component
public class AvroSerializer {

    /**
     * Serializes the object provided into binary data
     *
     * @param data The data in an object
     * @return serializedData The binary data
     * @throws IOException
     */
    public <T extends SpecificRecordBase> byte[] serialize(DatumWriter<T> datumWriter, T data) throws IOException {
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            datumWriter.setSchema(data.getSchema());
            datumWriter.write(data, encoder);
            encoder.flush();

            byte[] serializedData = out.toByteArray();
            encoder.flush();

            return serializedData;
        }
    }
}
