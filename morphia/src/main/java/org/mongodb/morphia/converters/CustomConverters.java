package org.mongodb.morphia.converters;

import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.Mapper;

import static java.lang.String.format;

public class CustomConverters extends Converters {
    private final DefaultConverters defaultConverters;

    public CustomConverters(final Mapper mapper) {
        super(mapper);
        defaultConverters = new DefaultConverters(mapper);
    }

    @Override
    protected TypeConverter getEncoder(final Class c) {
        TypeConverter encoder = super.getEncoder(c);
        if (encoder == null) {
            encoder = defaultConverters.getEncoder(c);
        }

        if (encoder != null) {
            return encoder;
        }
        throw new ConverterNotFoundException(format("Cannot find encoder for %s", c.getName()));
    }

    @Override
    protected TypeConverter getEncoder(final Object val, final MappedField mf) {
        TypeConverter encoder = super.getEncoder(val, mf);
        if (encoder == null) {
            encoder = defaultConverters.getEncoder(val, mf);
        }

        if (encoder != null) {
            return encoder;
        }

        throw new ConverterNotFoundException(format("Cannot find encoder for %s as need for %s", mf.getType(), mf.getFullName()));
    }
}
