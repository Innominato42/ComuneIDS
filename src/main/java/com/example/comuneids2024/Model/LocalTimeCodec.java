package com.example.comuneids2024.Model;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.LocalTime;

public class LocalTimeCodec implements Codec<LocalTime> {

    @Override
    public void encode(BsonWriter writer, LocalTime value, EncoderContext encoderContext) {
        // Convert LocalTime to seconds of the day
        writer.writeInt32(value.toSecondOfDay());
    }

    @Override
    public LocalTime decode(BsonReader reader, DecoderContext decoderContext) {
        // Convert seconds of the day to LocalTime
        return LocalTime.ofSecondOfDay(reader.readInt32());
    }

    @Override
    public Class<LocalTime> getEncoderClass() {
        return LocalTime.class;
    }
}