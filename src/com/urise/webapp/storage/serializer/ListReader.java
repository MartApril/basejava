package com.urise.webapp.storage.serializer;

import java.io.IOException;

public interface ListReader<T> {
    T readList() throws IOException;
}
