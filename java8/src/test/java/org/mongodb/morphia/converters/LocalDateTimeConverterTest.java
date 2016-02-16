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

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class LocalDateTimeConverterTest extends ConverterTest {
    public LocalDateTimeConverterTest() {
        super(new LocalDateTimeConverter());
    }

    @Test
    public void testConversion() throws ParseException {
        compare(LocalDateTime.class, LocalDateTime.now());
        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        compare(LocalDateTime.class, now.toEpochSecond(ZoneOffset.UTC), now);
        compare(LocalDateTime.class, now.format(DateTimeFormatter.ISO_DATE_TIME), now);

        Date date = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss").parse("2016/Jan/20 14:30:15");
        LocalDateTime ldt = LocalDateTime.parse("2016/Jan/20 14:30:15", DateTimeFormatter.ofPattern("yyyy/MMM/dd HH:mm:ss"));
        compare(LocalDateTime.class, date, ldt);
    }

}
