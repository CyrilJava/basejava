package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(String dir) {
        super(dir);
    }
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {

        /*try (DataOutputStream dos = new DataOutputStream(os)) {
            Files.write(dos)
        } catch (IOException e) {
        }*/
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        return null;
    }
}
