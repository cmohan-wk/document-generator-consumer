package uk.gov.companieshouse.document.generator.consumer.avro;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class AvroDatumFactory {

    public static <T> DatumReader<T> getReader() {
        return new SpecificDatumReader<T>();
    }

    public static <T> DatumWriter<T> getWriter() {
        return new SpecificDatumWriter<T>();
    }
}
