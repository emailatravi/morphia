/*
 * Copyright (c) 2008-2016 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mongodb.morphia.converters;

import org.mongodb.morphia.mapping.MappedField;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Provides a converter for {@link java.time.LocalDateTime}
 */
public class LocalDateTimeConverter extends TypeConverter implements SimpleValueConverter {
    /**
     * Creates the Converter.
     */
    public LocalDateTimeConverter() {
        this(LocalDateTime.class);
    }

    protected LocalDateTimeConverter(final Class clazz) {
        super(clazz);
    }

    @Override
    public Object decode(final Class<?> targetClass, final Object val, final MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if (val instanceof LocalDateTime) {
            return val;
        }

        if (val instanceof Date) {
            final Date date = (Date) val;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date.getTime());
            return LocalDateTime.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
                                    cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY),
                                    cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
        }

        if (val instanceof Number) {
            return LocalDateTime.ofEpochSecond(((Number) val).longValue(), 0, ZoneOffset.UTC);
        }

        if (val instanceof String) {
            return LocalDateTime.parse((String) val, DateTimeFormatter.ISO_DATE_TIME);
        }

        throw new IllegalArgumentException("Can't convert to LocalDateTime from " + val);
    }

}
