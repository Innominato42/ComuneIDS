package com.example.comuneids2024.Model;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "ComuneIDS"; // Nome del database MongoDB
    }

    @Override
    public boolean autoIndexCreation() {
        return true; // Abilita la creazione automatica degli indici
    }


    public CodecRegistry codecRegistry() {
        return org.bson.codecs.configuration.CodecRegistries.fromRegistries(
                org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()),
                org.bson.codecs.configuration.CodecRegistries.fromCodecs(new LocalTimeCodec())
        );
    }

    @Override
    public MongoCustomConversions customConversions() {
        List<Object> converters = new ArrayList<>();
        converters.add(new org.springframework.core.convert.converter.Converter<LocalTime, java.time.LocalTime>() {
            @Override
            public java.time.LocalTime convert(LocalTime source) {
                return java.time.LocalTime.of(source.getHour(), source.getMinute(), source.getSecond());
            }
        });
        return new MongoCustomConversions(converters);
    }
}
